package workflow;

import java.util.Map;
import java.util.Objects;
import framework.context.JourneyContext;
import framework.context.ExecutionState;
import framework.context.ExecutionStateSupport;
import framework.context.JourneyContextSupport;
import framework.context.JourneyLifecycleLogger;
import framework.context.LegacyDataKeys;
import io.restassured.response.Response;
import services.PaymentService;
import utils.ExtentReportManager;
import validator.DeletePaymentValidator;
import validator.OrderSetValidator;
import validator.PaymentCollectionValidator;
import validator.PaymentProvidersValidator;
import validator.PaymentSessionValidator;

/**
 * Coordinates payment operations for the existing map-based test workflow.
 *
 * <p>Successful operations retain the legacy data-map values used by existing
 * requests and additionally synchronize their identifiers with the current
 * thread's {@link JourneyContext}.</p>
 */
public final class PaymentFlow {

    private static final String STORE_URL = "https://staging-store.woloo.in";

    private static final String PROVIDER_ID_KEY = LegacyDataKeys.PROVIDER_ID;
    private static final String PAYMENT_COLLECTION_ID_KEY = LegacyDataKeys.PAYMENT_COLLECTION_ID;
    private static final String PAYMENT_SESSION_ID_KEY = LegacyDataKeys.PAYMENT_SESSION_ID;
    private static final String ORDER_ID_KEY = LegacyDataKeys.ORDER_ID;
    private static final String EXPECTED_AMOUNT_KEY = LegacyDataKeys.EXPECTED_AMOUNT;

    private static final String PROVIDER_ID_PATH = "payment_providers[0].id";
    private static final String PAYMENT_COLLECTION_ID_PATH = "payment_collection.id";
    private static final String PAYMENT_SESSION_ID_PATH =
            "payment_collection.payment_sessions[0].id";
    private static final String ORDER_ID_PATH = "order_set.id";

    private PaymentFlow() {
        // Utility class.
    }

    /**
     * Executes payment-provider, collection, and session creation.
     *
     * @param data mutable test data used by the existing workflow
     */
    public static void processPayment(Map<String, String> data) {
        Objects.requireNonNull(data, "Payment data must not be null");

        JourneyLifecycleLogger.start("payment processing");

        getPaymentProvider(data);
        createPaymentCollection(data);
        createPaymentSession(data);

        JourneyLifecycleLogger.complete("payment processing");
    }

    /**
     * Completes the cart and creates its order set.
     *
     * @param data mutable test data used by the existing workflow
     */
    public static void completeOrder(Map<String, String> data) {
        Objects.requireNonNull(data, "Payment data must not be null");

        JourneyLifecycleLogger.start("order completion");
        Response response = PaymentService.createSplitCompleteCart(STORE_URL, data);

        OrderSetValidator.validate(response);

        String orderId = response.jsonPath().getString(ORDER_ID_PATH);
        data.put(ORDER_ID_KEY, orderId);

        JourneyContextSupport.setIfText(JourneyContext::setOrderId, orderId);
        ExecutionStateSupport.update(ExecutionState::setPaymentCompleted, true);

        JourneyLifecycleLogger.complete("order completion");
    }

    /**
     * Deletes the current payment session after it has been successfully
     * validated as deleted.
     *
     * @param data mutable test data used by the existing workflow
     */
    public static void deletePaymentSession(Map<String, String> data) {
        Objects.requireNonNull(data, "Payment data must not be null");

        ExtentReportManager.info("Delete Payment Session validation started.");
        Response response = PaymentService.deleteProductPayment(STORE_URL, data);

        DeletePaymentValidator.validate(response, data.get(PAYMENT_SESSION_ID_KEY));

        JourneyContextSupport.current().setPaymentSessionId(null);
        ExecutionStateSupport.update(ExecutionState::setPaymentSessionCreated, false);

        JourneyLifecycleLogger.complete("payment session deletion");
    }

    /** Fetches and stores the validated payment provider. */
    private static void getPaymentProvider(Map<String, String> data) {
        ExtentReportManager.info("Fetching Payment Provider");
        Response response = PaymentService.getPaymentProviders(STORE_URL, data);

        PaymentProvidersValidator.validate(response);

        String providerId = response.jsonPath().getString(PROVIDER_ID_PATH);
        data.put(PROVIDER_ID_KEY, providerId);

        JourneyContextSupport.setIfText(JourneyContext::setProviderId, providerId);
        ExecutionStateSupport.update(ExecutionState::setPaymentProviderLoaded, true);

        ExtentReportManager.info("Payment Provider : " + providerId);
    }

    /** Creates and stores the validated payment collection. */
    private static void createPaymentCollection(Map<String, String> data) {
        ExtentReportManager.info("Creating Payment Collection");
        Response response = PaymentService.createPaymentCollection(STORE_URL, data);

        PaymentCollectionValidator.validate(
                response,
                Double.parseDouble(data.get(EXPECTED_AMOUNT_KEY)),
                "inr");

        String paymentCollectionId = response.jsonPath().getString(PAYMENT_COLLECTION_ID_PATH);
        data.put(PAYMENT_COLLECTION_ID_KEY, paymentCollectionId);

        JourneyContextSupport.setIfText(JourneyContext::setPaymentCollectionId, paymentCollectionId);
        ExecutionStateSupport.update(ExecutionState::setPaymentCollectionCreated, true);

        ExtentReportManager.info("Payment Collection Id : " + paymentCollectionId);
    }

    /** Creates and stores the validated payment session. */
    private static void createPaymentSession(Map<String, String> data) {
        ExtentReportManager.info("Creating Payment Session");
        Response response = PaymentService.createPaymentSession(STORE_URL, data);

        PaymentSessionValidator.validate(response, data.get(EXPECTED_AMOUNT_KEY));

        String paymentSessionId = response.jsonPath().getString(PAYMENT_SESSION_ID_PATH);
        data.put(PAYMENT_SESSION_ID_KEY, paymentSessionId);

        JourneyContextSupport.setIfText(JourneyContext::setPaymentSessionId, paymentSessionId);
        ExecutionStateSupport.update(ExecutionState::setPaymentSessionCreated, true);

        ExtentReportManager.info("Payment Session Id : " + paymentSessionId);
    }

}

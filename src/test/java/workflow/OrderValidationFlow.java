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
import validator.OrderSetListValidator;

/**
 * Coordinates validation of a completed order for the existing map-based
 * workflow.
 *
 * <p>The legacy map remains the primary request contract. Journey values are
 * used as fallbacks for the expected order identifier and as lifecycle context
 * for the customer and payment data established by earlier flow steps.</p>
 */
public final class OrderValidationFlow {

    private static final String STORE_URL = "https://staging-store.woloo.in";
    private static final String ORDER_ID_KEY = LegacyDataKeys.ORDER_ID;
    private static final String CUSTOMER_ID_KEY = LegacyDataKeys.CUSTOMER_ID;
    private static final String PROVIDER_ID_KEY = LegacyDataKeys.PROVIDER_ID;
    private static final String PAYMENT_COLLECTION_ID_KEY = LegacyDataKeys.PAYMENT_COLLECTION_ID;
    private static final String PAYMENT_SESSION_ID_KEY = LegacyDataKeys.PAYMENT_SESSION_ID;

    private OrderValidationFlow() {
        // Utility class.
    }

    /**
     * Validates that the completed order set is present in order history.
     *
     * @param data mutable test data used by the existing workflow
     */
    public static void validateOrder(Map<String, String> data) {
        Objects.requireNonNull(data, "Order validation data must not be null");

        JourneyContext context = JourneyContextSupport.current();
        String orderId = resolveOrderId(data, context);

        JourneyLifecycleLogger.start("order validation");
        logJourneyValidationContext(data, context, orderId);

        Response response = PaymentService.getOrderPlacement(STORE_URL, data);
        OrderSetListValidator.validate(response, orderId);

        JourneyContextSupport.setIfText(JourneyContext::setOrderId, orderId);
        ExecutionStateSupport.update(ExecutionState::setOrderValidated, true);

        JourneyLifecycleLogger.complete("order validation");
        ExtentReportManager.info("Order Id : " + orderId);
    }

    /** Resolves the order identifier without changing the legacy data map. */
    private static String resolveOrderId(
            Map<String, String> data,
            JourneyContext context) {

        return JourneyContextSupport.firstNonBlank(data.get(ORDER_ID_KEY), context.getOrderId());
    }

    /** Logs journey values available to the order-validation lifecycle. */
    private static void logJourneyValidationContext(
            Map<String, String> data,
            JourneyContext context,
            String orderId) {

        String customerId = JourneyContextSupport.firstNonBlank(
                context.getCustomerId(),
                data.get(CUSTOMER_ID_KEY));
        String providerId = JourneyContextSupport.firstNonBlank(
                context.getProviderId(),
                data.get(PROVIDER_ID_KEY));
        String paymentCollectionId = JourneyContextSupport.firstNonBlank(
                context.getPaymentCollectionId(),
                data.get(PAYMENT_COLLECTION_ID_KEY));
        String paymentSessionId = JourneyContextSupport.firstNonBlank(
                context.getPaymentSessionId(),
                data.get(PAYMENT_SESSION_ID_KEY));

        ExtentReportManager.info(
                "Validating order=" + orderId
                        + ", customer=" + customerId
                        + ", provider=" + providerId
                        + ", paymentCollection=" + paymentCollectionId
                        + ", paymentSession=" + paymentSessionId);
    }

}

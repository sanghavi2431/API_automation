package workflow;

import java.util.Map;
import java.util.Objects;

import framework.context.ExecutionState;
import framework.context.ExecutionStateSupport;
import framework.context.JourneyContext;
import framework.context.JourneyContextSupport;
import framework.context.JourneyLifecycleLogger;
import framework.context.LegacyDataKeys;
import io.restassured.response.Response;
import services.CartService;
import utils.ExtentReportManager;
import utils.ShippingOptionFinder;
import validator.AddBillingAddressValidator;
import validator.AddShippingMethodValidator;
import validator.AddVehicleDetailsValidator;
import validator.CheckInventoryValidator;
import validator.ShippingOptionsValidator;

/**
 * Coordinates checkout preparation for the existing map-based test workflow.
 *
 * <p>The flow preserves its legacy mutable-map contract while synchronizing a
 * successfully selected shipping option with the current thread's
 * {@link JourneyContext}.</p>
 */
public final class CheckoutFlow {

    private static final String STORE_URL = "https://staging-store.woloo.in";
    private static final String SHIPPING_OPTION_ID_KEY = LegacyDataKeys.SHIPPING_OPTION_ID;
    private static final String SHIPPING_OPTION_NAME_KEY = LegacyDataKeys.SHIPPING_OPTION_NAME;
    private static final String MOBILE_NUMBER_KEY = LegacyDataKeys.MOBILE_NUMBER;
    private static final String VEHICLE_NUMBER_KEY = LegacyDataKeys.VEHICLE_NUMBER;

    private CheckoutFlow() {
        // Utility class.
    }

    /**
     * Executes the complete checkout-preparation sequence.
     *
     * @param data mutable test data used by the existing workflow
     */
    public static void prepareCheckout(Map<String, String> data) {
        Objects.requireNonNull(data, "Checkout data must not be null");

        JourneyLifecycleLogger.start("checkout preparation");

        validateInventory(data);
        addVehicleDetails(data);
        addBillingAddress(data);
        String shippingOptionId = addShippingMethod(data);

        updateJourneyContext(shippingOptionId);
        updateCheckoutCompletedState(true);

        JourneyLifecycleLogger.complete("Checkout preparation");
    }

    /** Validates inventory before continuing with checkout preparation. */
    private static void validateInventory(Map<String, String> data) {
        ExtentReportManager.info("Validating Cart Inventory");

        Response response = CartService.checkCartInventory(STORE_URL, data);
        CheckInventoryValidator.validate(response);
    }

    /** Adds the customer's vehicle details to the cart. */
    private static void addVehicleDetails(Map<String, String> data) {
        ExtentReportManager.info("Adding Vehicle Details");

        Response response = CartService.addCustomerVehicleDetails(STORE_URL, data);
        AddVehicleDetailsValidator.validate(
                response,
                data.get(MOBILE_NUMBER_KEY),
                data.get(VEHICLE_NUMBER_KEY));

        ExtentReportManager.pass("Vehicle Details Added Successfully");
    }

    /** Adds the billing address to the cart. */
    private static void addBillingAddress(Map<String, String> data) {
        ExtentReportManager.info("Adding Billing Address");

        Response response = CartService.updateBillingAddress(STORE_URL, data);
        AddBillingAddressValidator.validate(response);

        ExtentReportManager.pass("Billing Address Added Successfully");
    }

    /** Resolves and adds the requested shipping method. */
    private static String addShippingMethod(Map<String, String> data) {
        ExtentReportManager.info("Fetching Shipping Options");

        Response shippingOptionResponse =
                CartService.getShippingAddressOptions(STORE_URL, data);
        ShippingOptionsValidator.validate(shippingOptionResponse);

        String shippingOptionId = ShippingOptionFinder.findShippingOptionId(
                shippingOptionResponse,
                data.get(SHIPPING_OPTION_NAME_KEY));
        data.put(SHIPPING_OPTION_ID_KEY, shippingOptionId);

        ExtentReportManager.info("Selected Shipping Option : " + shippingOptionId);

        Response response = CartService.addShippingMethod(STORE_URL, data);
        AddShippingMethodValidator.validate(response, shippingOptionId, 0.0);

        ExtentReportManager.pass("Shipping Method Added Successfully");
        return shippingOptionId;
    }

    /** Copies the validated shipping option into the current journey context. */
    private static void updateJourneyContext(String shippingOptionId) {
        JourneyContextSupport.setIfText(JourneyContext::setShippingOptionId, shippingOptionId);
    }

    /** Updates the checkout-completed milestone for the current journey. */
    private static void updateCheckoutCompletedState(boolean checkoutCompleted) {
        ExecutionStateSupport.update(ExecutionState::setCheckoutCompleted, checkoutCompleted);
    }
}

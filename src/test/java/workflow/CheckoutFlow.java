package workflow;

import java.util.Map;

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
 * ============================================================================
 * CheckoutFlow
 * ----------------------------------------------------------------------------
 * Handles all Checkout-related operations.
 *
 * Responsibilities:
 * 1. Inventory Validation
 * 2. Vehicle Details
 * 3. Billing Address
 * 4. Shipping Options
 * 5. Shipping Method
 *
 * This class should NOT contain Payment or Order Placement logic.
 * ============================================================================
 */
public final class CheckoutFlow {

    /**
     * Store API URL
     */
    private static final String STORE_URL = "https://staging-store.woloo.in";

    /**
     * Prevent Object Creation.
     */
    private CheckoutFlow() {

    }

    //==========================================================================
    // PUBLIC METHODS
    //==========================================================================

    /**
     * Executes the complete Checkout preparation.
     *
     * Flow:
     * 1. Validate Inventory
     * 2. Add Vehicle Details
     * 3. Add Billing Address
     * 4. Add Shipping Method
     *
     * @param data Test Data
     */
    public static void prepareCheckout(Map<String, String> data) {

        ExtentReportManager.info(
                "=============== Checkout Started ===============");

        validateInventory(data);

        addVehicleDetails(data);

        addBillingAddress(data);

        addShippingMethod(data);

        ExtentReportManager.pass(
                "Checkout Preparation Completed Successfully");
    }

    //==========================================================================
    // PRIVATE METHODS
    //==========================================================================

    /**
     * Validates Inventory before Checkout.
     *
     * @param data Test Data
     */
    private static void validateInventory(Map<String, String> data) {

        ExtentReportManager.info("Validating Cart Inventory");

        Response response =
                CartService.checkCartInventory(STORE_URL, data);

        CheckInventoryValidator.validate(response);
    }

    /**
     * Adds Customer Vehicle Details.
     *
     * @param data Test Data
     */
    private static void addVehicleDetails(Map<String, String> data) {

        ExtentReportManager.info("Adding Vehicle Details");

        Response response =
                CartService.addCustomerVehicleDetails(STORE_URL, data);

        AddVehicleDetailsValidator.validate(
                response,
                data.get("mobileNo"),
                data.get("vehicle_number"));

        ExtentReportManager.pass(
                "Vehicle Details Added Successfully");
    }

    /**
     * Adds Billing Address.
     *
     * @param data Test Data
     */
    private static void addBillingAddress(Map<String, String> data) {

        ExtentReportManager.info("Adding Billing Address");

        Response response =
                CartService.updateBillingAddress(STORE_URL, data);

        AddBillingAddressValidator.validate(response);

        ExtentReportManager.pass(
                "Billing Address Added Successfully");
    }

    /**
     * Fetches Shipping Options and adds selected Shipping Method.
     *
     * @param data Test Data
     */
    private static void addShippingMethod(Map<String, String> data) {

        ExtentReportManager.info("Fetching Shipping Options");

        Response shippingOptionResponse =
                CartService.getShippingAddressOptions(STORE_URL, data);

        ShippingOptionsValidator.validate(shippingOptionResponse);

        String shippingOptionId =
                ShippingOptionFinder.findShippingOptionId(
                        shippingOptionResponse,
                        data.get("shippingOption_name"));

        data.put("shipping_option_id", shippingOptionId);

        ExtentReportManager.info(
                "Selected Shipping Option : " + shippingOptionId);

        Response response =
                CartService.addShippingMethod(STORE_URL, data);

        AddShippingMethodValidator.validate(
                response,
                shippingOptionId,
                0.0);

        ExtentReportManager.pass(
                "Shipping Method Added Successfully");
    }

}
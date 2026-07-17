package workflow;

import java.util.Map;

import io.restassured.response.Response;
import services.PaymentService;
import utils.ExtentReportManager;
import validator.OrderSetListValidator;

/**
 * ============================================================================
 * OrderValidationFlow
 * ----------------------------------------------------------------------------
 * Handles all Order Validation related operations.
 *
 * Responsibilities:
 * 1. Validate Placed Order
 * 2. Verify Order exists in Order History
 * 3. Future validations:
 *      - Invoice Validation
 *      - Subscription Validation
 *      - Rewards Validation
 *      - Loyalty Validation
 *
 * This class should ONLY validate completed orders.
 * ============================================================================
 */
public final class OrderValidationFlow {

    /**
     * Store API URL
     */
    private static final String STORE_URL = "https://staging-store.woloo.in";

    /**
     * Prevent object creation.
     */
    private OrderValidationFlow() {

    }

    //==========================================================================
    // PUBLIC METHODS
    //==========================================================================

    /**
     * Validates the successfully placed order.
     *
     * Validation Includes:
     * - Order exists
     * - Order Id matches
     * - Order returned in Order List
     *
     * @param data Test Data
     */
    public static void validateOrder(Map<String, String> data) {

        ExtentReportManager.info(
                "=============== Order Validation Started ===============");

        Response response =
                PaymentService.getOrderPlacement(STORE_URL, data);

        OrderSetListValidator.validate(
                response,
                data.get("order_id"));

        ExtentReportManager.pass(
                "Order Validation Completed Successfully");

        ExtentReportManager.info(
                "Order Id : " + data.get("order_id"));
    }

}
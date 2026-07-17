package workflow;

import java.util.Map;

import io.restassured.response.Response;
import services.CartService;
import utils.ExtentReportManager;
import validator.ApplyPromotionValidator;
import validator.DeletePromotionValidator;
import validator.InvalidPromotionValidator;

/**
 * ============================================================================
 * PromotionFlow
 * ----------------------------------------------------------------------------
 * Handles all Promotion-related business operations.
 *
 * Responsibilities:
 * 1. Check Promo Code Availability
 * 2. Apply Valid Promo Code
 * 3. Apply Invalid Promo Code
 * 4. Delete Applied Promo Code
 *
 * This class should NOT contain Cart, Checkout or Payment logic.
 * ============================================================================
 */
public final class PromotionFlow {

    /**
     * Store API URL
     */
    private static final String STORE_URL = "https://staging-store.woloo.in";

    /**
     * Request Data Keys
     */
    private static final String PROMO_CODE_KEY = "promo_codes";

    /**
     * Prevent Object Creation
     */
    private PromotionFlow() {

    }

    //==========================================================================
    // PUBLIC METHODS
    //==========================================================================

    /**
     * Checks whether Promo Code exists in the Excel test data.
     *
     * Empty cells or blank values will return false.
     *
     * @param data Test Data
     * @return true if Promo Code exists
     */
    public static boolean hasPromoCode(Map<String, String> data) {

        String promoCode = data.get(PROMO_CODE_KEY);

        return promoCode != null && !promoCode.isBlank();
    }

    /**
     * Applies a valid Promo Code.
     *
     * @param data Test Data
     */
    public static void applyPromotion(Map<String, String> data) {

        if (!hasPromoCode(data)) {

            ExtentReportManager.info(
                    "Promo Code is empty. Skipping Apply Promotion API.");

            return;
        }

        ExtentReportManager.info(
                "Applying Promo Code : " + data.get(PROMO_CODE_KEY));

        Response response =
                CartService.applyPromocodes(STORE_URL, data);

        ApplyPromotionValidator.validate(
                response,
                data.get(PROMO_CODE_KEY));

        ExtentReportManager.pass(
                "Promo Code Applied Successfully");
    }

    /**
     * Applies an Invalid Promo Code.
     *
     * Used for Negative Test Cases.
     *
     * @param data Test Data
     */
    public static void applyInvalidPromotion(Map<String, String> data) {

        if (!hasPromoCode(data)) {

            ExtentReportManager.info(
                    "Promo Code is empty. Skipping Invalid Promotion API.");

            return;
        }

        ExtentReportManager.info(
                "Applying Invalid Promo Code : "
                        + data.get(PROMO_CODE_KEY));

        Response response =
                CartService.applyPromocodes(STORE_URL, data);

        InvalidPromotionValidator.validate(
                response,
                data.get(PROMO_CODE_KEY));

        ExtentReportManager.pass(
                "Invalid Promotion Validation Completed");
    }

    /**
     * Deletes Applied Promo Code.
     *
     * @param data Test Data
     */
    public static void deletePromotion(Map<String, String> data) {

        if (!hasPromoCode(data)) {

            ExtentReportManager.info(
                    "Promo Code is empty. Skipping Delete Promotion API.");

            return;
        }

        ExtentReportManager.info(
                "Deleting Promo Code : "
                        + data.get(PROMO_CODE_KEY));

        Response response =
                CartService.deletePromocodes(STORE_URL, data);

        DeletePromotionValidator.validate(response);

        ExtentReportManager.pass(
                "Promo Code Deleted Successfully");
    }

}
package workflow;

import java.util.Map;
import java.util.Objects;

import framework.context.ExecutionState;
import framework.context.ExecutionStateSupport;
import framework.context.JourneyContextSupport;
import framework.context.JourneyLifecycleLogger;
import framework.context.LegacyDataKeys;
import io.restassured.response.Response;
import services.CartService;
import utils.ExtentReportManager;
import validator.ApplyPromotionValidator;
import validator.DeletePromotionValidator;
import validator.InvalidPromotionValidator;

/**
 * Coordinates promotion operations for the existing map-based test workflow.
 *
 * <p>Successful promotion operations retain the mutable-map contract and
 * synchronize the applied promotion codes with the current thread's
 * {@link JourneyContext}.</p>
 */
public final class PromotionFlow {

    private static final String STORE_URL = "https://staging-store.woloo.in";
    private static final String PROMO_CODE_KEY = LegacyDataKeys.PROMO_CODES;
    private static final String PROMOTION_CODES_ATTRIBUTE = "promotion.codes";

    private PromotionFlow() {
        // Utility class.
    }

    /**
     * Checks whether promo-code data is present in the existing test-data map.
     *
     * @param data mutable test data used by the existing workflow
     * @return {@code true} when a non-blank promo code is available
     */
    public static boolean hasPromoCode(Map<String, String> data) {
        Objects.requireNonNull(data, "Promotion data must not be null");
        return JourneyContextSupport.hasText(data.get(PROMO_CODE_KEY));
    }

    /**
     * Applies the promo code supplied by the existing test-data map.
     *
     * @param data mutable test data used by the existing workflow
     */
    public static void applyPromotion(Map<String, String> data) {
        Objects.requireNonNull(data, "Promotion data must not be null");

        if (!hasPromoCode(data)) {
            logSkippedOperation("Apply Promotion");
            return;
        }

        String promoCode = data.get(PROMO_CODE_KEY);
        JourneyLifecycleLogger.start("promotion application");
        ExtentReportManager.info("Applying Promo Code : " + promoCode);

        Response response = CartService.applyPromocodes(STORE_URL, data);
        ApplyPromotionValidator.validate(response, promoCode);

        updateJourneyContext(promoCode);
        updatePromotionState(true);

        JourneyLifecycleLogger.complete("promotion application");
    }

    /**
     * Applies the supplied invalid promo code for negative test cases.
     *
     * @param data mutable test data used by the existing workflow
     */
    public static void applyInvalidPromotion(Map<String, String> data) {
        Objects.requireNonNull(data, "Promotion data must not be null");

        if (!hasPromoCode(data)) {
            logSkippedOperation("Invalid Promotion");
            return;
        }

        String promoCode = data.get(PROMO_CODE_KEY);
        ExtentReportManager.info("Applying Invalid Promo Code : " + promoCode);

        Response response = CartService.applyPromocodes(STORE_URL, data);
        InvalidPromotionValidator.validate(response, promoCode);

        JourneyLifecycleLogger.complete("invalid promotion validation");
    }

    /**
     * Deletes the promo code supplied by the existing test-data map.
     *
     * @param data mutable test data used by the existing workflow
     */
    public static void deletePromotion(Map<String, String> data) {
        Objects.requireNonNull(data, "Promotion data must not be null");

        if (!hasPromoCode(data)) {
            logSkippedOperation("Delete Promotion");
            return;
        }

        String promoCode = data.get(PROMO_CODE_KEY);
        ExtentReportManager.info("Deleting Promo Code : " + promoCode);

        Response response = CartService.deletePromocodes(STORE_URL, data);
        DeletePromotionValidator.validate(response);

        clearJourneyContext();
        updatePromotionState(false);

        JourneyLifecycleLogger.complete("promotion deletion");
    }

    /** Stores successfully applied promotion codes in the current journey. */
    private static void updateJourneyContext(String promoCode) {
        JourneyContextSupport.putIfText(PROMOTION_CODES_ATTRIBUTE, promoCode);
    }

    /** Removes promotion data from the current journey after successful deletion. */
    private static void clearJourneyContext() {
        JourneyContextSupport.remove(PROMOTION_CODES_ATTRIBUTE);
    }

    /** Updates the promotion-applied milestone for the current journey. */
    private static void updatePromotionState(boolean promotionApplied) {
        ExecutionStateSupport.update(ExecutionState::setPromotionApplied, promotionApplied);
    }

    /** Logs an operation that is intentionally skipped for blank promo data. */
    private static void logSkippedOperation(String operation) {
        ExtentReportManager.info(
                "Promo Code is empty. Skipping " + operation + " API.");
    }

}

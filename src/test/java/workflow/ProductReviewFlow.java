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
import services.ProductReviewService;
import utils.ExtentReportManager;
import utils.ProductVariantResolver;
import validator.GetProductReviewValidator;
import validator.ProductReviewValidator;

/**
 * Coordinates product-review submission and validation for the legacy
 * map-based workflow.
 *
 * <p>Resolved product data continues to be stored in the supplied map for
 * existing service calls. After successful review operations, reusable values
 * are synchronized with the current thread's {@link JourneyContext}.</p>
 */
public final class ProductReviewFlow {

    private static final String STORE_URL = "https://staging-store.woloo.in";

    private static final String PRODUCT_ID_KEY = LegacyDataKeys.PRODUCT_ID;
    private static final String CUSTOMER_ID_KEY = LegacyDataKeys.CUSTOMER_ID;
    private static final String ORDER_ID_KEY = LegacyDataKeys.ORDER_ID;
    private static final String REVIEW_ID_ATTRIBUTE = "review.id";
    private static final String REVIEW_ID_PATH = "review.id";

    private ProductReviewFlow() {
        // Utility class.
    }

    /**
     * Resolves a product and submits its review.
     *
     * @param productResponse validated product-list response
     * @param data mutable test data used by the existing workflow
     */
    public static void addReview(Response productResponse, Map<String, String> data) {
        Objects.requireNonNull(productResponse, "Product response must not be null");
        Objects.requireNonNull(data, "Product review data must not be null");

        JourneyLifecycleLogger.start("product review submission");
        ProductVariantResolver.fetchProductId(productResponse, data);

        JourneyContext context = JourneyContextSupport.current();
        String productId = resolveProductId(data, context);
        logJourneyReviewContext(data, context, productId);

        Response response = ProductReviewService.addReview(STORE_URL, data);
        ProductReviewValidator.validate(
                response,
                data.get(LegacyDataKeys.COMMENT),
                Integer.parseInt(data.get(LegacyDataKeys.RATING)));

        String reviewId = response.jsonPath().getString(REVIEW_ID_PATH);
        updateJourneyContext(data, productId, reviewId);
        updateReviewSubmittedState(true);

        JourneyLifecycleLogger.complete("product review submission");
    }

    /**
     * Retrieves and validates reviews for the resolved product.
     *
     * @param data mutable test data used by the existing workflow
     */
    public static void validateReview(Map<String, String> data) {
        Objects.requireNonNull(data, "Product review data must not be null");

        JourneyContext context = JourneyContextSupport.current();
        String productId = resolveProductId(data, context);

        JourneyLifecycleLogger.start("product review validation");
        logJourneyReviewContext(data, context, productId);

        Response response = ProductReviewService.getProductReview(STORE_URL, data);
        GetProductReviewValidator.validate(response, productId);

        updateJourneyContext(data, productId, null);

        JourneyLifecycleLogger.complete("product review validation");
    }

    /**
     * Resolves the product identifier from legacy data, falling back to the
     * current journey when the map does not contain one.
     */
    private static String resolveProductId(
            Map<String, String> data,
            JourneyContext context) {

        return JourneyContextSupport.resolveLegacyValue(
                data,
                PRODUCT_ID_KEY,
                context.getProductId());
    }

    /** Logs customer and order context available to the review lifecycle. */
    private static void logJourneyReviewContext(
            Map<String, String> data,
            JourneyContext context,
            String productId) {

        String customerId = JourneyContextSupport.firstNonBlank(
                context.getCustomerId(), data.get(CUSTOMER_ID_KEY));
        String orderId = JourneyContextSupport.firstNonBlank(
                context.getOrderId(), data.get(ORDER_ID_KEY));

        ExtentReportManager.info(
                "Review context: product=" + productId
                        + ", customer=" + customerId
                        + ", order=" + orderId);
    }

    /** Synchronizes successfully used product, customer, order, and review values. */
    private static void updateJourneyContext(
            Map<String, String> data,
            String productId,
            String reviewId) {

        JourneyContextSupport.setIfText(JourneyContext::setProductId, productId);
        JourneyContextSupport.setIfText(JourneyContext::setCustomerId, data.get(CUSTOMER_ID_KEY));
        JourneyContextSupport.setIfText(JourneyContext::setOrderId, data.get(ORDER_ID_KEY));
        JourneyContextSupport.putIfText(REVIEW_ID_ATTRIBUTE, reviewId);
    }

    /** Marks review submission complete after the submission validator succeeds. */
    private static void updateReviewSubmittedState(boolean reviewSubmitted) {
        ExecutionStateSupport.update(ExecutionState::setReviewSubmitted, reviewSubmitted);
    }
}

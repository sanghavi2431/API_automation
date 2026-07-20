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
import utils.CartItemFinder;
import utils.ExtentReportManager;
import utils.ProductVariantResolver;
import validator.AddItemToCartValidator;
import validator.CheckInventoryValidator;
import validator.CreateCartValidator;
import validator.DeleteItemFromCartValidator;
import validator.GetCartValidator;
import validator.InsufficientInventoryValidator;

/**
 * Coordinates cart operations for the existing map-based test workflow.
 *
 * <p>Successful cart operations continue to update the legacy test-data map
 * and additionally synchronize reusable identifiers with the current thread's
 * {@link JourneyContext}.</p>
 */
public final class CartFlow {

    private static final String STORE_URL = "https://staging-store.woloo.in";

    private static final String CART_ID_PATH = "cart.id";
    private static final String CART_ID_KEY = LegacyDataKeys.CART_ID;
    private static final String LINE_ITEM_ID_KEY = LegacyDataKeys.LINE_ITEM_ID;
    private static final String VARIANT_ID_KEY = LegacyDataKeys.VARIANT_ID;
    private static final String PRODUCT_ID_KEY = LegacyDataKeys.PRODUCT_ID;

    private CartFlow() {
        // Utility class.
    }

    /**
     * Creates a new shopping cart.
     *
     * @param data mutable test data used by the existing workflow
     */
    public static void createCart(Map<String, String> data) {
        Objects.requireNonNull(data, "Cart data must not be null");

        JourneyLifecycleLogger.start("shopping cart creation");
        Response response = CartService.createCartId(STORE_URL, data);

        CreateCartValidator.validate(response);

        String cartId = response.jsonPath().getString(CART_ID_PATH);
        data.put(CART_ID_KEY, cartId);

        updateJourneyContext(cartId, null, null, null);
        updateCartCreatedState(true);

        JourneyLifecycleLogger.complete("Shopping cart creation");
    }

    /**
     * Adds a resolved product variant to the shopping cart.
     *
     * @param productResponse validated product-list response
     * @param data mutable test data used by the existing workflow
     */
    public static void addProduct(Response productResponse, Map<String, String> data) {
        Objects.requireNonNull(productResponse, "Product response must not be null");
        Objects.requireNonNull(data, "Cart data must not be null");

        ExtentReportManager.info("Resolving product variant and adding it to the shopping cart.");
        ProductVariantResolver.addSingleVariantProduct(productResponse, data);

        Response response = CartService.addProductToCart(STORE_URL, data);
        AddItemToCartValidator.validate(
                response,
                data.get(VARIANT_ID_KEY),
                Integer.parseInt(data.get(LegacyDataKeys.QUANTITY)));

        validateCart(data);

        updateJourneyContext(
                data.get(CART_ID_KEY),
                data.get(LINE_ITEM_ID_KEY),
                data.get(VARIANT_ID_KEY),
                data.get(PRODUCT_ID_KEY));
        updateProductAddedState(true);

        JourneyLifecycleLogger.complete("shopping cart product addition");
    }

    /**
     * Deletes a product from the shopping cart.
     *
     * @param data mutable test data used by the existing workflow
     */
    public static void deleteProduct(Map<String, String> data) {
        Objects.requireNonNull(data, "Cart data must not be null");

        ExtentReportManager.info("Deleting product from the shopping cart.");
        Response response = CartService.deleteProductfromCart(STORE_URL, data);

        DeleteItemFromCartValidator.validate(response, data.get(LINE_ITEM_ID_KEY));
        updateProductAddedState(false);

        ExtentReportManager.pass("Product deleted from the shopping cart successfully.");
    }

    /**
     * Checks the availability of items in the current shopping cart.
     *
     * @param data mutable test data used by the existing workflow
     */
    public static void checkInventory(Map<String, String> data) {
        Objects.requireNonNull(data, "Cart data must not be null");

        ExtentReportManager.info("Checking shopping cart inventory.");
        Response response = CartService.checkCartInventory(STORE_URL, data);

        CheckInventoryValidator.validate(response);
        ExtentReportManager.pass("Shopping cart inventory validated successfully.");
    }

    /**
     * Validates the existing insufficient-inventory scenario.
     *
     * @param productResponse validated product-list response
     * @param data mutable test data used by the existing workflow
     */
    public static void addInsufficientInventoryProduct(
            Response productResponse,
            Map<String, String> data) {

        Objects.requireNonNull(productResponse, "Product response must not be null");
        Objects.requireNonNull(data, "Cart data must not be null");

        ExtentReportManager.info("Validating insufficient inventory scenario.");
        ProductVariantResolver.addSingleVariantProduct(productResponse, data);

        Response response = CartService.addProductToCart(STORE_URL, data);

        InsufficientInventoryValidator.validate(response);
        ExtentReportManager.pass("Insufficient inventory scenario validated successfully.");
    }

    /**
     * Retrieves and validates the cart, then preserves the selected line-item
     * identifier for the existing delete-product workflow.
     */
    private static void validateCart(Map<String, String> data) {
        ExtentReportManager.info("Validating shopping cart contents.");
        Response response = CartService.getCartItem(STORE_URL, data);

        GetCartValidator.validate(
                response,
                data.get(CART_ID_KEY),
                Integer.parseInt(data.get(LegacyDataKeys.QUANTITY)));

        String itemId = CartItemFinder.findLineItemIdInDetailedCart(
                response,
                data.get(LegacyDataKeys.PRODUCT_NAME),
                data.get(VARIANT_ID_KEY));

        data.put(LINE_ITEM_ID_KEY, itemId);
    }

    /**
     * Copies reusable cart values into the current thread's journey context.
     */
    private static void updateJourneyContext(
            String cartId,
            String lineItemId,
            String variantId,
            String productId) {

        JourneyContextSupport.setIfText(JourneyContext::setCartId, cartId);
        JourneyContextSupport.setIfText(JourneyContext::setLineItemId, lineItemId);
        JourneyContextSupport.setIfText(JourneyContext::setVariantId, variantId);
        JourneyContextSupport.setIfText(JourneyContext::setProductId, productId);
    }

    /** Updates the cart-created milestone for the current journey. */
    private static void updateCartCreatedState(boolean cartCreated) {
        ExecutionStateSupport.update(ExecutionState::setCartCreated, cartCreated);
    }

    /** Updates the product-added milestone for the current journey. */
    private static void updateProductAddedState(boolean productAdded) {
        ExecutionStateSupport.update(ExecutionState::setProductAdded, productAdded);
    }
}

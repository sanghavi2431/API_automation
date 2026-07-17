package workflow;

import java.util.Map;

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
 * ============================================================================
 * CartFlow
 * ----------------------------------------------------------------------------
 * Handles all Cart-related business operations.
 *
 * Responsibilities: 1. Create Cart 2. Add Product to Cart 3. Validate Cart 4.
 * Delete Product from Cart 5. Inventory Validation 6. Insufficient Inventory
 * Validation
 *
 * This class should NOT contain Promotion, Payment or Checkout logic.
 * ============================================================================
 */
public final class CartFlow {

	/**
	 * Store API URL
	 */
	private static final String STORE_URL = "https://staging-store.woloo.in";

	/**
	 * Prevent object creation.
	 */
	private CartFlow() {
	}

	// ==========================================================================
	// PUBLIC METHODS
	// ==========================================================================

	/**
	 * Creates a new shopping cart.
	 *
	 * @param data Test Data
	 */
	public static void createCart(Map<String, String> data) {

		ExtentReportManager.info("Creating Shopping Cart");

		Response response = CartService.createCartId(STORE_URL, data);

		CreateCartValidator.validate(response);

		data.put("cart_id", response.jsonPath().getString("cart.id"));
	}

	/**
	 * Adds a product to the shopping cart.
	 *
	 * @param productResponse Product API Response
	 * @param data            Test Data
	 */
	public static void addProduct(Response productResponse, Map<String, String> data) {

		ExtentReportManager.info("Adding Product To Cart");

		// Resolve Variant ID
		ProductVariantResolver.addSingleVariantProduct(productResponse, data);

		// Add Product
		Response response = CartService.addProductToCart(STORE_URL, data);

		// Validate Product Added
		AddItemToCartValidator.validate(response, data.get("variant_id"), Integer.parseInt(data.get("quantity")));

		// Validate Cart
		validateCart(data);
	}

	/**
	 * Deletes product from cart.
	 *
	 * @param data Test Data
	 */
	public static void deleteProduct(Map<String, String> data) {

		ExtentReportManager.info("Deleting Product From Cart");

		Response response = CartService.deleteProductfromCart(STORE_URL, data);

		DeleteItemFromCartValidator.validate(response, data.get("item_id"));
	}

	/**
	 * Checks inventory before checkout.
	 *
	 * @param data Test Data
	 */
	public static void checkInventory(Map<String, String> data) {

		ExtentReportManager.info("Checking Inventory");

		Response response = CartService.checkCartInventory(STORE_URL, data);

		CheckInventoryValidator.validate(response);
	}

	/**
	 * Adds product with quantity greater than available stock.
	 *
	 * Used for Negative Test Cases.
	 *
	 * @param productResponse Product API Response
	 * @param data            Test Data
	 */
	public static void addInsufficientInventoryProduct(Response productResponse, Map<String, String> data) {

		ExtentReportManager.info("Validating Insufficient Inventory Scenario");

		ProductVariantResolver.addSingleVariantProduct(productResponse, data);

		Response response = CartService.addProductToCart(STORE_URL, data);

		InsufficientInventoryValidator.validate(response);
	}

	// ==========================================================================
	// PRIVATE METHODS
	// ==========================================================================

	/**
	 * Validates Cart Details.
	 *
	 * Extracts Item ID required for: - Update Cart - Delete Cart Item - Future Cart
	 * Operations
	 *
	 * @param data Test Data
	 */
	private static void validateCart(Map<String, String> data) {

		ExtentReportManager.info("Validating Shopping Cart");

		Response response = CartService.getCartItem(STORE_URL, data);

		GetCartValidator.validate(response, data.get("cart_id"), Integer.parseInt(data.get("quantity")));

		String itemId = CartItemFinder.findLineItemIdInDetailedCart(response, data.get("productName"),
				data.get("variant_id"));

		data.put("item_id", itemId);
	}

}
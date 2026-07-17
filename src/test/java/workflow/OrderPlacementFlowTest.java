package workflow;

import java.util.Map;

import io.restassured.response.Response;
import utils.ExtentReportManager;

/**
 * ============================================================================
 * OrderPlacementFlow
 * ----------------------------------------------------------------------------
 * The complete Cafe Order Placement workflow.
 *
 * Workflow: 1. Authenticate User 2. Initialize Cafe 3. Create Cart 4. Add
 * Product to Cart 5. Apply Promotion (Optional) 6. Prepare Checkout 7. Process
 * Payment 8. Complete Order 9. Validate Order
 *
 * This class should only coordinate workflow execution. Business logic belongs
 * in the respective Flow classes.
 * ============================================================================
 */
public final class OrderPlacementFlowTest {

	/**
	 * Prevent object creation.
	 */
	private OrderPlacementFlowTest() {

	}

	// ==========================================================================
	// ORDER PLACEMENT
	// ==========================================================================

	/**
	 * Executes the complete order placement flow.
	 *
	 * @param data Test data loaded from Excel.
	 */
	public static void placeOrder(Map<String, String> data) {

		logStart("Order Placement");

		// Step 1 : Authentication
		AuthenticationFlow.authenticate(data);

		// Step 2 : Initialize Cafe & Load Products
		Response productResponse = CafeFlow.initializeCafe(data);

		// Step 3 : Create Cart
		CartFlow.createCart(data);

		// Step 4 : Add Product
		CartFlow.addProduct(productResponse, data);

		// Step 5 : Apply Promo Code (Optional)
		PromotionFlow.applyPromotion(data);

		// Step 6 : Checkout
		CheckoutFlow.prepareCheckout(data);

		// Step 7 : Payment
		PaymentFlow.processPayment(data);

		// Step 8 : Complete Order
		PaymentFlow.completeOrder(data);

		// Step 9 : Validate Order
		OrderValidationFlow.validateOrder(data);

		// Step 10 : Add Product review
		ProductReviewFlow.addReview(productResponse,data);
		
		// Step 11: Validate Review
		ProductReviewFlow.validateReview(data);

		logEnd("Order Placement");
	}

	// ==========================================================================
	// INVALID PROMOTION FLOW
	// ==========================================================================

	/**
	 * Executes order flow with an invalid promo code.
	 *
	 * @param data Test data
	 */
	public static void placeOrderWithInvalidPromo(Map<String, String> data) {

		logStart("Invalid Promotion");

		AuthenticationFlow.authenticate(data);

		Response productResponse = CafeFlow.initializeCafe(data);

		CartFlow.createCart(data);

		CartFlow.addProduct(productResponse, data);

		PromotionFlow.applyInvalidPromotion(data);

		logEnd("Invalid Promotion");
	}

	// ==========================================================================
	// DELETE PROMOTION FLOW
	// ==========================================================================

	/**
	 * Applies and deletes a promo code.
	 *
	 * @param data Test data
	 */
	public static void deletePromotion(Map<String, String> data) {

		logStart("Delete Promotion");

		AuthenticationFlow.authenticate(data);

		Response productResponse = CafeFlow.initializeCafe(data);

		CartFlow.createCart(data);

		CartFlow.addProduct(productResponse, data);

		PromotionFlow.applyPromotion(data);

		PromotionFlow.deletePromotion(data);

		logEnd("Delete Promotion");
	}

	// ==========================================================================
	// DELETE CART PRODUCT FLOW
	// ==========================================================================

	/**
	 * Deletes a product from the shopping cart.
	 *
	 * @param data Test data
	 */
	public static void deleteCartProduct(Map<String, String> data) {

		logStart("Delete Cart Product");

		AuthenticationFlow.authenticate(data);

		Response productResponse = CafeFlow.initializeCafe(data);

		CartFlow.createCart(data);

		CartFlow.addProduct(productResponse, data);

		CartFlow.deleteProduct(data);

		logEnd("Delete Cart Product");
	}

	// ==========================================================================
	// INSUFFICIENT INVENTORY FLOW
	// ==========================================================================

	/**
	 * Validates insufficient inventory scenario.
	 *
	 * @param data Test data
	 */
	public static void validateInsufficientInventory(Map<String, String> data) {

		logStart("Insufficient Inventory");

		AuthenticationFlow.authenticate(data);

		Response productResponse = CafeFlow.initializeCafe(data);

		CartFlow.createCart(data);

		CartFlow.addInsufficientInventoryProduct(productResponse, data);

		logEnd("Insufficient Inventory");
	}
	
	// ==========================================================================
		// DELETE CART PRODUCT PAYMENT FLOW
		// ==========================================================================

		/**
		 * Deletes Cart Payment.
		 *
		 * @param data Test data
		 */
		public static void deleteProductPayment(Map<String, String> data) {

			logStart("Delete Cart Product Payment");

			AuthenticationFlow.authenticate(data);

			Response productResponse = CafeFlow.initializeCafe(data);

			CartFlow.createCart(data);

			CartFlow.addProduct(productResponse, data);

			PromotionFlow.applyPromotion(data);

			CheckoutFlow.prepareCheckout(data);

			PaymentFlow.processPayment(data);

			PaymentFlow.deletePaymentSession(data);

			logEnd("Delete Cart Product Payment");
		}
		
		/**
		 * Invalid OTP handling
		 *
		 * @param data Test data
		 */
		public static void invalidOTPErroHandling(Map<String, String> data) {
			
			AuthenticationFlow.invalidOTPError(data);
			
		}
		
		/**
		 * Invalid Mobile number handling
		 *
		 * @param data Test data
		 */
		public static void invalidMobileNoErroHandling(Map<String, String> data) {
			
			AuthenticationFlow.invalidMobileNoError(data);
			
		}

	// ==========================================================================
	// COMMON LOGGING
	// ==========================================================================

	/**
	 * Logs workflow start.
	 *
	 * @param flowName Flow name
	 */
	private static void logStart(String flowName) {

		ExtentReportManager.info("================================================================");

		ExtentReportManager.info("Starting Flow : " + flowName);

		ExtentReportManager.info("================================================================");
	}

	/**
	 * Logs workflow completion.
	 *
	 * @param flowName Flow name
	 */
	private static void logEnd(String flowName) {

		ExtentReportManager.pass(flowName + " completed successfully.");

		ExtentReportManager.info("================================================================");
	}

}
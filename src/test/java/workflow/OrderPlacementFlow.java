package workflow;

import java.util.Map;

import io.restassured.response.Response;
import services.AuthService;
import services.CafeCategories;
import services.CafeCollections;
import services.CafeProducts;
import services.CartService;
import services.PaymentService;
import services.QRService;
import utils.CartItemFinder;
import utils.ExtentReportManager;
import utils.ProductVariantResolver;
import utils.ShippingOptionFinder;
import validator.AddBillingAddressValidator;
import validator.AddItemToCartValidator;
import validator.AddShippingMethodValidator;
import validator.AddVehicleDetailsValidator;
import validator.ApplyPromotionValidator;
import validator.CafeCategoryValidator;
import validator.CafeCollectionsValidator;
import validator.CafeProductsValidator;
import validator.CafeStockLocationValidator;
import validator.CheckInventoryValidator;
import validator.CreateCartValidator;
import validator.DeleteItemFromCartValidator;
import validator.DeletePromotionValidator;
import validator.GetCartValidator;
import validator.InsufficientInventoryValidator;
import validator.InvalidPromotionValidator;
import validator.OrderSetListValidator;
import validator.OrderSetValidator;
import validator.PaymentCollectionValidator;
import validator.PaymentProvidersValidator;
import validator.PaymentSessionValidator;
import validator.ShippingOptionsValidator;

public final class OrderPlacementFlow {

	private static final String BASE_URL = "https://staging-api.woloo.in";
	private static final String STORE_URL = "https://staging-store.woloo.in";

	private OrderPlacementFlow() {
	}

	public static void orderFlow(Map<String, String> data) {

		ExtentReportManager.info("========== Order Placement Flow Started ==========");

		authenticate(data);

		initializeCafe(data);

		Response res = fetchCafeMasterData(data);

		createCart(data);

		addProductToCart(res, data);

		String promoCode = (String) data.get("promo_codes");

		if (promoCode != null && !promoCode.isBlank()) {
		    applyPromotion(data);
		}

		prepareCheckout(data);

		processPayment(data);

		completeOrder(data);

		validateOrder(data);

		ExtentReportManager.info("========== Order Placement Flow Completed ==========");
	}
	
	public static void orderFlowApplyInvalidPromoCode(Map<String, String> data) {

		ExtentReportManager.info("========== Order Placement Flow Started ==========");

		authenticate(data);

		initializeCafe(data);

		Response res = fetchCafeMasterData(data);

		createCart(data);

		addProductToCart(res, data);

		String promoCode = (String) data.get("promo_codes");

		if (promoCode != null && !promoCode.isBlank()) {
			applyInvalidPromotion(data);
		}

		prepareCheckout(data);

		processPayment(data);

		completeOrder(data);

		validateOrder(data);

		ExtentReportManager.info("========== Order Placement Flow Completed ==========");
	}
	
	public static void deleteCartProduct(Map<String, String> data) {

		ExtentReportManager.info("========== Cart Product Deletion flow Started ==========");

		authenticate(data);

		initializeCafe(data);

		Response res = fetchCafeMasterData(data);

		createCart(data);

		addProductToCart(res, data);

		deleteProductfromCart(data);

		ExtentReportManager.info("========== Deleted Product from cart Successfully ==========");
	}
	
	public static void insufficientInventoryErrorHandling(Map<String, String> data) {

		ExtentReportManager.info("========== Check Insufficient Inventory ==========");

		authenticate(data);

		initializeCafe(data);

		Response res = fetchCafeMasterData(data);

		createCart(data);

		addInsufficientInventoryProductToCart(res, data);


		ExtentReportManager.info("========== Successfully validated  ==========");
	}
	
	public static void deletePromocode(Map<String, String> data) throws InterruptedException {

		ExtentReportManager.info("========== Order Placement Flow Started ==========");

		authenticate(data);

		initializeCafe(data);

		Response res = fetchCafeMasterData(data);

		createCart(data);

		addProductToCart(res, data);

		String promoCode = (String) data.get("promo_codes");

		if (promoCode != null && !promoCode.isBlank()) {
		    applyPromotion(data);
		     Thread.sleep(100);
		    deletePromotion(data);
		}

		prepareCheckout(data);

		processPayment(data);

		completeOrder(data);

		validateOrder(data);

		ExtentReportManager.info("========== Order Placement Flow Completed ==========");
	}

	// ==========================================================
	// AUTHENTICATION
	// ==========================================================

	private static void authenticate(Map<String, String> data) {

		Response response = AuthService.login(BASE_URL, data);

		data.put("medusa_token", "Bearer " + response.jsonPath().getString("results.medusa_token"));

		data.put("clientToken", response.jsonPath().getString("results.token"));

		data.put("region_id", response.jsonPath().getString("results.region_id"));

		data.put("user_id", response.jsonPath().getString("results.user_id"));
	}

	// ==========================================================
	// CAFE INITIALIZATION
	// ==========================================================

	private static void initializeCafe(Map<String, String> data) {

		Response response = QRService.getCafeStockLocation(STORE_URL, data);

		CafeStockLocationValidator.validate(response, data);

		data.put("publishableKey", response.jsonPath().getString("publishable_api_key"));

		data.put("fields", "*variants.calculated_price,variants.inventory_quantity,*variants.images,*categories");
	}

	// ==========================================================
	// MASTER DATA
	// ==========================================================

//    private static Response cafeProductResponse;

	private static Response fetchCafeMasterData(Map<String, String> data) {

		data.put("fields", "*variants.calculated_price, variants.inventory_quantity,*variants.images,*categories");
		Response cafeProductResponse = CafeProducts.getCafeProducts(STORE_URL, data);
		CafeProductsValidator.validate(cafeProductResponse);

		Response categoryResponse = CafeCategories.getCafeProductCategories(STORE_URL, data);

		CafeCategoryValidator.validate(categoryResponse);

		Response collectionResponse = CafeCollections.getCafeProductCollections(STORE_URL, data);

		CafeCollectionsValidator.validate(collectionResponse);

		return cafeProductResponse;
	}

	// ==========================================================
	// CART
	// ==========================================================

	private static void createCart(Map<String, String> data) {

		Response response = CartService.createCartId(STORE_URL, data);

		CreateCartValidator.validate(response);

		data.put("cart_id", response.jsonPath().getString("cart.id"));
	}

	private static void addProductToCart(Response cafeProductResponse, Map<String, String> data) {

		ProductVariantResolver.addSingleVariantProduct(cafeProductResponse, data);

		Response response = CartService.addProductToCart(STORE_URL, data);

		AddItemToCartValidator.validate(response, data.get("variant_id"), Integer.parseInt(data.get("quantity")));

		validateCart(data);
	}

	private static void addInsufficientInventoryProductToCart(Response cafeProductResponse, Map<String, String> data) {

		ProductVariantResolver.addSingleVariantProduct(cafeProductResponse, data);

		Response response = CartService.addProductToCart(STORE_URL, data);

		InsufficientInventoryValidator.validate(response);
	}
	private static void validateCart(Map<String, String> data) {

		Response response = CartService.getCartItem(STORE_URL, data);

		GetCartValidator.validate(response, data.get("cart_id"), Integer.parseInt(data.get("quantity")));

		String itemId = CartItemFinder.findLineItemIdInDetailedCart(response, data.get("productName"),
				data.get("variant_id"));

		data.put("item_id", itemId);
	}
	
	private static void deleteProductfromCart( Map<String, String> data) {


		Response response = CartService.deleteProductfromCart(STORE_URL, data);

		DeleteItemFromCartValidator.validate(response, data.get("item_id"));
	}

	// ==========================================================
	// PROMOTION
	// ==========================================================

	private static void applyPromotion(Map<String, String> data) {

		Response response = CartService.applyPromocodes(STORE_URL, data);

		ApplyPromotionValidator.validate(response, data.get("promo_codes"));
	}
	
	private static void applyInvalidPromotion(Map<String, String> data) {

		Response response = CartService.applyPromocodes(STORE_URL, data);

		InvalidPromotionValidator.validate(response, data.get("promo_codes"));
	}

	private static void deletePromotion(Map<String, String> data) {

		Response response = CartService.deletePromocodes(STORE_URL, data);

		DeletePromotionValidator.validate(response);
	}
	// ==========================================================
	// CHECKOUT
	// ==========================================================

	private static void prepareCheckout(Map<String, String> data) {

		validateInventory(data);

		addVehicleDetails(data);

		addBillingAddress(data);

		addShippingMethod(data);
	}

	private static void validateInventory(Map<String, String> data) {

		Response response = CartService.checkCartInventory(STORE_URL, data);

		CheckInventoryValidator.validate(response);
	}

	private static void addVehicleDetails(Map<String, String> data) {

		Response response = CartService.addCustomerVehicleDetails(STORE_URL, data);

		AddVehicleDetailsValidator.validate(response, data.get("mobileNo"), data.get("vehicle_number"));
	}

	private static void addBillingAddress(Map<String, String> data) {

		Response response = CartService.updateBillingAddress(STORE_URL, data);

		AddBillingAddressValidator.validate(response);
	}

	private static void addShippingMethod(Map<String, String> data) {

		Response shippingOptionResponse = CartService.getShippingAddressOptions(STORE_URL, data);

		ShippingOptionsValidator.validate(shippingOptionResponse);

		String shippingOptionId = ShippingOptionFinder.findShippingOptionId(shippingOptionResponse,
				data.get("shippingOption_name"));

		data.put("shipping_option_id", shippingOptionId);

		Response response = CartService.addShippingMethod(STORE_URL, data);

		AddShippingMethodValidator.validate(response, shippingOptionId, 0.0);
	}

	// ==========================================================
	// PAYMENT
	// ==========================================================

	private static void processPayment(Map<String, String> data) {

		getPaymentProvider(data);

		createPaymentCollection(data);

		createPaymentSession(data);
	}

	private static void getPaymentProvider(Map<String, String> data) {

		Response response = PaymentService.getPaymentProviders(STORE_URL, data);

		PaymentProvidersValidator.validates(response);

		data.put("provider_id", response.jsonPath().getString("payment_providers[0].id"));
	}

	private static void createPaymentCollection(Map<String, String> data) {

		Response response = PaymentService.createPaymentCollection(STORE_URL, data);

		PaymentCollectionValidator.validate(response, Double.parseDouble(data.get("expectedAmount")), "inr");

		data.put("payment_id", response.jsonPath().getString("payment_collection.id"));
	}

	private static void createPaymentSession(Map<String, String> data) {

		Response response = PaymentService.createPaymentSession(STORE_URL, data);

		PaymentSessionValidator.validate(response, data.get("expectedAmount"));

		data.put("payment_sessionid", response.jsonPath().getString("payment_collection.payment_sessions[0].id"));
	}

	// ==========================================================
	// COMPLETE ORDER
	// ==========================================================

	private static void completeOrder(Map<String, String> data) {

		Response response = PaymentService.createSplitCompleteCart(STORE_URL, data);

		OrderSetValidator.validate(response);

		data.put("order_id", response.jsonPath().getString("order_set.id"));
	}

	// ==========================================================
	// ORDER VALIDATION
	// ==========================================================

	private static void validateOrder(Map<String, String> data) {

		Response response = PaymentService.getOrderPlacement(STORE_URL, data);

		 OrderSetListValidator.validate(response, data.get("order_id"));

	}

}
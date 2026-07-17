package services;

import java.util.Map;

import factory.AddItemToCartRequestFactory;
import factory.AddShippingMethodRequestFactory;
import factory.ApplyPromotionRequestFactory;
import factory.CreateCartRequestFactory;
import factory.UpdateCartAddressRequestFactory;
import factory.UpdateCartMetadataRequestFactory;
import io.restassured.response.Response;
import util.JsonUtils;
import utils.ApiClients;

public final class CartService {

	private static final String Cafe_Carts_ENDPOINT = "/store/carts";

	private CartService() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Creates ApiClient instance.
	 *
	 * @param baseUrl API base URL
	 * @return ApiClient
	 */
	private static ApiClients client(String baseUrl) {
		return new ApiClients(baseUrl);
	}

	/**
	 * Create Cafe Cart based on RegionId.
	 * 
	 * API: POST /store/carts
	 * 
	 * @param Bearer token
	 * @param Region ID
	 * @return Cafe Carts Response
	 */
	public static Response createCartId(String baseUrl, Map<String, String> data) {

		String requestBody = JsonUtils.toJson(CreateCartRequestFactory.createCart(data.get("region_id")));

		Response response = client(baseUrl).post(Cafe_Carts_ENDPOINT, data.get("medusa_token"), requestBody,
				data.get("publishableKey"));

		return response;

	}

	/**
	 * Add Cafe Product to Cart based on Variant ID and Quantity.
	 * 
	 * API: POST /store/carts
	 * 
	 * @param Bearer token
	 * @param Region ID
	 * @return Cafe Carts Response
	 */
	public static Response addProductToCart(String baseUrl, Map<String, String> data) {
		final String Cafe_AddProductToCarts_ENDPOINT = "/store/carts/" + data.get("cart_id") + "/line-items";

		Map<String, Object> queryParams = Map.of("fields",
				"items.variant.*,items.variant.options.*,items.variant.images.*,items.product.*,items.product.images.*");

		String requestBody = JsonUtils.toJson(AddItemToCartRequestFactory
				.createRequest(Integer.parseInt(data.get("quantity")), data.get("variant_id")));

		Response response = client(baseUrl).post(Cafe_AddProductToCarts_ENDPOINT, data.get("medusa_token"), requestBody,
				data.get("publishableKey"), queryParams);

		return response;

	}

	/**
	 * Apply Promotions based on CartID and products.
	 * 
	 * API: Post /store/carts/cartId/promotions
	 * 
	 * @param Bearer     token
	 * @param Promocodes eg:DISC10,WOLOO_COINS
	 * @return Carts Response
	 */
	public static Response applyPromocodes(String baseUrl, Map<String, String> data) {
		final String Cafe_Promocodes_ENDPOINT = "/store/carts/" + data.get("cart_id") + "/promotions";

		String requestBody = JsonUtils.toJson(ApplyPromotionRequestFactory.create(data));

		Response response = client(baseUrl).post(Cafe_Promocodes_ENDPOINT, data.get("medusa_token"), requestBody,
				data.get("publishableKey"));

		return response;

	}
	
	/**
	 * Delete Promotions based on CartID and products.
	 * 
	 * API: DELETE /store/carts/cartId/promotions
	 * 
	 * @param Bearer     token
	 * @param Promocodes eg:DISC10,WOLOO_COINS
	 * @return Carts Response
	 */
	public static Response deletePromocodes(String baseUrl, Map<String, String> data) {
		final String Cafe_Promocodes_ENDPOINT = "/store/carts/" + data.get("cart_id") + "/promotions";

		String requestBody = JsonUtils.toJson(ApplyPromotionRequestFactory.create(data));

		Response response = client(baseUrl).delete(Cafe_Promocodes_ENDPOINT, data.get("medusa_token"), requestBody,
				data.get("publishableKey"));

		return response;

	}

	/**
	 * Get Added Cart Product based on Cart ID.
	 * 
	 * API: GET /store/carts/cartId
	 * 
	 * @param Bearer token
	 * @param fileds and cartId
	 * @return Carts Response
	 */
	public static Response getCartItem(String baseUrl, Map<String, String> data) {
		final String Cafe_GetCartsProduct_ENDPOINT = "/store/carts/" + data.get("cart_id");

		Map<String, Object> queryParams = Map.of("fields",
				"items.variant.*,items.variant.options.*,items.variant.images.*,items.product.*,items.product.images.*");

		Response response = client(baseUrl).get(Cafe_GetCartsProduct_ENDPOINT, data.get("medusa_token"), queryParams,
				data.get("publishableKey"));

		return response;

	}
	
	/**
	 * Check Cart Inventory based on Cart ID.
	 * 
	 * API: GET /store/carts/cartId/check-inventory
	 * 
	 * @param Bearer token
	 * @param fileds and cartId
	 * @return Carts Response
	 */
	public static Response checkCartInventory(String baseUrl, Map<String, String> data) {
		final String Cafe_CheckInventory_ENDPOINT = "/store/carts/" + data.get("cart_id")+"/check-inventory";

		Response response = client(baseUrl).get(Cafe_CheckInventory_ENDPOINT, data.get("medusa_token"),
				data.get("publishableKey"));

		return response;

	}
	
	/**
	 * Add Vehicle details for delivery.
	 * 
	 * API: Post /store/carts/cartId
	 * 
	 * @param Bearer token
	 * @param Customre vehicle details like "Vehicle no, User name,Vehicle type, User name"
	 * @return Cart Response
	 */
	public static Response addCustomerVehicleDetails(String baseUrl, Map<String, String> data) {
		final String CustomerVehicleDetails_ENDPOINT = "/store/carts/" + data.get("cart_id");

		String requestBody = JsonUtils.toJson(UpdateCartMetadataRequestFactory.create(data));

		Response response = client(baseUrl).post(CustomerVehicleDetails_ENDPOINT, data.get("medusa_token"), requestBody,
				data.get("publishableKey"));

		return response;

	}
	
	/**
	 * Add Delivery Address.
	 * 
	 * API: Post /store/carts/cartId
	 * 
	 * @param Bearer token
	 * @param Customer billing and shipping address details
	 * @return Cart Response
	 */
	public static Response updateBillingAddress(String baseUrl, Map<String, String> data) {
		final String Product_Shippingaddress_ENDPOINT = "/store/carts/" + data.get("cart_id");

		String requestBody = JsonUtils.toJson(UpdateCartAddressRequestFactory.create(data));

		Response response = client(baseUrl).post(Product_Shippingaddress_ENDPOINT, data.get("medusa_token"), requestBody,
				data.get("publishableKey"));

		return response;

	}
	
	/**
	 * Get Shipping address options based on cartID.
	 * 
	 * API: GET /store/shipping-options/address
	 * 
	 * @param Bearer token
	 * @return Cart shipping address options Response
	 */
	public static Response getShippingAddressOptions(String baseUrl, Map<String, String> data) {
		final String Product_ShippingaddressOptions_ENDPOINT = "/store/shipping-options/address";

		Map<String, Object> queryParams = Map.of("cart_id",data.get("cart_id"));

		Response response = client(baseUrl).get(Product_ShippingaddressOptions_ENDPOINT, data.get("medusa_token"), queryParams,
				data.get("publishableKey"));

		return response;

	}
	
	/**
	 * Add Delivery Address.
	 * 
	 * API: Post /store/carts/cartId
	 * 
	 * @param Bearer token
	 * @param Customer billing and shipping address details
	 * @return Cart Response
	 */
	public static Response addShippingMethod(String baseUrl, Map<String, String> data) {
		final String Add_ShippingMethod_ENDPOINT = "/store/carts/" + data.get("cart_id")+"/add-shipping-methods";

		String requestBody = JsonUtils.toJson(AddShippingMethodRequestFactory.create(data));

		Response response = client(baseUrl).post(Add_ShippingMethod_ENDPOINT, data.get("medusa_token"), requestBody,
				data.get("publishableKey"));

		return response;

	}
	
	/**
	 * Delete Cafe Product from Cart based on Item ID .
	 * 
	 * API: DELETE /store/carts/Cart_id/line-items/item_id
	 * 
	 * @param Bearer token
	 * @return Cafe Carts Response
	 */
	public static Response deleteProductfromCart(String baseUrl, Map<String, String> data) {
		final String Cafe_DeleteProductToCarts_ENDPOINT = "/store/carts/" + data.get("cart_id") + "/line-items/"+data.get("item_id");

		Map<String, Object> queryParams = Map.of("fields",
				"items.variant.*,items.variant.options.*,items.variant.images.*,items.product.*,items.product.images.*");

		Response response = client(baseUrl).delete(Cafe_DeleteProductToCarts_ENDPOINT, data.get("medusa_token"),
				data.get("publishableKey"), queryParams);

		return response;

	}

}

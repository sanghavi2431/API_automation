package services;

import java.util.Map;

import factory.CreatePaymentCollectionRequestFactory;
import factory.CreatePaymentSessionRequestFactory;
import factory.DeletePaymentRequestFactory;
import factory.EcomCoinUpdateRequestFactory;
import io.restassured.response.Response;
import util.JsonUtils;
import utils.ApiClients;

public class PaymentService {

	private static final String Cafe_PaymentProviders_ENDPOINT = "/store/payment-providers";
	private static final String Cafe_PaymentCollection_ENDPOINT = "/store/payment-collections";

	private PaymentService() {
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
	 * Get Cafe Payment providers based on RegionId.
	 * 
	 * API: GET /store/payment-providers
	 * 
	 * @param Bearer token
	 * @param Region ID
	 * @return Cafe Payment providers Response
	 */
	public static Response getPaymentProviders(String baseUrl, Map<String, String> data) {

		Map<String, Object> queryParams = Map.of("region_id", data.get("region_id"));

		Response response = client(baseUrl).get(Cafe_PaymentProviders_ENDPOINT, data.get("medusa_token"), queryParams,
				data.get("publishableKey"));

		return response;

	}

	/**
	 * Create a Payment Collection based on Cart ID.
	 * 
	 * API: POST /store/carts
	 * 
	 * @param Bearer token
	 * @param Cart   ID
	 * @return Cafe Payment collection Response
	 */
	public static Response createPaymentCollection(String baseUrl, Map<String, String> data) {

		String requestBody = JsonUtils.toJson(CreatePaymentCollectionRequestFactory.create(data));

		Response response = client(baseUrl).post(Cafe_PaymentCollection_ENDPOINT, data.get("medusa_token"), requestBody,
				data.get("publishableKey"));

		return response;

	}

	/**
	 * Create Payment sessions based on Payment Provider ID.
	 * 
	 * API: Post /store/payment-collections/{{payment_id}}/payment-sessions
	 * 
	 * @param Bearer  token
	 * @param Payment Provider ID
	 * @return Cart Payment sessions Response
	 */
	public static Response createPaymentSession(String baseUrl, Map<String, String> data) {
		final String Add_Paymentsessions_ENDPOINT = "/store/payment-collections/" + data.get("payment_id")
				+ "/payment-sessions";

		String requestBody = JsonUtils.toJson(CreatePaymentSessionRequestFactory.create(data));

		Response response = client(baseUrl).post(Add_Paymentsessions_ENDPOINT, data.get("medusa_token"), requestBody,
				data.get("publishableKey"));

		return response;

	}

	/**
	 * Complete Payment based on Cart ID.
	 * 
	 * API: Post /store/payment-collections/{{payment_id}}/payment-sessions
	 * 
	 * @param Bearer token
	 * @param Cart   ID
	 * @return Split and Complete Cart Response
	 */
	public static Response createSplitCompleteCart(String baseUrl, Map<String, String> data) {
		final String Complete_Cart_ENDPOINT = "/store/carts/" + data.get("cart_id") + "/split-and-complete-cart";

		Response response = client(baseUrl).postWithoutReqBody(Complete_Cart_ENDPOINT, data.get("medusa_token"),
				data.get("publishableKey"));

		return response;

	}
	
	/**
	 * Update E-commerce points based on Order ID.
	 * 
	 * API: Post /api/blog/ecomCoinUpdate
	 * 
	 * @param Client Token, ClientUrl
	 * @param OrderId
	 * @return Woloo Points Response
	 */
	public static Response updateWolooPoints(String baseUrl, Map<String, String> data) {
		final String Complete_Cart_ENDPOINT = "/api/blog/ecomCoinUpdate";
		
		String requestBody = JsonUtils.toJson(EcomCoinUpdateRequestFactory.create(data));

		Response response = client(baseUrl).postUser(Complete_Cart_ENDPOINT, data.get("clientToken"), requestBody);

		return response;

	}
	
	/**
	 * Get OrderSet.
	 * 
	 * API: GET /store/order-sets
	 * 
	 * @param Bearer Token
	 * @return Order Completion Response
	 */
	public static Response getOrderPlacement(String baseUrl, Map<String, String> data) {
		final String Complete_OrderSet_ENDPOINT = "/store/order-sets";

		Response response = client(baseUrl).get(Complete_OrderSet_ENDPOINT, data.get("medusa_token"), data.get("publishableKey"));

		return response;

	}
	
	/**
	 * Delete Cafe Product payment based on payment sessions ID .
	 * 
	 * API: DELETE /store/payment/delete-payment
	 * 
	 * @param Bearer token and payment sessions ID
	 * @return Delete payment Response
	 */
	public static Response deleteProductPayment(String baseUrl, Map<String, String> data) {
		final String Cafe_DeletePayment_ENDPOINT = "/store/payment/delete-payment";

		String requestBody = JsonUtils.toJson(DeletePaymentRequestFactory.create(data));
		
		Response response = client(baseUrl).delete(Cafe_DeletePayment_ENDPOINT, data.get("medusa_token"),requestBody,
				data.get("publishableKey"));

		return response;

	}
	
}

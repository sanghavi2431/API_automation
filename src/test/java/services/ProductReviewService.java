package services;

import java.util.Map;

import factory.ProductReviewRequestFactory;
import io.restassured.response.Response;
import util.JsonUtils;
import utils.ApiClients;

public final class ProductReviewService {

	private static final String Cafe_ProductsReview_ENDPOINT = "/store/reviews";

	private ProductReviewService() {
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
	 * Add Cafe Products Reviews based on ProductID.
	 * 
	 * API: POST /store/reviews
	 * 
	 * @param Bearer  token
	 * @param Product ID, Comments, Ratings
	 * @return Products Review Response
	 */

	public static Response addReview(String baseUrl, Map<String, String> data) {

		String requestBody = JsonUtils.toJson(ProductReviewRequestFactory.create(data));

		Response response = client(baseUrl).post(Cafe_ProductsReview_ENDPOINT, data.get("medusa_token"), requestBody,
				data.get("publishableKey"));

		return response;
	}

	/**
	 * Get Cafe Products Reviews based on ProductID and ReviewId.
	 * 
	 * API: GET /store/products/product_id/reviews?all
	 * 
	 * @param Bearer  token
	 * @param Product ID
	 * @return Get Products Review Response
	 */

	public static Response getProductReview(String baseUrl, Map<String, String> data) {

		final String Cafe_GetCartsProductReview_ENDPOINT = "/store/products/" + data.get("product_id")+"/reviews";

		Map<String, Object> queryParams = Map.of("all", "1");

		Response response = client(baseUrl).get(Cafe_GetCartsProductReview_ENDPOINT, data.get("medusa_token"),
				queryParams, data.get("publishableKey"));

		return response;
	}
}
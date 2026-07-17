package services;

import java.util.Map;

import io.restassured.response.Response;
import utils.ApiClients;

public final class CafeCategories {

	private static final String Cafe_Products_Categories_ENDPOINT = "/store/product-categories";

	private CafeCategories() {
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
	 * Get Cafe Products Categories based on Rank.
	 * 
	 * API: GET /store/product-categories
	 * 
	 * @param Bearer token
	 * @param categories limit
	 * @param Order
	 * @return Cafe Products Categories Response
	 */
	public static Response getCafeProductCategories(String baseUrl, Map<String, String> data) {

		Map<String, Object> queryParams = Map.of("limit", data.get("Category_limit"), "order",
				"rank");

		Response response = client(baseUrl).get(Cafe_Products_Categories_ENDPOINT, data.get("medusa_token"), queryParams,data.get("publishableKey"));

		return response;

	}
}

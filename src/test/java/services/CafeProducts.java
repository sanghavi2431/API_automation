package services;

import java.util.Map;

import io.restassured.response.Response;
import utils.ApiClients;

public final class CafeProducts {

	private static final String Cafe_Products_ENDPOINT = "/store/products";

	private CafeProducts() {
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
	 * Get Cafe Products based on RegionId.
	 * 
	 * API: GET /store/products
	 * 
	 * @param Bearer token
	 * @param products fields
	 * @param Region ID
	 * @return Cafe Products Response
	 */
	public static Response getCafeProducts(String baseUrl, Map<String, String> data) {

		Map<String, Object> queryParams = Map.of("fields", data.get("fields"), "region_id",
				data.get("region_id"));

		Response response = client(baseUrl).get(Cafe_Products_ENDPOINT, data.get("medusa_token"), queryParams,data.get("publishableKey"));

		return response;

	}

}

package services;

import java.util.Map;

import io.restassured.response.Response;
import utils.ApiClients;

public final class CafeCollections {
	private static final String Cafe_Products_Collections_ENDPOINT = "/store/collections";

	private CafeCollections() {
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
	 * Get Cafe Products Collections based on id,title,metadata and limit.
	 * 
	 * API: GET /store/collections
	 * 
	 * @param Bearer token
	 * @param fields
	 * @param collections limit
	 * @return Cafe Products Collections Response
	 */
	public static Response getCafeProductCollections(String baseUrl, Map<String, String> data) {

		Map<String, Object> queryParams = Map.of("fields","id,title,metadata","limit", data.get("collection_limit"));

		Response response = client(baseUrl).get(Cafe_Products_Collections_ENDPOINT, data.get("medusa_token"), queryParams,data.get("publishableKey"));

		return response;

	}
}

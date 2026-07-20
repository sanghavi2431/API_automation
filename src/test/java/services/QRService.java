package services;

import java.util.Map;

import io.restassured.response.Response;
import utils.ApiClients;

public final class QRService {

	private static final String Cafe_QR_ENDPOINT = "/qr/resolve";

	private QRService() {
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
	 * Get Cafe Location based on Sales channel ID and Location ID.
	 * 
	 * API: GET /qr/resolve
	 * 
	 * @param Bearer token
	 * @param Salechannel ID
	 * @param Location ID
	 * @return Cafe Location Response
	 */
	public static Response getCafeStockLocation(String baseUrl, Map<String, String> data) {

		Map<String, Object> queryParams = Map.of("stock_location_id", data.get("stock_location_id"), "sales_channel_id",
				data.get("sales_channel_id"));

		Response response = client(baseUrl).get(Cafe_QR_ENDPOINT, data.get("medusa_token"), queryParams);

		return response;

	}
}

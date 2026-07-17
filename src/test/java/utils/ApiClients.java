package utils;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public final class ApiClients {

	private static final String WOLOO_TOKEN_HEADER = "x-woloo-token";
	private static final String TOKEN_HEADER = "Authorization";
	private static final String PUBLISHABLEKEY_HEADER = "x-publishable-api-key";
	private static final String CLIENT_ID_HEADER = "client-id";
	private static final String CLIENT_SECRET_HEADER = "client-secret";
	private static final String LANGUAGE_HEADER = "accept-language";

	private static final int CONNECTION_TIMEOUT = 30000;
	private static final int SOCKET_TIMEOUT = 30000;

	private final String baseUrl;

	public ApiClients(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Creates common request specification.
	 *
	 * @return base request specification
	 */
	private RequestSpecification baseRequest() {

		return RestAssured.given()
				.spec(new RequestSpecBuilder().setBaseUri(baseUrl).setContentType(ContentType.JSON).build())
				.config(RestAssured.config()
						.httpClient(HttpClientConfig.httpClientConfig()
								.setParam("http.connection.timeout", CONNECTION_TIMEOUT)
								.setParam("http.socket.timeout", SOCKET_TIMEOUT)));
	}

	/**
	 * Creates token authenticated request.
	 *
	 * @param token authentication token
	 * @return request specification
	 */
	private RequestSpecification userRequest(String token) {

		return baseRequest().header(TOKEN_HEADER, token);
	}
	
	private RequestSpecification WolooUserRequest(String token) {

		return baseRequest().header(WOLOO_TOKEN_HEADER, token);
	}
	
	/**
	 * Creates token authenticated request.
	 *
	 * @param Publishable Key
	 * @param Bearer token For authentication
	 * @return request specification
	 */
	private RequestSpecification apiKeyRequest(String apiKey, String token) {

		return baseRequest().header(PUBLISHABLEKEY_HEADER, apiKey).header(TOKEN_HEADER, token);
	}

	/**
	 * Creates token authenticated request with language.
	 *
	 * @param token    authentication token
	 * @param language request language
	 * @return request specification
	 */
	private RequestSpecification userRequest(String token, String language) {

		return userRequest(token).header(LANGUAGE_HEADER, language);
	}

	/**
	 * Creates client authenticated request.
	 *
	 * @param clientId     client id
	 * @param clientSecret client secret
	 * @return request specification
	 */
	private RequestSpecification clientRequest(String clientId, String clientSecret) {

		return baseRequest().header(CLIENT_ID_HEADER, clientId).header(CLIENT_SECRET_HEADER, clientSecret);
	}

	/**
	 * Executes POST request.
	 *
	 * @param endpoint api endpoint
	 * @param token    auth token
	 * @param body     request body
	 * @return response
	 */
	public Response post(String endpoint, String token, String body) {

		ApiExtentLogger.logRequest(endpoint, "POST", TOKEN_HEADER, body);

		Response response = userRequest(token).body(body).post(endpoint);

		ApiExtentLogger.logResponse(response);

		return response;
	}
	
	public Response post(String endpoint, String token, String body, String pulishablekey) {

		ApiExtentLogger.logRequest(endpoint, "POST", TOKEN_HEADER, body);

		Response response = apiKeyRequest(pulishablekey,token).body(body).post(endpoint);//.log().all()

		ApiExtentLogger.logResponse(response);

		return response;
	}
	
	public Response delete(String endpoint, String token, String body, String pulishablekey) {

		ApiExtentLogger.logRequest(endpoint, "DELETE", TOKEN_HEADER, body);

		Response response = apiKeyRequest(pulishablekey,token).body(body).delete(endpoint);//.log().all()

		ApiExtentLogger.logResponse(response);

		return response;
	}
	
	public Response post(String endpoint, String token, String body, String pulishablekey, Map<String, Object> queryParams) {

		ApiExtentLogger.logRequest(endpoint, "POST", TOKEN_HEADER, body);

		Response response = apiKeyRequest(pulishablekey,token).queryParams(queryParams).body(body).post(endpoint);//.log().all()

		ApiExtentLogger.logResponse(response);

		return response;
	}
	
	public Response postUser(String endpoint, String token, String body) {

		ApiExtentLogger.logRequest(endpoint, "POST", WOLOO_TOKEN_HEADER, body);

		Response response = WolooUserRequest(token).body(body).post(endpoint);

		ApiExtentLogger.logResponse(response);

		return response;
	}
	
	public Response postWithoutReqBody(String endpoint, String token, String pulishablekey) {

		ApiExtentLogger.logRequest(endpoint, "POST", TOKEN_HEADER, null);

		Response response = apiKeyRequest(pulishablekey,token).post(endpoint);//.log().all()

		ApiExtentLogger.logResponse(response);

		return response;
	}

	/**
	 * Executes POST request with language header.
	 */
	public Response post(String endpoint, String token, String language, Object body) {

		ApiExtentLogger.logRequest(endpoint, "POST", TOKEN_HEADER, String.valueOf(body));

		Response response = userRequest(token, language).body(body).post(endpoint);

		ApiExtentLogger.logResponse(response);

		return response;
	}

	/**
	 * Executes client authenticated POST request.
	 */
	public Response postWithClientAuth(String endpoint, String clientId, String clientSecret, Object body) {

		ApiExtentLogger.logRequest(endpoint, "POST", CLIENT_ID_HEADER, String.valueOf(body));

		Response response = clientRequest(clientId, clientSecret).body(body).post(endpoint);

		ApiExtentLogger.logResponse(response);

		return response;
	}

	/**
	 * Executes authenticated GET request.
	 */
	public Response get(String endpoint, String token, Map<String, Object> queryParams) {

		ApiExtentLogger.logRequest(endpoint, "GET", queryParams);

		Response response = userRequest(token).queryParams(queryParams).get(endpoint);

		ApiExtentLogger.logResponse(response);

		return response;
	}
	
	public Response get(String endpoint, String token, Map<String, Object> queryParams, String publishableKey) {

		ApiExtentLogger.logRequest(endpoint, "GET", queryParams.toString(),publishableKey);

		Response response = apiKeyRequest(publishableKey,token).queryParams(queryParams).get(endpoint);

		ApiExtentLogger.logResponse(response);

		return response;
	}
	
	public Response get(String endpoint, String token, String publishableKey) {

		ApiExtentLogger.logRequest(endpoint, "GET", null,publishableKey);

		Response response = apiKeyRequest(publishableKey,token).get(endpoint);

		ApiExtentLogger.logResponse(response);

		return response;
	}

	/**
	 * Executes authenticated GET request without parameters.
	 */
	public Response get(String endpoint, String token) {

		ApiExtentLogger.logRequest(endpoint, "GET", "No Query Params", null);

		Response response = userRequest(token).get(endpoint);

		ApiExtentLogger.logResponse(response);

		return response;
	}

	/**
	 * Executes client authenticated GET request.
	 */
	public Response getWithClientAuth(String endpoint, String clientId, String clientSecret) {

		ApiExtentLogger.logRequest(endpoint, "GET", "Client Authentication", null);

		Response response = clientRequest(clientId, clientSecret).get(endpoint);

		ApiExtentLogger.logResponse(response);

		return response;
	}

	/**
	 * Executes PUT request.
	 */
	public Response put(String endpoint, String token, Object body) {

		ApiExtentLogger.logRequest(endpoint, "PUT", TOKEN_HEADER, String.valueOf(body));

		Response response = userRequest(token).body(body).put(endpoint);

		ApiExtentLogger.logResponse(response);

		return response;
	}

	/**
	 * Executes PATCH request.
	 */
	public Response patch(String endpoint, String token, Object body) {

		ApiExtentLogger.logRequest(endpoint, "PATCH", TOKEN_HEADER, String.valueOf(body));

		Response response = userRequest(token).body(body).patch(endpoint);

		ApiExtentLogger.logResponse(response);

		return response;
	}

	/**
	 * Executes DELETE request.
	 */
	public Response delete(String endpoint, String token, String publishableKey,Map<String, Object> queryParams) {

		ApiExtentLogger.logRequest(endpoint, "DELETE", queryParams.toString(), null);

		Response response = apiKeyRequest(publishableKey,token).queryParams(queryParams).delete(endpoint);

		ApiExtentLogger.logResponse(response);

		return response;
	}
}
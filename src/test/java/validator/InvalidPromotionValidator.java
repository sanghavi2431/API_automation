package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.error.InvalidPromocodeErrorResponse;

public final class InvalidPromotionValidator {

	private InvalidPromotionValidator() {
	}

	public static void validate(Response apiResponse, String expectedPromoCode) {

		Assert.assertEquals(apiResponse.statusCode(), 400, "Expected HTTP 400 Bad Request");

		SchemaValidator.validate(apiResponse, "schemas/invalid-promotion-schema.json");

		InvalidPromocodeErrorResponse response = apiResponse.as(InvalidPromocodeErrorResponse.class);

		Assert.assertNotNull(response);

		Assert.assertEquals(response.getType(), "invalid_data");

		Assert.assertTrue(response.getMessage().contains(expectedPromoCode), "Incorrect error message");

		Assert.assertTrue(response.getMessage().toLowerCase().contains("invalid"),
				"Expected invalid promotion message");

	}

}
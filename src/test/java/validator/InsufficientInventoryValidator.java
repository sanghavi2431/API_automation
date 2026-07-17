package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.error.InventoryErrorResponse;

public final class InsufficientInventoryValidator {

	private InsufficientInventoryValidator() {
	}

	public static void validate(Response apiResponse) {

		Assert.assertEquals(apiResponse.statusCode(), 400, "Expected HTTP 400 Bad Request");

		SchemaValidator.validate(apiResponse, "schemas/insufficient-inventory-schema.json");

		InventoryErrorResponse response = apiResponse.as(InventoryErrorResponse.class);

		Assert.assertNotNull(response);

		Assert.assertEquals(response.getCode(), "insufficient_inventory");

		Assert.assertEquals(response.getType(), "not_allowed");

		Assert.assertEquals(response.getMessage(), "Some variant does not have the required inventory");
	}

}
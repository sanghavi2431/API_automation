package validator;

import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public final class SchemaValidator {

	private SchemaValidator() {
	}

	public static void validate(Response response, String schemaPath) {

		response.then().assertThat().body(matchesJsonSchemaInClasspath(schemaPath));
	}
}
package validator;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import io.restassured.response.Response;
import model.response.productreview.ProductReviewResponse;

public final class ProductReviewValidator {

	private ProductReviewValidator() {

	}

	public static void validate(Response response, String expectedComment, int expectedRating) {

		assertEquals(response.statusCode(), 201);
		SchemaValidator.validate(response, "schemas/product-review-schema.json");

		ProductReviewResponse reviewResponse = response.as(ProductReviewResponse.class);

		assertNotNull(reviewResponse);

		assertNotNull(reviewResponse.getReview());

		assertNotNull(reviewResponse.getReview().getId());

		assertEquals(reviewResponse.getReview().getComment(), expectedComment);

		assertEquals(reviewResponse.getReview().getRating().intValue(), expectedRating);

		assertFalse(reviewResponse.getReview().getApproval());

		assertNotNull(reviewResponse.getReview().getCreated_at());

		assertNotNull(reviewResponse.getReview().getUpdated_at());

	}

}
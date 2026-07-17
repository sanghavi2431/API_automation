package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.productreview.Customer;
import model.response.productreview.GetProductReviewResponse;
import model.response.productreview.ReviewList;

public final class GetProductReviewValidator {

	private GetProductReviewValidator() {
	}

	public static void validate(Response response, String expectedProductId) {

		Assert.assertEquals(response.statusCode(), 200);
		SchemaValidator.validate(response, "schemas/get-product-review-schema.json");

		GetProductReviewResponse reviewResponse = response.as(GetProductReviewResponse.class);

		Assert.assertTrue(reviewResponse.getSuccess());

		Assert.assertNotNull(reviewResponse.getData());

		Assert.assertEquals(reviewResponse.getData().getProduct_id(), expectedProductId);

		Assert.assertNotNull(reviewResponse.getData().getReviews());

		for (ReviewList review : reviewResponse.getData().getReviews()) {

			Assert.assertTrue(review.getId().length() > 0);

			Assert.assertTrue(review.getRating() >= 1 && review.getRating() <= 5);

			Assert.assertFalse(review.getComment().isBlank());

			Assert.assertNotNull(review.getApproval());

			Assert.assertNotNull(review.getCreated_at());

			Assert.assertNotNull(review.getUpdated_at());

			if (review.getCustomer() != null) {

				validateCustomer(review.getCustomer());

			}
		}

	}

	private static void validateCustomer(Customer customer) {

		Assert.assertTrue(customer.getId().startsWith("cus_"));

		Assert.assertFalse(customer.getFirstName().isBlank());

		Assert.assertFalse(customer.getLastName().isBlank());

		Assert.assertFalse(customer.getEmail().isBlank());

		Assert.assertNotNull(customer.getHasAccount());

	}

}
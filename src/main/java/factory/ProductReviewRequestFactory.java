package factory;

import java.util.Map;

import requestModel.ProductReviewRequest;

public final class ProductReviewRequestFactory {

    private ProductReviewRequestFactory() {

    }

    /**
     * Creates Product Review request from Excel test data.
     */
    public static ProductReviewRequest create(Map<String, String> data) {

        return ProductReviewRequest.builder()
                .product_id(data.get("product_id"))
                .comment(data.get("comment"))
                .rating(Integer.parseInt(data.get("rating")))
                .build();
    }

}
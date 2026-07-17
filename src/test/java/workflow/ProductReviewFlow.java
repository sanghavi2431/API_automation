package workflow;

import java.util.Map;

import io.restassured.response.Response;
import services.ProductReviewService;
import utils.ExtentReportManager;
import utils.ProductVariantResolver;
import validator.GetProductReviewValidator;
import validator.ProductReviewValidator;

public final class ProductReviewFlow {

    private static final String STORE_URL =
            "https://staging-store.woloo.in";

    private ProductReviewFlow() {

    }

    public static void addReview(Response productResponse,Map<String, String> data) {

        ExtentReportManager.info("Adding Product Review");
        
        ProductVariantResolver.fetchProductId(productResponse, data);

        Response response =
                ProductReviewService.addReview(STORE_URL, data);

        ProductReviewValidator.validate(response, data.get("comment"), Integer.parseInt(data.get("rating")));

        ExtentReportManager.pass("Product Review Added Successfully");
    }

    
    public static void validateReview(Map<String, String> data) {

        ExtentReportManager.info("Verify Product Review");
       

        Response response =
                ProductReviewService.getProductReview(STORE_URL, data);

        GetProductReviewValidator.validate(response, data.get("product_id"));

        ExtentReportManager.pass("Product Review validated Successfully");
    }
}
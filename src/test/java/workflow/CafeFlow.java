package workflow;

import java.util.Map;

import io.restassured.response.Response;
import services.CafeCategories;
import services.CafeCollections;
import services.CafeProducts;
import services.QRService;
import validator.CafeCategoryValidator;
import validator.CafeCollectionsValidator;
import validator.CafeProductsValidator;
import validator.CafeStockLocationValidator;

/**
 * ============================================================================
 * CafeFlow
 * ----------------------------------------------------------------------------
 * Handles all Cafe-related API operations.
 *
 * Responsibilities:
 * 1. Initialize Cafe
 * 2. Fetch Product List
 * 3. Fetch Categories
 * 4. Fetch Collections
 *
 * This class should NOT contain any Cart, Payment or Checkout logic.
 * ============================================================================
 */
public final class CafeFlow {

    /**
     * Store API Base URL
     */
    private static final String STORE_URL = "https://staging-store.woloo.in";

    /**
     * Product fields required from Medusa API.
     */
    private static final String PRODUCT_FIELDS =
            "*variants.calculated_price,variants.inventory_quantity,*variants.images,*categories";

    /**
     * Prevent object creation.
     */
    private CafeFlow() {
    }

    //==========================================================================
    // PUBLIC METHODS
    //==========================================================================

    /**
     * Initializes Cafe and loads all required master data.
     *
     * Flow:
     * 1. Fetch Cafe Stock Location
     * 2. Fetch Products
     * 3. Fetch Categories
     * 4. Fetch Collections
     *
     * @param data Test Data
     * @return Product Response
     */
    public static Response initializeCafe(Map<String, String> data) {

        initializeStockLocation(data);

        return fetchMasterData(data);
    }

    //==========================================================================
    // PRIVATE METHODS
    //==========================================================================

    /**
     * Fetches Cafe Stock Location.
     *
     * Also stores:
     * - Publishable API Key
     * - Product Fields
     *
     * @param data Test Data
     */
    private static void initializeStockLocation(Map<String, String> data) {

        Response response = QRService.getCafeStockLocation(STORE_URL, data);

        CafeStockLocationValidator.validate(response, data);

        data.put("publishableKey",
                response.jsonPath().getString("publishable_api_key"));

        data.put("fields", PRODUCT_FIELDS);
    }

    /**
     * Fetches all master data required before cart creation.
     *
     * APIs:
     * - Products
     * - Categories
     * - Collections
     *
     * @param data Test Data
     * @return Product Response
     */
    private static Response fetchMasterData(Map<String, String> data) {
    	data.put("fields", "*variants.calculated_price, variants.inventory_quantity,*variants.images,*categories");

        Response productResponse = fetchProducts(data);

        fetchCategories(data);

        fetchCollections(data);

        return productResponse;
    }

    /**
     * Fetches available Cafe Products.
     *
     * @param data Test Data
     * @return Product Response
     */
    private static Response fetchProducts(Map<String, String> data) {

        Response response = CafeProducts.getCafeProducts(STORE_URL, data);

        CafeProductsValidator.validate(response);

        return response;
    }

    /**
     * Fetches available Product Categories.
     *
     * @param data Test Data
     */
    private static void fetchCategories(Map<String, String> data) {

        Response response =
                CafeCategories.getCafeProductCategories(STORE_URL, data);

        CafeCategoryValidator.validate(response);
    }

    /**
     * Fetches Product Collections.
     *
     * @param data Test Data
     */
    private static void fetchCollections(Map<String, String> data) {

        Response response =
                CafeCollections.getCafeProductCollections(STORE_URL, data);

        CafeCollectionsValidator.validate(response);
    }

}
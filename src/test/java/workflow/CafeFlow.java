package workflow;

import java.util.Map;
import java.util.Objects;

import framework.context.ExecutionState;
import framework.context.ExecutionStateSupport;
import framework.context.JourneyContext;
import framework.context.JourneyContextSupport;
import framework.context.JourneyLifecycleLogger;
import framework.context.LegacyDataKeys;
import io.restassured.response.Response;
import services.CafeCategories;
import services.CafeCollections;
import services.CafeProducts;
import services.QRService;
import utils.ExtentReportManager;
import validator.CafeCategoryValidator;
import validator.CafeCollectionsValidator;
import validator.CafeProductsValidator;
import validator.CafeStockLocationValidator;

/**
 * Coordinates cafe initialization and the master-data calls required before
 * cart operations.
 *
 * <p>The flow retains the existing mutable map contract and mirrors reusable
 * cafe values into the current thread's {@link JourneyContext} only after the
 * full initialization sequence has completed successfully.</p>
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

    private static final String PUBLISHABLE_API_KEY_PATH = "publishable_api_key";
    private static final String STOCK_LOCATION_ID_PATH = "stock_location.id";
    private static final String SALES_CHANNEL_ID_PATH = "sales_channel.id";
    private static final String VENDOR_ID_PATH = "vendor.id";

    private static final String STOCK_LOCATION_ID_ATTRIBUTE = "cafe.stock_location_id";
    private static final String SALES_CHANNEL_ID_ATTRIBUTE = "cafe.sales_channel_id";
    private static final String VENDOR_ID_ATTRIBUTE = "cafe.vendor_id";
    private static final String PRODUCT_FIELDS_ATTRIBUTE = "cafe.product_fields";

    /**
     * Prevent object creation.
     */
    private CafeFlow() {
        // Utility class.
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
     * @param data mutable test data used by the existing workflow
     * @return validated product response
     */
    public static Response initializeCafe(Map<String, String> data) {
        Objects.requireNonNull(data, "Cafe data must not be null");

        JourneyLifecycleLogger.start("cafe initialization");

        Response stockLocationResponse = initializeStockLocation(data);
        Response productResponse = fetchMasterData(data);

        updateJourneyContext(data, stockLocationResponse);
        updateCafeInitializationState(true);

        JourneyLifecycleLogger.complete("Cafe initialization");
        return productResponse;
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
     * @param data mutable test data used by the existing workflow
     * @return validated cafe stock-location response
     */
    private static Response initializeStockLocation(Map<String, String> data) {

        ExtentReportManager.info("Resolving cafe stock location.");
        Response response = QRService.getCafeStockLocation(STORE_URL, data);

        CafeStockLocationValidator.validate(response, data);

        data.put(LegacyDataKeys.PUBLISHABLE_KEY, response.jsonPath().getString(PUBLISHABLE_API_KEY_PATH));

        data.put(LegacyDataKeys.FIELDS, PRODUCT_FIELDS);
        return response;
    }

    /**
     * Fetches all master data required before cart creation.
     *
     * APIs:
     * - Products
     * - Categories
     * - Collections
     *
     * @param data mutable test data used by the existing workflow
     * @return validated product response
     */
    private static Response fetchMasterData(Map<String, String> data) {
    	data.put(LegacyDataKeys.FIELDS, "*variants.calculated_price, variants.inventory_quantity,*variants.images,*categories");

        Response productResponse = fetchProducts(data);

        fetchCategories(data);

        fetchCollections(data);

        return productResponse;
    }

    /**
     * Fetches available Cafe Products.
     *
     * @param data mutable test data used by the existing workflow
     * @return validated product response
     */
    private static Response fetchProducts(Map<String, String> data) {

        ExtentReportManager.info("Fetching cafe products.");
        Response response = CafeProducts.getCafeProducts(STORE_URL, data);

        CafeProductsValidator.validate(response);

        return response;
    }

    /**
     * Fetches available Product Categories.
     *
     * @param data mutable test data used by the existing workflow
     */
    private static void fetchCategories(Map<String, String> data) {

        ExtentReportManager.info("Fetching cafe product categories.");
        Response response =
                CafeCategories.getCafeProductCategories(STORE_URL, data);

        CafeCategoryValidator.validate(response);
    }

    /**
     * Fetches Product Collections.
     *
     * @param data mutable test data used by the existing workflow
     */
    private static void fetchCollections(Map<String, String> data) {

        ExtentReportManager.info("Fetching cafe product collections.");
        Response response =
                CafeCollections.getCafeProductCollections(STORE_URL, data);

        CafeCollectionsValidator.validate(response);
    }

    /**
     * Copies reusable cafe values into the current thread's journey context.
     * This method does not change the legacy map values consumed by existing
     * workflows and service classes.
     */
    private static void updateJourneyContext(
            Map<String, String> data,
            Response stockLocationResponse) {

        JourneyContextSupport.setIfText(
                JourneyContext::setPublishableApiKey,
                data.get(LegacyDataKeys.PUBLISHABLE_KEY));

        JourneyContextSupport.setIfText(
                JourneyContext::setRegionId,
                data.get(LegacyDataKeys.REGION_ID));

        JourneyContextSupport.putIfText(
                STOCK_LOCATION_ID_ATTRIBUTE,
                stockLocationResponse.jsonPath().getString(STOCK_LOCATION_ID_PATH));
        JourneyContextSupport.putIfText(
                SALES_CHANNEL_ID_ATTRIBUTE,
                stockLocationResponse.jsonPath().getString(SALES_CHANNEL_ID_PATH));
        JourneyContextSupport.putIfText(
                VENDOR_ID_ATTRIBUTE,
                stockLocationResponse.jsonPath().getString(VENDOR_ID_PATH));
        JourneyContextSupport.putIfText(
                PRODUCT_FIELDS_ATTRIBUTE, data.get(LegacyDataKeys.FIELDS));
    }

    /** Updates the cafe-initialization milestone for the current journey. */
    private static void updateCafeInitializationState(boolean cafeInitialized) {
        ExecutionStateSupport.update(ExecutionState::setCafeInitialized, cafeInitialized);
    }

}

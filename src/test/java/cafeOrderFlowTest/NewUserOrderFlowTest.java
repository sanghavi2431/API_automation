package cafeOrderFlowTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import utils.ExcelUtil;
import workflow.OrderPlacementFlowTest;

@Listeners(utils.ExtentTestListener.class)
public class NewUserOrderFlowTest {

	private static final String EXCEL_PATH = "./src/test/resources/TestData.xlsx";

	/**
	 * Generic DataProvider method to read Excel data.
	 *
	 * @param sheetName Excel Sheet Name
	 * @return Object[][]
	 * @throws Exception
	 */
	private static Object[][] getTestData(String sheetName) throws Exception {

		List<Map<String, String>> data = ExcelUtil.getData(EXCEL_PATH, sheetName);

		return data.stream().map(row -> new Object[] { row }).toArray(Object[][]::new);
	}

	// ==========================================================================
	// DATA PROVIDERS
	// ==========================================================================

	@DataProvider(name = "cafe")
	public static Object[][] cafeData() throws Exception {
		return getTestData("CafeData");
	}

	@DataProvider(name = "invalidPromocode")
	public static Object[][] invalidPromocodeData() throws Exception {
		return getTestData("InvalidPromocode");
	}

	@DataProvider(name = "deletePromocode")
	public static Object[][] deletePromocodeData() throws Exception {
		return getTestData("DeletePromocode");
	}

	@DataProvider(name = "insufficientStock")
	public static Object[][] insufficientStockData() throws Exception {
		return getTestData("InsufficientStock");
	}

	@DataProvider(name = "deleteProductFromCart")
	public static Object[][] deleteProductFromCartData() throws Exception {
		return getTestData("DeleteProductFromCart");
	}

	// ==========================================================================
	// TEST CASES
	// ==========================================================================

	@Test(dataProvider = "cafe")
	public void TC01_placeOrderTest(Map<String, String> data) {

		OrderPlacementFlowTest.placeOrder(data);
	}

	@Test(dataProvider = "invalidPromocode")
	public void TC02_applyInvalidPromotionTest(Map<String, String> data) {

		OrderPlacementFlowTest.placeOrderWithInvalidPromo(data);
	}

	@Test(dataProvider = "deletePromocode")
	public void TC03_deletePromotionTest(Map<String, String> data) {

		OrderPlacementFlowTest.deletePromotion(data);
	}

	@Test(dataProvider = "deleteProductFromCart")
	public void TC04_deleteProductFromCartTest(Map<String, String> data) {

		OrderPlacementFlowTest.deleteCartProduct(data);
	}

	@Test(dataProvider = "insufficientStock")
	public void TC05_validateInsufficientInventoryTest(Map<String, String> data) {

		OrderPlacementFlowTest.validateInsufficientInventory(data);
	}

	@Test(dataProvider = "deleteProductFromCart")
	public void TC06_deleteCartPaymentTest(Map<String, String> data) {

		OrderPlacementFlowTest.deleteProductPayment(data);
	}

	@Test
	public void TC07_invalidMobileNoValidationTest() {

		Map<String, String> data = new HashMap<String, String>();
		OrderPlacementFlowTest.invalidMobileNoErroHandling(data);
	}
	
	@Test
	public void TC08_invalidOTPValidationTest() {

		Map<String, String> data = new HashMap<String, String>();
		OrderPlacementFlowTest.invalidOTPErroHandling(data);
	}

}
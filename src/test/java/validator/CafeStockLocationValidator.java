package validator;

import java.util.Map;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.cafeLogin.Address;
import model.response.cafeLogin.CafeStockLocationResponse;
import model.response.cafeLogin.DayHours;
import model.response.cafeLogin.Metadata;
import model.response.cafeLogin.OperatingHours;
import model.response.cafeLogin.SalesChannel;
import model.response.cafeLogin.StockLocation;
import model.response.cafeLogin.Vendor;


public final class CafeStockLocationValidator {

	private CafeStockLocationValidator() {
	}

	public static void validate(Response response, Map<String, String> data) {

		Assert.assertEquals(response.statusCode(), 200);

		SchemaValidator.validate(response, "schemas/cafe-stock-location-schema.json");

		CafeStockLocationResponse apiResponse = response.as(CafeStockLocationResponse.class);

		Assert.assertNotNull(apiResponse);

		validateVendor(apiResponse.getVendor());

		validateStockLocation(apiResponse.getStockLocation());

		validateSalesChannel(apiResponse.getSalesChannel());

		validateApiKey(apiResponse.getPublishableApiKey());
	}

	// =================================================

	private static void validateVendor(Vendor vendor) {

		Assert.assertNotNull(vendor);

		Assert.assertTrue(vendor.getId().startsWith("vnd_"));

		Assert.assertFalse(vendor.getName().isBlank());

		Assert.assertFalse(vendor.getAddress().isBlank());

		Assert.assertFalse(vendor.getCity().isBlank());

		Assert.assertTrue(vendor.getPincode().matches("\\d{6}"));

		Assert.assertTrue(vendor.getLatitude() >= -90 && vendor.getLatitude() <= 90);

		Assert.assertTrue(vendor.getLongitude() >= -180 && vendor.getLongitude() <= 180);

		Assert.assertTrue(vendor.getServiceRadiusKm() > 0);

		Assert.assertEquals(vendor.getStatus(), "active");

		Assert.assertFalse(vendor.getVasyErpBranchCode().isBlank());

		validateOperatingHours(vendor.getOperatingHours());
	}

	// =================================================

	private static void validateOperatingHours(OperatingHours hours) {

		Assert.assertNotNull(hours);

		validateDay(hours.getMonday());
		validateDay(hours.getTuesday());
		validateDay(hours.getWednesday());
		validateDay(hours.getThursday());
		validateDay(hours.getFriday());
		validateDay(hours.getSaturday());
		validateDay(hours.getSunday());
	}

	private static void validateDay(DayHours day) {

		Assert.assertNotNull(day);

		Assert.assertTrue(day.getOpen().matches("^([01]\\d|2[0-3]):[0-5]\\d$"));

		Assert.assertTrue(day.getClose().matches("^([01]\\d|2[0-3]):[0-5]\\d$"));
	}

	// =================================================

	private static void validateStockLocation(StockLocation location) {

		Assert.assertNotNull(location);

		Assert.assertTrue(location.getId().startsWith("sloc_"));

		Assert.assertFalse(location.getName().isBlank());

		Address address = location.getAddress();

		Assert.assertNotNull(address);

		Assert.assertTrue(address.getId().startsWith("laddr_"));

		Assert.assertFalse(address.getAddress1().isBlank());

		Assert.assertFalse(address.getCity().isBlank());

		Assert.assertEquals(address.getCountryCode(), "IN");

		Assert.assertTrue(address.getPostalCode().matches("\\d{6}"));

		Metadata metadata = location.getMetadata();

		Assert.assertNotNull(metadata);

		Assert.assertTrue(metadata.getLatitude() >= -90 && metadata.getLatitude() <= 90);

		Assert.assertTrue(metadata.getLongitude() >= -180 && metadata.getLongitude() <= 180);

		Assert.assertTrue(metadata.getEstimateDeliveryTime() > 0);
	}

	// =================================================

	private static void validateSalesChannel(SalesChannel channel) {

		Assert.assertNotNull(channel);

		Assert.assertTrue(channel.getId().startsWith("sc_"));

		Assert.assertFalse(channel.getName().isBlank());

		Assert.assertFalse(channel.getDescription().isBlank());

		Assert.assertNotNull(channel.getIsDisabled());
	}

	// =================================================

	private static void validateApiKey(String apiKey) {

		Assert.assertNotNull(apiKey);

		Assert.assertFalse(apiKey.isBlank());

		Assert.assertTrue(apiKey.startsWith("pk_"));
	}
}
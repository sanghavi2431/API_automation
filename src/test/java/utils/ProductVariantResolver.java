package utils;

import java.util.List;
import java.util.Map;

import io.restassured.response.Response;
import model.response.product.Product;
import model.response.product.ProductListResponse;
import model.response.product.VariantOptionLookup;

/**
 *
 * Two cases: - Single-variant products (no real choice to make): use the
 * product's default_variant_id directly. - Multi-variant products (user picks
 * e.g. Flavour + Size): match the user's selections against
 * variant_options_lookup to find the corresponding variant_id.
 */
public final class ProductVariantResolver {

	private ProductVariantResolver() {
	}

	/**
	 * The single entry point: given the full product list (as returned by GET
	 * /products) and what the user is looking for, find the matching product and
	 * return the variant_id to send to add-line-items.
	 *
	 * Matches by title first (case-insensitive), falling back to handle - e.g.
	 * passing "420 SOAN PAPDI" matches product.getTitle(), or passing
	 * "420-soan-papdi" matches product.getHandle(). Either works.
	 *
	 * @param products          the full list from ProductListResponse.getProducts()
	 * @param productIdentifier the product's title or handle, as the caller knows
	 *                          it
	 * @param desiredOptions    the user's option selections (e.g. {"flavour":"Duetz
	 *                          Raspberry", "size":"60ml"}), or null/empty if the
	 *                          product has no meaningful choice to make
	 * @return the variant_id to pass to AddLineItemRequest
	 */
	public static String findVariantId(List<Product> products, String productIdentifier,
			Map<String, String> desiredOptions) {

		Product product = findProduct(products, productIdentifier);

		boolean hasOptionsToChooseFrom = product.getVariantOptionsLookup() != null
				&& product.getVariantOptionsLookup().size() > 1;

		if (!hasOptionsToChooseFrom) {
			// Only one variant exists (or none listed) - nothing to disambiguate,
			// regardless of whether the caller passed desiredOptions or not.
			return resolveDefaultVariant(product);
		}

		if (desiredOptions == null || desiredOptions.isEmpty()) {
			throw new IllegalArgumentException(
					"Product '" + productIdentifier + "' has " + product.getVariantOptionsLookup().size()
							+ " variants to choose from, but no option selections were provided. "
							+ "Pass desiredOptions (e.g. flavour/size) to disambiguate.");
		}

		return resolveVariantByOptions(product, desiredOptions);
	}

	/** Convenience overload for single-variant products - no options needed. */
	public static String findVariantId(List<Product> products, String productIdentifier) {
		return findVariantId(products, productIdentifier, null);
	}

	// =====================================================

	private static Product findProduct(List<Product> products, String productIdentifier) {

		return products.stream().filter(p -> matchesIdentifier(p, productIdentifier)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						"No product found matching '" + productIdentifier + "' (checked title and handle)"));
	}

	private static boolean matchesIdentifier(Product product, String identifier) {

		if (product.getTitle() != null && product.getTitle().equalsIgnoreCase(identifier)) {
			return true;
		}

		return product.getHandle() != null && product.getHandle().equalsIgnoreCase(identifier);
	}

	// =====================================================

	/**
	 * Use when the product has no meaningful options to choose from
	 * (variant_options_lookup has exactly one entry, or the caller doesn't care
	 * which variant and just wants "the" default one).
	 */
	public static String resolveDefaultVariant(Product product) {

		if (product.getDefaultVariantId() == null) {
			throw new IllegalStateException("Product " + product.getId() + " has no default_variant_id");
		}

		return product.getDefaultVariantId();
	}

	/**
	 * Use when the product has options the user actively selects (e.g.
	 * Flavour="Duetz Raspberry", Size="60ml"). Matches every entry in
	 * desiredOptions against each variant_options_lookup entry's attribute map and
	 * returns the variant_id of the single match.
	 *
	 * @param product        the product being added to cart
	 * @param desiredOptions the user's selections, keyed by option name exactly as
	 *                       it appears in variant_options_lookup (e.g. "flavour",
	 *                       "size", "type" - case-sensitive, matching whatever key
	 *                       the API actually used)
	 * @throws IllegalArgumentException if zero or more than one variant matches
	 */
	public static String resolveVariantByOptions(Product product, Map<String, String> desiredOptions) {

		if (product.getVariantOptionsLookup() == null || product.getVariantOptionsLookup().isEmpty()) {
			throw new IllegalStateException("Product " + product.getId() + " has no variant_options_lookup entries");
		}

		String matchedVariantId = null;

		for (VariantOptionLookup lookup : product.getVariantOptionsLookup()) {

			if (matchesAll(lookup, desiredOptions)) {

				if (matchedVariantId != null) {
					throw new IllegalArgumentException("Ambiguous variant match for " + desiredOptions
							+ " - matched both " + matchedVariantId + " and " + lookup.getVariantId());
				}

				matchedVariantId = lookup.getVariantId();
			}
		}

		if (matchedVariantId == null) {
			throw new IllegalArgumentException(
					"No variant found for product " + product.getId() + " matching options " + desiredOptions);
		}

		return matchedVariantId;
	}

	// =====================================================

	private static boolean matchesAll(VariantOptionLookup lookup, Map<String, String> desiredOptions) {

		for (Map.Entry<String, String> desired : desiredOptions.entrySet()) {

			String actual = lookup.getAttribute(desired.getKey());

			if (actual == null || !actual.equalsIgnoreCase(desired.getValue())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns Product ID based on Product Title or Handle.
	 *
	 * @param products          List of products returned from Get Store Products
	 *                          API
	 * @param productIdentifier Product Title or Handle
	 * @return Product ID
	 */
	public static String findProductId(List<Product> products, String productIdentifier) {

		Product product = findProduct(products, productIdentifier);

		return product.getId();
	}

	/**
	 * Fetches Product ID from Get Store Products response.
	 *
	 * @param apiResponse Get Store Products API Response
	 * @param data        Test Data
	 */
	public static void fetchProductId(Response apiResponse, Map<String, String> data) {

		ProductListResponse response = apiResponse.as(ProductListResponse.class);

		String productId = findProductId(response.getProducts(), data.get("productName"));

		data.put("product_id", productId);
	}

	public static void addSingleVariantProduct(Response apiResponse, Map<String, String> data) {

		ProductListResponse response = apiResponse.as(ProductListResponse.class);

		String variantId = ProductVariantResolver.findVariantId(response.getProducts(), data.get("productName"));// "420
																													// SOAN
																													// PAPDI"

		data.put("variant_id", variantId);

	}

	public static void addMultipleVariantProduct(Response apiResponse, Map<String, String> data) {

		ProductListResponse response = apiResponse.as(ProductListResponse.class);

		Map<String, String> userSelection = Map.of("flavour", "Duetz Raspberry", "size", "60ml");

		String variantId = ProductVariantResolver.findVariantId(response.getProducts(), "Amul IC Stk", userSelection);

		data.put("variant_id", variantId);

	}

}
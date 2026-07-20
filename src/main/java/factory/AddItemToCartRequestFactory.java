package factory;

import requestModel.AddItemToCartRequest;

public final class AddItemToCartRequestFactory {

	private AddItemToCartRequestFactory() {
	}

	/**
	 * Creates Add Item To Cart Request
	 */
	public static AddItemToCartRequest createRequest(int quantity, String variantId) {

		AddItemToCartRequest request = new AddItemToCartRequest();

		request.setQuantity(quantity);
		request.setVariantId(variantId);

		return request;
	}

}

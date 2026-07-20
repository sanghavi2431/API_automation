package framework.context;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-confined state shared by the steps of one API test journey.
 *
 * <p>
 * Instances are owned by {@code JourneyManager}; each executing test thread
 * receives an independent context. The attribute map supports incremental
 * migration from string-keyed test data to typed journey state.
 * </p>
 */
public final class JourneyContext {

	private String medusaToken;
	private String clientToken;
	private String userId;
	private String customerId;
	private String regionId;
	private String publishableApiKey;

	private String productId;
	private String variantId;
	private String cartId;
	private String lineItemId;

	private String shippingOptionId;

	private String providerId;
	private String paymentCollectionId;
	private String paymentSessionId;

	private String orderId;

	private final Map<String, Object> attributes = new ConcurrentHashMap<>();

	public String getMedusaToken() {
		return medusaToken;
	}

	public void setMedusaToken(String medusaToken) {
		this.medusaToken = medusaToken;
	}

	/** Legacy alias retained for callers that use a generic access-token name. */
	public String getAccessToken() {
		return medusaToken;
	}

	/** Legacy alias retained for callers that use a generic access-token name. */
	public void setAccessToken(String accessToken) {
		this.medusaToken = accessToken;
	}

	public String getClientToken() {
		return clientToken;
	}

	public void setClientToken(String clientToken) {
		this.clientToken = clientToken;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getPublishableApiKey() {
		return publishableApiKey;
	}

	public void setPublishableApiKey(String publishableApiKey) {
		this.publishableApiKey = publishableApiKey;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getVariantId() {
		return variantId;
	}

	public void setVariantId(String variantId) {
		this.variantId = variantId;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public String getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(String lineItemId) {
		this.lineItemId = lineItemId;
	}

	public String getShippingOptionId() {
		return shippingOptionId;
	}

	public void setShippingOptionId(String shippingOptionId) {
		this.shippingOptionId = shippingOptionId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getPaymentCollectionId() {
		return paymentCollectionId;
	}

	public void setPaymentCollectionId(String paymentCollectionId) {
		this.paymentCollectionId = paymentCollectionId;
	}

	public String getPaymentSessionId() {
		return paymentSessionId;
	}

	public void setPaymentSessionId(String paymentSessionId) {
		this.paymentSessionId = paymentSessionId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/** Stores a non-null journey-scoped attribute. */
	public void put(String key, Object value) {
		attributes.put(Objects.requireNonNull(key, "Attribute key must not be null"),
				Objects.requireNonNull(value, "Attribute value must not be null"));
	}

	/** Retrieves a journey-scoped attribute. */
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) attributes.get(key);
	}

	public void remove(String key) {
		attributes.remove(Objects.requireNonNull(key, "Attribute key must not be null"));
	}

	public boolean contains(String key) {
		return attributes.containsKey(Objects.requireNonNull(key, "Attribute key must not be null"));
	}

	public boolean hasAccessToken() {
		return hasText(medusaToken);
	}

	public boolean hasCustomer() {
		return hasText(customerId) || hasText(userId);
	}

	public boolean hasProduct() {
		return hasText(productId);
	}

	public boolean hasVariant() {
		return hasText(variantId);
	}

	public boolean hasCart() {
		return hasText(cartId);
	}

	public boolean hasShippingOption() {
		return hasText(shippingOptionId);
	}

	public boolean hasProvider() {
		return hasText(providerId);
	}

	public boolean hasPaymentSession() {
		return hasText(paymentSessionId);
	}

	public boolean hasOrder() {
		return hasText(orderId);
	}

	/** Clears all typed and dynamic state so this context can be safely reused. */
	public void clear() {
		medusaToken = null;
		clientToken = null;
		userId = null;
		customerId = null;
		regionId = null;
		publishableApiKey = null;
		productId = null;
		variantId = null;
		cartId = null;
		lineItemId = null;
		shippingOptionId = null;
		providerId = null;
		paymentCollectionId = null;
		paymentSessionId = null;
		orderId = null;
		attributes.clear();
	}

	private boolean hasText(String value) {
		return value != null && !value.isBlank();
	}
}

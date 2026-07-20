package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.orderset.Order;
import model.response.orderset.OrderLineItem;
import model.response.orderset.OrderPaymentCollection;
import model.response.orderset.OrderSet;
import model.response.orderset.OrderSetResponse;


public final class OrderSetValidator {

	private OrderSetValidator() {
	}

	/**
	 * Shared by both GetOrderSet-by-id and SplitCompleteCart - both return
	 * an identical order_set shape (confirmed across four captured payloads
	 * with different cart/order ids).
	 */
	public static void validate(Response apiResponse) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/order-set-response-schema.json");

		OrderSetResponse response = apiResponse.as(OrderSetResponse.class);
		Assert.assertNotNull(response);

		validateOrderSetStructure(response.getOrderSet());
	}

	/**
	 * Public so OrderSetListValidator can apply the exact same per-order-set
	 * checks to every entry in an order_sets[] list, rather than duplicating
	 * this logic - a schema/business-rule change here only needs to happen
	 * once and both validators pick it up.
	 */
	public static void validateOrderSetStructure(OrderSet orderSet) {

		Assert.assertNotNull(orderSet, "order_set is null");

		Assert.assertNotNull(orderSet.getId());

		Assert.assertTrue(orderSet.getId().startsWith("ordset_"));

		Assert.assertNotNull(orderSet.getCartId());

		Assert.assertTrue(orderSet.getCartId().startsWith("cart_"));

		Assert.assertNotNull(orderSet.getCart(), "order_set.cart is null");

		// Business rule: cart_id must actually match the expanded cart
		// object's own id - a mismatch would mean the two were populated
		// from different sources and drifted apart.
		Assert.assertEquals(orderSet.getCart().getId(), orderSet.getCartId(),
				"order_set.cart.id does not match order_set.cart_id");

		Assert.assertNotNull(orderSet.getPaymentCollectionId());

		Assert.assertNotNull(orderSet.getPaymentCollection(), "order_set.payment_collection is null");

		Assert.assertEquals(orderSet.getPaymentCollection().getId(), orderSet.getPaymentCollectionId(),
				"order_set.payment_collection.id does not match order_set.payment_collection_id");

		Assert.assertNotNull(orderSet.getOrders(), "orders list is null");

		Assert.assertFalse(orderSet.getOrders().isEmpty(), "order_set has no orders");

		for (Order order : orderSet.getOrders()) {
			validateOrder(order, orderSet);
		}

		validateOrderSetTotalsMatchOrders(orderSet);
	}

	// =====================================================

	private static void validateOrder(Order order, OrderSet orderSet) {

		Assert.assertNotNull(order.getId());

		Assert.assertTrue(order.getId().startsWith("order_"));

		Assert.assertNotNull(order.getStatus());

		Assert.assertNotNull(order.getTotal());

		Assert.assertTrue(order.getTotal() >= 0);

		Assert.assertNotNull(order.getItems(), "order.items is null");

		Assert.assertFalse(order.getItems().isEmpty(), "order has no items");

		double expectedItemTotal = 0.0;

		for (OrderLineItem item : order.getItems()) {

			Assert.assertNotNull(item.getId());

			Assert.assertTrue(item.getId().startsWith("ordli_"));

			Assert.assertNotNull(item.getVariant(), "item.variant is null");

			Assert.assertNotNull(item.getProduct(), "item.product is null");

			Assert.assertNotNull(item.getQuantity());

			Assert.assertTrue(item.getQuantity() > 0);

			Assert.assertNotNull(item.getUnitPrice());

			Assert.assertNotNull(item.getTotal());

			expectedItemTotal += item.getTotal();
		}

		double delta = 0.01;

		// Business rule: order.total must equal the sum of its line items'
		// individual totals - a mismatch means the order-level aggregate
		// wasn't recalculated correctly from its items.
		Assert.assertEquals(order.getTotal(), expectedItemTotal, delta,
				"order.total does not match the sum of item totals");

		Assert.assertNotNull(order.getPaymentCollections(), "order.payment_collections is null");

		for (OrderPaymentCollection paymentCollection : order.getPaymentCollections()) {

			Assert.assertNotNull(paymentCollection.getId());

			// Business rule: every payment collection referenced by an order
			// should trace back to the same payment_collection_id the
			// order_set itself points to - a different id here would mean
			// the order somehow attached to an unrelated payment collection.
			Assert.assertEquals(paymentCollection.getId(), orderSet.getPaymentCollectionId(),
					"order.payment_collections[].id does not match order_set.payment_collection_id");
		}
	}

	// =====================================================

	/**
	 * Business rule: order_set's STRING-typed totals must numerically equal
	 * the sum of the NUMBER-typed totals across orders[] - this is the one
	 * check that specifically exists because of the string/number type
	 * split documented on OrderSet.java. Schema validation confirms each
	 * field is the *type* the API promised; this confirms the two levels
	 * actually agree on the *value*.
	 */
	private static void validateOrderSetTotalsMatchOrders(OrderSet orderSet) {

		double orderSetTotal = Double.parseDouble(orderSet.getTotal());

		double sumOfOrderTotals = orderSet.getOrders().stream()
				.mapToDouble(Order::getTotal)
				.sum();

		double delta = 0.01;

		Assert.assertEquals(orderSetTotal, sumOfOrderTotals, delta,
				"order_set.total (string) does not match the sum of orders[].total (numbers)");
	}
}
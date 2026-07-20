package framework.context;

public class ExecutionState {

    // ==========================================================
    // Authentication
    // ==========================================================

    private boolean authenticated;

    // ==========================================================
    // Cafe Initialization
    // ==========================================================

    private boolean cafeInitialized;

    // ==========================================================
    // Cart
    // ==========================================================

    private boolean cartCreated;
    private boolean productAdded;

    // ==========================================================
    // Promotion
    // ==========================================================

    private boolean promotionApplied;

    // ==========================================================
    // Checkout
    // ==========================================================

    private boolean checkoutCompleted;

    // ==========================================================
    // Payment
    // ==========================================================

    private boolean paymentProviderLoaded;
    private boolean paymentCollectionCreated;
    private boolean paymentSessionCreated;
    private boolean paymentCompleted;

    // ==========================================================
    // Order
    // ==========================================================

    private boolean orderValidated;

    // ==========================================================
    // Product Review
    // ==========================================================

    private boolean reviewSubmitted;

    public ExecutionState() {
        reset();
    }

    /**
     * Resets the complete execution state.
     */
    public void reset() {

        authenticated = false;

        cafeInitialized = false;

        cartCreated = false;
        productAdded = false;

        promotionApplied = false;

        checkoutCompleted = false;

        paymentProviderLoaded = false;
        paymentCollectionCreated = false;
        paymentSessionCreated = false;
        paymentCompleted = false;

        orderValidated = false;

        reviewSubmitted = false;
    }

    // ==========================================================
    // Helper Methods
    // ==========================================================

    public boolean isCheckoutReady() {
        return authenticated
                && cafeInitialized
                && cartCreated
                && productAdded;
    }

    public boolean isPaymentReady() {
        return checkoutCompleted
                && paymentProviderLoaded
                && paymentCollectionCreated
                && paymentSessionCreated;
    }

    public boolean isJourneyCompleted() {
        return paymentCompleted
                && orderValidated;
    }

    // ==========================================================
    // Getters & Setters
    // ==========================================================

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isCafeInitialized() {
        return cafeInitialized;
    }

    public void setCafeInitialized(boolean cafeInitialized) {
        this.cafeInitialized = cafeInitialized;
    }

    public boolean isCartCreated() {
        return cartCreated;
    }

    public void setCartCreated(boolean cartCreated) {
        this.cartCreated = cartCreated;
    }

    public boolean isProductAdded() {
        return productAdded;
    }

    public void setProductAdded(boolean productAdded) {
        this.productAdded = productAdded;
    }

    public boolean isPromotionApplied() {
        return promotionApplied;
    }

    public void setPromotionApplied(boolean promotionApplied) {
        this.promotionApplied = promotionApplied;
    }

    public boolean isCheckoutCompleted() {
        return checkoutCompleted;
    }

    public void setCheckoutCompleted(boolean checkoutCompleted) {
        this.checkoutCompleted = checkoutCompleted;
    }

    public boolean isPaymentProviderLoaded() {
        return paymentProviderLoaded;
    }

    public void setPaymentProviderLoaded(boolean paymentProviderLoaded) {
        this.paymentProviderLoaded = paymentProviderLoaded;
    }

    public boolean isPaymentCollectionCreated() {
        return paymentCollectionCreated;
    }

    public void setPaymentCollectionCreated(boolean paymentCollectionCreated) {
        this.paymentCollectionCreated = paymentCollectionCreated;
    }

    public boolean isPaymentSessionCreated() {
        return paymentSessionCreated;
    }

    public void setPaymentSessionCreated(boolean paymentSessionCreated) {
        this.paymentSessionCreated = paymentSessionCreated;
    }

    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }

    public void setPaymentCompleted(boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }

    public boolean isOrderValidated() {
        return orderValidated;
    }

    public void setOrderValidated(boolean orderValidated) {
        this.orderValidated = orderValidated;
    }

    public boolean isReviewSubmitted() {
        return reviewSubmitted;
    }

    public void setReviewSubmitted(boolean reviewSubmitted) {
        this.reviewSubmitted = reviewSubmitted;
    }

    @Override
    public String toString() {
        return "ExecutionState{" +
                "authenticated=" + authenticated +
                ", cafeInitialized=" + cafeInitialized +
                ", cartCreated=" + cartCreated +
                ", productAdded=" + productAdded +
                ", promotionApplied=" + promotionApplied +
                ", checkoutCompleted=" + checkoutCompleted +
                ", paymentProviderLoaded=" + paymentProviderLoaded +
                ", paymentCollectionCreated=" + paymentCollectionCreated +
                ", paymentSessionCreated=" + paymentSessionCreated +
                ", paymentCompleted=" + paymentCompleted +
                ", orderValidated=" + orderValidated +
                ", reviewSubmitted=" + reviewSubmitted +
                '}';
    }
}
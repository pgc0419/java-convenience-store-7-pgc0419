package store.Model;

public class Products {
    private final String productName;
    private final int quantity;
    private final String productOfPromotion;

    public Products(String productName, int quantity, String productOfPromotion) {
        this.productName = productName;
        this.quantity = quantity;
        this.productOfPromotion = productOfPromotion;
    }
}

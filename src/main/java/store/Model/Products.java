package store.Model;

public class Products {
    private final String productName;
    private final String quantity;
    private final String productOfPromotion;

    public Products(String productName, String quantity, String productOfPromotion) {
        this.productName = productName;
        this.quantity = quantity;
        this.productOfPromotion = productOfPromotion;
    }

    public String getProductName() {
        return productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getProductOfPromotion() {
        return productOfPromotion;
    }
}

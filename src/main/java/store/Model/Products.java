package store.Model;

public class Products {
    private String productName;
    private String quantity;
    private String productOfPromotion;

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

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

package store.Model;

public class Promotions {
    private String promotionName;
    private String buy;
    private String get;
    private String productOfPromotion;

    public Promotions(String productOfPromotion) {
        this.productOfPromotion = productOfPromotion;
    }
}

package store.Model;

public class Promotions {
    private final String promotionName;
    private final String buy;
    private final String get;

    public Promotions(String promotionName, String buy, String get) {
        this.promotionName = promotionName;
        this.buy = buy;
        this.get = get;
    }
}

package store.Model;

public class Promotions {
    private final String promotionName;
    private final int buy;
    private final int get;

    public Promotions(String promotionName, int buy, int get) {
        this.promotionName = promotionName;
        this.buy = buy;
        this.get = get;
    }
}

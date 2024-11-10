package store.Controller;

import store.Model.*;
import store.Reader.*;

import java.util.List;
import java.util.Map;

public class PayController {
    private final ProductsReader productsReader;
    private final PromotionsController promotionsController;

    public PayController(ProductsReader productsReader, PromotionsController promotionsController) {
        this.productsReader = productsReader;
        this.promotionsController = promotionsController;
    }

    public int calculateTotalPayment() {
        List<Products> products = productsReader.readProducts();

        promotionsController.classifyAndApplyPromotions();
        List<Map<String, Object>> calculatedPromotions = promotionsController.getAllPromotionResults();

        int totalPayment = 0;
        for (Map<String, Object> promotionResult : calculatedPromotions) {
            String productName = (String) promotionResult.get("productName");
            int buy = (int) promotionResult.get("buy");

            Products product = products.stream()
                    .filter(p -> p.getProductName().equals(productName))
                    .findFirst()
                    .orElseThrow();

            int price = Integer.parseInt(product.getPrice());
            totalPayment += price * buy;
        }

        return totalPayment;
    }
}

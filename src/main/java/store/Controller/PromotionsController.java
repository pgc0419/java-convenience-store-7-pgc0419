package store.Controller;

import store.Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PromotionsController {
    private final List<Products> products;
    private final DateController dateController;

    public PromotionsController(List<Products> products, DateController dateController) {
        this.products = products;
        this.dateController = dateController;
    }
    public List<Products> findProductsWithActivePromotions() {
        List<Products> productsWithActivePromotions = new ArrayList<>();
        List<Promotions> activePromotions = dateController.getActivePromotions();

        for (Products product : products) {
            for (Promotions activePromotion : activePromotions) {
                if (Objects.equals(product.getProductOfPromotion(), activePromotion.getPromotionName())) {
                    productsWithActivePromotions.add(product);
                }
            }
        }
        return productsWithActivePromotions;
    }

    public void classifyProductsByPromotion() {
        List<Products> productsWithActivePromotions = findProductsWithActivePromotions();

        List<Products> TwoPlusOnePromotions = new ArrayList<>();
        List<Products> MDPromotions = new ArrayList<>();
        List<Products> TimeSalePromotions = new ArrayList<>();

        for (Products product : productsWithActivePromotions) {
            String promotion = product.getProductOfPromotion();

            if (promotion == null) {
                mergeNullProductWithExistingPromotions(product, TwoPlusOnePromotions, MDPromotions, TimeSalePromotions);
            } else if (promotion.equals("탄산2+1")) {
                TwoPlusOnePromotions.add(product);
            } else if (promotion.equals("MD추천상품")) {
                MDPromotions.add(product);
            } else if (promotion.equals("반짝할인")) {
                TimeSalePromotions.add(product);
            }
        }

        List<Map<String, Object>> twoPlusOneResults = calculateTwoPlusOnePromotion(TwoPlusOnePromotions);
        List<Map<String, Object>> mdResults = calculateMDPromotion(MDPromotions);
        List<Map<String, Object>> timeSaleResults = calculateTimeSalePromotion(TimeSalePromotions);
    }

    public List<Map<String, Object>> calculateTwoPlusOnePromotion(List<Products> products) {
        List<Map<String, Object>> results = new ArrayList<>();

        for (Products product : products) {
            int inputQuantity = Integer.parseInt(product.getQuantity());
            int buy = (inputQuantity / 3) * 2 + (inputQuantity % 3);
            int get = inputQuantity - buy;

            results.add(Map.of(
                    "productName", product.getProductName(),
                    "buy", buy,
                    "get", get
            ));
        }
        return results;
    }

    public List<Map<String, Object>> calculateMDPromotion(List<Products> products) {
        List<Map<String, Object>> results = new ArrayList<>();

        for (Products product : products) {
            int inputQuantity = Integer.parseInt(product.getQuantity());
            int buy = (inputQuantity / 2) + (inputQuantity % 2);
            int get = inputQuantity - buy;

            results.add(Map.of(
                    "productName", product.getProductName(),
                    "buy", buy,
                    "get", get
            ));
        }

        return results;
    }

    public List<Map<String, Object>> calculateTimeSalePromotion(List<Products> products) {
        List<Map<String, Object>> results = new ArrayList<>();

        for (Products product : products) {
            int inputQuantity = Integer.parseInt(product.getQuantity());
            int buy = (inputQuantity / 2) + (inputQuantity % 2);
            int get = inputQuantity - buy;

            results.add(Map.of(
                    "productName", product.getProductName(),
                    "buy", buy,
                    "get", get
            ));
        }

        return results;
    }

    public List<Map<String, Object>> calculateNullPromotion(List<Products> products) {
        List<Map<String, Object>> results = new ArrayList<>();

        for (Products product : products) {
            int inputQuantity = Integer.parseInt(product.getQuantity());
            int buy = inputQuantity;
            int get = 0;

            results.add(Map.of(
                    "productName", product.getProductName(),
                    "buy", buy,
                    "get", get
            ));
        }

        return results;
    }

    private void mergeNullProductWithExistingPromotions(
            Products nullProduct,
            List<Products> twoPlusOnePromotions,
            List<Products> mdPromotions,
            List<Products> timeSalePromotions) {
        String nullProductName = nullProduct.getProductName();
        String nullProductQuantity = nullProduct.getQuantity();

        for (Products product : twoPlusOnePromotions) {
            if (product.getProductName().equals(nullProductName)) {
                int totalQuantity = Integer.parseInt(product.getQuantity()) + Integer.parseInt(nullProductQuantity);
                product.setQuantity(String.valueOf(totalQuantity));
                return;
            }
        }

        for (Products product : mdPromotions) {
            if (product.getProductName().equals(nullProductName)) {
                int totalQuantity = Integer.parseInt(product.getQuantity()) + Integer.parseInt(nullProductQuantity);
                product.setQuantity(String.valueOf(totalQuantity));
                return;
            }
        }

        for (Products product : timeSalePromotions) {
            if (product.getProductName().equals(nullProductName)) {
                int totalQuantity = Integer.parseInt(product.getQuantity()) + Integer.parseInt(nullProductQuantity);
                product.setQuantity(String.valueOf(totalQuantity));
                return;
            }
        }
    }

}

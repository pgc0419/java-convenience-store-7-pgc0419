package store.Controller;

import store.Model.*;
import store.View.*;

import java.util.*;

public class PromotionsController {
    private final List<Products> products;
    private final DateController dateController;
    private final InputView inputView;

    public PromotionsController(List<Products> products, DateController dateController, InputView inputView) {
        this.products = products;
        this.dateController = dateController;
        this.inputView = inputView;
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

    public void classifyAndApplyPromotions() {
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
        applyPromotion(twoPlusOneResults, "탄산2+1");

        List<Map<String, Object>> mdResults = calculateMDPromotion(MDPromotions);
        applyPromotion(mdResults, "MD추천상품");

        List<Map<String, Object>> timeSaleResults = calculateTimeSalePromotion(TimeSalePromotions);
        applyPromotion(timeSaleResults, "반짝할인");
    }

    private void applyPromotion(List<Map<String, Object>> promotionResults, String promotionType) {
        for (Map<String, Object> result : promotionResults) {
            String productName = (String) result.get("productName");
            int buy = (int) result.get("buy");
            int get = (int) result.get("get");
            int noPromotionQuantity = (int) result.get("noPromotionQuantity");
            int noPromotionBuy = (int) result.getOrDefault("noPromotionBuy", buy);
            int noPromotionGet = (int) result.getOrDefault("noPromotionGet", get);

            if (get > 0) {
                String userResponse = inputView.promotion()
                        .replace("{상품명}", productName);

                if (userResponse.equalsIgnoreCase("N")) {
                } else {;
                    get -= 1;
                }
            }
            else {
                String userResponse = inputView.promotionSale()
                        .replace("{상품명}", productName)
                        .replace("{수량}", String.valueOf(noPromotionQuantity));

                if (userResponse.equalsIgnoreCase("N")) {
                } else {
                    buy = noPromotionBuy;
                    get = noPromotionGet;
                }
            }

            result.put("buy", buy);
            result.put("get", get);
        }
    }

    public List<Map<String, Object>> calculateTwoPlusOnePromotion(List<Products> products) {
        List<Map<String, Object>> results = new ArrayList<>();

        for (Products product : products) {
            int inputQuantity = Integer.parseInt(product.getQuantity());
            int buy = (inputQuantity / 3) * 2 + (inputQuantity % 3);
            int get = inputQuantity - buy;
            int noPromotionBuy = (inputQuantity / 3) * 2;
            int noPromotionGet = (inputQuantity % 3);
            int noPromotionQuantity = inputQuantity - noPromotionBuy - noPromotionGet;

            Map<String, Object> result = new HashMap<>();
            result.put("productName", product.getProductName());
            result.put("buy", buy);
            result.put("get", get);
            result.put("noPromotionBuy", noPromotionBuy);
            result.put("noPromotionGet", noPromotionGet);
            result.put("noPromotionQuantity", noPromotionQuantity);

            results.add(result);
        }
        return results;
    }

    public List<Map<String, Object>> calculateMDPromotion(List<Products> products) {
        List<Map<String, Object>> results = new ArrayList<>();

        for (Products product : products) {
            int inputQuantity = Integer.parseInt(product.getQuantity());
            int buy = (inputQuantity / 2) + (inputQuantity % 2);
            int get = inputQuantity - buy;
            int noPromotionBuy = (inputQuantity / 2);
            int noPromotionGet = (inputQuantity / 2);
            int noPromotionQuantity = inputQuantity - noPromotionBuy - noPromotionGet;

            Map<String, Object> result = new HashMap<>();
            result.put("productName", product.getProductName());
            result.put("buy", buy);
            result.put("get", get);
            result.put("noPromotionBuy", noPromotionBuy);
            result.put("noPromotionGet", noPromotionGet);
            result.put("noPromotionQuantity", noPromotionQuantity);

            results.add(result);
        }

        return results;
    }

    public List<Map<String, Object>> calculateTimeSalePromotion(List<Products> products) {
        List<Map<String, Object>> results = new ArrayList<>();

        for (Products product : products) {
            int inputQuantity = Integer.parseInt(product.getQuantity());
            int buy = (inputQuantity / 2) + (inputQuantity % 2);
            int get = inputQuantity - buy;
            int noPromotionBuy = (inputQuantity / 2);
            int noPromotionGet = (inputQuantity / 2);
            int noPromotionQuantity = inputQuantity - noPromotionBuy - noPromotionGet;

            Map<String, Object> result = new HashMap<>();
            result.put("productName", product.getProductName());
            result.put("buy", buy);
            result.put("get", get);
            result.put("noPromotionBuy", noPromotionBuy);
            result.put("noPromotionGet", noPromotionGet);
            result.put("noPromotionQuantity", noPromotionQuantity);

            results.add(result);
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

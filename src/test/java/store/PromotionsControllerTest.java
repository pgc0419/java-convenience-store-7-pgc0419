package store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import store.Model.*;
import store.Controller.*;
import store.Reader.PromotionsReader;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PromotionsControllerTest {

    @DisplayName("특정 날짜에 활성화된 프로모션 테스트")
    @Test
    void 특정_날짜에_활성화된_프로모션_테스트() {
        PromotionsReader promotionsReader = new PromotionsReader() {
            @Override
            public List<Promotions> readPromotions() {
                return List.of(
                        new Promotions("탄산2+1", "6", "2"),
                        new Promotions("MD추천상품", "5", "3"),
                        new Promotions("반짝할인", "4", "2")
                );
            }

            @Override
            public List<Date> readDate() {
                return List.of(
                        new Date("2024-11-01", "2024-11-30"),
                        new Date("2024-11-01", "2024-11-15"),
                        new Date("2024-11-10", "2024-11-20")
                );
            }
        };

        LocalDate specificDate = LocalDate.of(2024, 11, 10);
        DateController dateController = new DateController(promotionsReader, specificDate);

        List<Products> products = List.of(
                new Products("콜라", "6", "탄산2+1"),
                new Products("사이다", "5", "MD추천상품"),
                new Products("탄산수", "4", "반짝할인"),
                new Products("물", "3", null),
                new Products("콜라", "2", null)
        );

        PromotionsController promotionsController = new PromotionsController(products, dateController);
        promotionsController.classifyProductsByPromotion();

        List<Map<String, Object>> twoPlusOneResults = promotionsController.calculateTwoPlusOnePromotion(List.of(
                new Products("콜라", "8", "탄산2+1")
        ));

        for (Map<String, Object> result : twoPlusOneResults) {
            System.out.println("Product Name: " + result.get("productName"));
            System.out.println("Buy: " + result.get("buy"));
            System.out.println("Get: " + result.get("get"));
        }

        List<Map<String, Object>> mdResults = promotionsController.calculateMDPromotion(List.of(
                new Products("사이다", "5", "MD추천상품")
        ));

        for (Map<String, Object> result : mdResults) {
            System.out.println("Product Name: " + result.get("productName"));
            System.out.println("Buy: " + result.get("buy"));
            System.out.println("Get: " + result.get("get"));
        }

        List<Map<String, Object>> timeSaleResults = promotionsController.calculateTimeSalePromotion(List.of(
                new Products("탄산수", "4", "반짝할인")
        ));

        for (Map<String, Object> result : timeSaleResults) {
            System.out.println("Product Name: " + result.get("productName"));
            System.out.println("Buy: " + result.get("buy"));
            System.out.println("Get: " + result.get("get"));
        }
    }
}

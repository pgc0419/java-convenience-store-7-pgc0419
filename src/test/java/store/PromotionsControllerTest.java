package store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import store.Model.*;
import store.Controller.*;
import store.View.*;
import store.Reader.PromotionsReader;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PromotionsControllerTest {

    private PromotionsReader mockPromotionsReader() {
        return new PromotionsReader() {
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
    }

    private InputView mockInputView(String promotionResponse, String promotionSaleResponse) {
        return new InputView() {
            @Override
            public String promotion() {
                return promotionResponse;
            }

            @Override
            public String promotionSale() {
                return promotionSaleResponse;
            }
        };
    }

    private PromotionsController setupController(String promotionResponse, String promotionSaleResponse) {
        LocalDate specificDate = LocalDate.of(2024, 11, 10);
        DateController dateController = new DateController(mockPromotionsReader(), specificDate);

        List<Products> products = List.of(
                new Products("콜라", "6", "탄산2+1"),
                new Products("사이다", "5", "MD추천상품"),
                new Products("탄산수", "4", "반짝할인"),
                new Products("물", "3", null),
                new Products("콜라", "2", null)
        );

        InputView inputView = mockInputView(promotionResponse, promotionSaleResponse);

        return new PromotionsController(products, dateController, inputView);
    }

    @DisplayName("탄산2+1 프로모션 테스트")
    @Test
    void 탄산2_플러스_1_프로모션_테스트() {
        PromotionsController promotionsController = setupController("Y", "N");

        List<Map<String, Object>> twoPlusOneResults = promotionsController.calculateTwoPlusOnePromotion(List.of(
                new Products("콜라", "8", "탄산2+1")
        ));

        for (Map<String, Object> result : twoPlusOneResults) {
            assertEquals("콜라", result.get("productName"));
            assertEquals(6, result.get("buy"));
            assertEquals(2, result.get("get"));
        }
    }

    @DisplayName("MD추천상품 프로모션 테스트")
    @Test
    void MD추천상품_프로모션_테스트() {
        PromotionsController promotionsController = setupController("Y", "N");

        List<Map<String, Object>> mdResults = promotionsController.calculateMDPromotion(List.of(
                new Products("사이다", "5", "MD추천상품")
        ));

        for (Map<String, Object> result : mdResults) {
            assertEquals("사이다", result.get("productName"));
            assertEquals(3, result.get("buy"));
            assertEquals(2, result.get("get"));
        }
    }

    @DisplayName("반짝할인 프로모션 테스트")
    @Test
    void 반짝할인_프로모션_테스트() {
        PromotionsController promotionsController = setupController("Y", "N");

        List<Map<String, Object>> timeSaleResults = promotionsController.calculateTimeSalePromotion(List.of(
                new Products("탄산수", "4", "반짝할인")
        ));

        for (Map<String, Object> result : timeSaleResults) {
            assertEquals("탄산수", result.get("productName"));
            assertEquals(2, result.get("buy"));
            assertEquals(2, result.get("get"));
        }
    }

    @DisplayName("특정 날짜에 활성화된 프로모션 검증")
    @Test
    void 특정_날짜에_활성화된_프로모션_검증() {
        PromotionsController promotionsController = setupController("Y", "N");

        promotionsController.classifyAndApplyPromotions();

        List<Products> productsWithActivePromotions = promotionsController.findProductsWithActivePromotions();
        assertEquals(3, productsWithActivePromotions.size());
        assertTrue(productsWithActivePromotions.stream().anyMatch(p -> p.getProductName().equals("콜라")));
        assertTrue(productsWithActivePromotions.stream().anyMatch(p -> p.getProductName().equals("사이다")));
        assertTrue(productsWithActivePromotions.stream().anyMatch(p -> p.getProductName().equals("탄산수")));
    }

    @DisplayName("탄산2+1 프로모션 거부 테스트")
    @Test
    void 탄산2_플러스_1_프로모션_거부_테스트() {
        PromotionsController promotionsController = setupController("N", "N");

        List<Map<String, Object>> twoPlusOneResults = promotionsController.calculateTwoPlusOnePromotion(List.of(
                new Products("콜라", "8", "탄산2+1")
        ));

        for (Map<String, Object> result : twoPlusOneResults) {
            assertEquals("콜라", result.get("productName"));
            assertEquals(6, result.get("buy"));
            assertEquals(2, result.get("get"));
        }
    }

    @DisplayName("MD추천상품 프로모션 거부 테스트")
    @Test
    void MD추천상품_프로모션_거부_테스트() {
        PromotionsController promotionsController = setupController("N", "N");

        List<Map<String, Object>> mdResults = promotionsController.calculateMDPromotion(List.of(
                new Products("사이다", "5", "MD추천상품")
        ));

        for (Map<String, Object> result : mdResults) {
            assertEquals("사이다", result.get("productName"));
            assertEquals(3, result.get("buy"));
            assertEquals(2, result.get("get"));
        }
    }

    @DisplayName("반짝할인 프로모션 거부 테스트")
    @Test
    void 반짝할인_프로모션_거부_테스트() {
        PromotionsController promotionsController = setupController("N", "N");

        List<Map<String, Object>> timeSaleResults = promotionsController.calculateTimeSalePromotion(List.of(
                new Products("탄산수", "4", "반짝할인")
        ));

        for (Map<String, Object> result : timeSaleResults) {
            assertEquals("탄산수", result.get("productName"));
            assertEquals(2, result.get("buy"));
            assertEquals(2, result.get("get"));
        }
    }
}

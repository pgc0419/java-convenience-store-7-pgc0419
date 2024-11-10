package store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import store.Controller.*;
import store.Model.*;
import store.Reader.*;
import store.View.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayControllerTest {

    @DisplayName("결제 금액 계산 테스트")
    @Test
    void 결제_금액_계산_테스트() {
        ProductsReader productsReader = new ProductsReader() {
            @Override
            public List<Products> readProducts() {
                return List.of(
                        new Products("콜라", "1000", "6", "탄산2+1"),
                        new Products("사이다", "1000", "5", "탄산2+1"),
                        new Products("탄산수", "1200", "4", "반짝할인")
                );
            }
        };

        PromotionsReader promotionsReader = new PromotionsReader() {
            @Override
            public List<Promotions> readPromotions() {
                return List.of(
                        new Promotions("탄산2+1", "2", "1"),
                        new Promotions("반짝할인", "1", "1")
                );
            }

            @Override
            public List<Date> readDate() {
                return List.of(
                        new Date("2024-11-01", "2024-11-30"),
                        new Date("2024-11-10", "2024-11-20")
                );
            }
        };

        LocalDate specificDate = LocalDate.of(2024, 11, 10);
        PromotionsController promotionsController = new PromotionsController(
                productsReader.readProducts(),
                new DateController(promotionsReader, specificDate),
                new InputView() {
                    @Override
                    public String promotion() {
                        return "Y";
                    }

                    @Override
                    public String promotionSale() {
                        return "Y";
                    }
                }
        );

        PayController payController = new PayController(productsReader, promotionsController);

        int totalPayment = payController.calculateTotalPayment();

        assertEquals(10400, totalPayment);
    }
}

package store;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.Controller.DateController;
import store.Model.Date;
import store.Model.Promotions;
import store.Reader.PromotionsReader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DateControllerTest {
    @DisplayName("현재 시간 출력 테스트")
    @Test
    void 현재_시간_출력_테스트() {
        LocalDateTime now = DateTimes.now();

        System.out.println("현재 시간: " + now);

        assertNotNull(now, "현재 시간이 null이 아니어야 합니다.");
    }

    @DisplayName("오늘 날짜에 해당하는 프로모션 필터링 테스트")
    @Test
    void 오늘_날짜에_해당하는_프로모션_필터링_테스트() {
        PromotionsReader promotionsReader = new PromotionsReader() {
            @Override
            public List<Promotions> readPromotions() {
                return List.of(
                        new Promotions("탄산2+1", "2", "1"),
                        new Promotions("MD추천상품", "1", "1"),
                        new Promotions("반짝할인", "1", "1")
                );
            }

            @Override
            public List<Date> readDate() {
                return List.of(
                        new Date("2024-01-01", "2024-12-31"),
                        new Date("2024-01-01", "2024-12-31"),
                        new Date("2024-11-01", "2024-11-30")
                );
            }
        };

        DateController dateController = new DateController(promotionsReader);

        List<Promotions> activePromotions = dateController.getActivePromotions();

        assertEquals(3, activePromotions.size(), "활성 프로모션의 개수가 예상과 다릅니다.");
        assertEquals("탄산2+1", activePromotions.get(0).getPromotionName());
        assertEquals("MD추천상품", activePromotions.get(1).getPromotionName());
        assertEquals("반짝할인", activePromotions.get(2).getPromotionName());
    }

    @DisplayName("오늘 날짜를 12월 1일로 설정하고 활성 프로모션 필터링 테스트")
    @Test
    void 오늘_날짜를_12월_1일로_설정하고_활성_프로모션_필터링_테스트() {
        PromotionsReader promotionsReader = new PromotionsReader() {
            @Override
            public List<Promotions> readPromotions() {
                return List.of(
                        new Promotions("탄산2+1", "2", "1"),
                        new Promotions("MD추천상품", "1", "1"),
                        new Promotions("반짝할인", "1", "1")
                );
            }

            @Override
            public List<Date> readDate() {
                return List.of(
                        new Date("2024-01-01", "2024-12-31"),
                        new Date("2024-01-01", "2024-12-31"),
                        new Date("2024-11-01", "2024-11-30")
                );
            }
        };

        LocalDate customToday = LocalDate.of(2024, 12, 1);
        DateController dateController = new DateController(promotionsReader, customToday);

        List<Promotions> activePromotions = dateController.getActivePromotions();

        assertEquals(2, activePromotions.size(), "활성 프로모션의 개수가 예상과 다릅니다.");
        assertEquals("탄산2+1", activePromotions.get(0).getPromotionName());
        assertEquals("MD추천상품", activePromotions.get(1).getPromotionName());
    }
}

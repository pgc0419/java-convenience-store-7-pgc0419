package store.Controller;

import camp.nextstep.edu.missionutils.DateTimes;
import store.Model.Date;
import store.Model.Promotions;
import store.Reader.PromotionsReader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateController {

    private final PromotionsReader promotionsReader;
    private final LocalDate today;

    // 기본 생성자: 현재 날짜 사용
    public DateController(PromotionsReader promotionsReader) {
        this(promotionsReader, convertToLocalDate(DateTimes.now()));
    }

    // 테스트용 생성자: 특정 날짜 주입
    public DateController(PromotionsReader promotionsReader, LocalDate today) {
        this.promotionsReader = promotionsReader;
        this.today = today;
    }

    public List<Promotions> getActivePromotions() {
        List<Date> dates = promotionsReader.readDate();
        List<Promotions> promotions = promotionsReader.readPromotions();
        List<Promotions> activePromotions = new ArrayList<>();

        for (int i = 0; i < dates.size(); i++) {
            LocalDate startDate = parseDate(dates.get(i).getStart_date());
            LocalDate endDate = parseDate(dates.get(i).getEnd_date());

            if ((today.isEqual(startDate) || today.isAfter(startDate)) && (today.isEqual(endDate) || today.isBefore(endDate))) {
                activePromotions.add(promotions.get(i));
            }
        }

        return activePromotions;
    }

    private LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    private static LocalDate convertToLocalDate(LocalDateTime dateTime) {
        return dateTime.toLocalDate();
    }
}

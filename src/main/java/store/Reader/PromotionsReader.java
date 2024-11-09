package store.Reader;

import store.Model.Date;
import store.Model.Promotions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PromotionsReader {
    private static final String FILE_PATH = "/promotions.md";

    public List<Promotions> readPromotions() {
        List<Promotions> promotions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(FILE_PATH)))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String promotionName = fields[0];
                String buy = fields[1];
                String get = fields[2];
                promotions.add(new Promotions(promotionName, buy, get));
            }
        } catch (IOException e) {
        }
        return promotions;
    }

    public List<Date> readDate() {
        List<Date> date = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(FILE_PATH)))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String start_date = fields[3];
                String end_date = fields[4];
                date.add(new Date(start_date, end_date));
            }
        } catch (IOException e) {
        }
        return date;
    }
}

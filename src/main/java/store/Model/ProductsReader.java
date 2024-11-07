package store.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductsReader {
    private static final String FILE_PATH = "resources/products.md";

    public List<Products> readProducts() {
        List<Products> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String name = fields[0];
                int quantity = Integer.parseInt(fields[2]);
                products.add(new Products(name, quantity));
            }
        } catch (IOException e) {
        }
        return products;
    }

    public List<Pay> readPrices() {
        List<Pay> pay = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                int price = Integer.parseInt(fields[1]);
                pay.add(new Pay(price));
            }
        } catch (IOException e) {
        }
        return pay;
    }

    public List<Promotions> readProductsOfPromotions() {
        List<Promotions> promotions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String promotion = null;
                String productOfPromotion = fields.length > 3 ? fields[3] : null;
                promotions.add(new Promotions(promotion, productOfPromotion));
            }
        } catch (IOException e) {
        }
        return promotions;
    }
}

package store.Reader;

import store.Model.Products;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProductsReader {
    private static final String FILE_PATH = "/products.md";

    public List<Products> readProducts() {
        List<Products> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(FILE_PATH)))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String productName = fields[0];
                String price = fields[1];
                String quantity = fields[2];
                String productOfPromotion = fields.length > 3 ? fields[3] : null;
                products.add(new Products(productName, price, quantity, productOfPromotion));
            }
        } catch (IOException e) {
        }
        return products;
    }

    public List<String> readRawLines() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(FILE_PATH)))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
        }
        return lines;
    }
}
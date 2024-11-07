package store.Controller;

import store.View.InputView;

import java.util.ArrayList;
import java.util.List;

public class ProductsController {
    public List<String> parseProductNames() {
        List<String> productNames = new ArrayList<>();
        String[] productArray = InputView.readProduct();

        for (String product : productArray) {
            product = product.trim().replace("[", "").replace("]", "");
            String[] parts = product.split("-");
            productNames.add(parts[0].trim());
        }

        return productNames;
    }

    public List<String> parseQuantities() {
        List<String> quantities = new ArrayList<>();
        String[] productArray = InputView.readProduct();

        for (String product : productArray) {
            product = product.trim().replace("[", "").replace("]", "");
            String[] parts = product.split("-");
            quantities.add(parts[1].trim());
        }

        return quantities;
    }
}

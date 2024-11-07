package store.Controller;

import store.View.InputView;

import java.util.ArrayList;
import java.util.List;

public class ProductsController {
    public List<String> parseNames() {
        List<String> names = new ArrayList<>();
        String[] productArray = InputView.readProduct();

        for (String product : productArray) {
            product = product.trim().replace("[", "").replace("]", "");
            String[] parts = product.split("-");
            names.add(parts[0].trim());
        }

        return names;
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

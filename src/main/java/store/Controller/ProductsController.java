package store.Controller;

import store.View.InputView;

import java.util.ArrayList;
import java.util.List;

public class ProductsController {
    public List<String> parseProduct() {
        String input = InputView.readProduct();
        String[] productArray = input.split(",");
        List<String> productList = new ArrayList<>();
        for (String product : productArray) {
            product = product.trim().replace("[", "").replace("]", "");
            productList.add(product);
        }
        return productList;
    }
}

package store.Controller;

import store.View.InputView;
import store.Model.Products;
import store.Validate.InputValidate;

import java.util.ArrayList;
import java.util.List;

public class ProductsController {
    private final List<Products> products;

    public ProductsController(List<Products> products) {
        this.products = products;
    }

    public List<String> parseProductNames() {
        List<String> productNames = new ArrayList<>();
        String[] productArray = InputView.readProduct();

        for (String product : productArray) {
            InputValidate.InputNotNull(product);
            InputValidate.InputFormatValid(product);
            product = product.trim().replace("[", "").replace("]", "");
            String[] parts = product.split("-");
            productNames.add(parts[0].trim());
        }
        return productNames;
    }

    public List<Integer> parseInputQuantities() {
        List<Integer> inputQuantities = new ArrayList<>();
        String[] productArray = InputView.readProduct();

        for (String product : productArray) {
            InputValidate.InputNotNull(product);
            InputValidate.InputFormatValid(product);
            product = product.trim().replace("[", "").replace("]", "");
            String[] parts = product.split("-");
            inputQuantities.add(Integer.parseInt(parts[1].trim()));
        }
        return inputQuantities;
    }

    public void updateQuantities() {
        List<String> productNames = parseProductNames();
        List<Integer> inputQuantities = parseInputQuantities();
        List<String> availableProductNames = getAvailableProductNames();

        InputValidate.ProductNotExist(productNames, availableProductNames);
        InputValidate.QuantityIsExceed(productNames, inputQuantities, products);

        for (int i = 0; i < productNames.size(); i++) {
            String productName = productNames.get(i);
            int inputQuantity = inputQuantities.get(i);
            updateProductQuantity(productName, inputQuantity);
        }
    }

    private List<String> getAvailableProductNames() {
        List<String> availableProductNames = new ArrayList<>();
        for (Products product : products) {
            availableProductNames.add(product.getProductName());
        }
        return availableProductNames;
    }

    private void updateProductQuantity(String productName, int inputQuantity) {
        Products product = findProductByName(productName);
        if (product != null) {
            int updatedQuantity = calculateUpdatedQuantity(product, inputQuantity);

            if (updatedQuantity == 0) {
                product.setQuantity("재고 없음");
            } else {
                product.setQuantity(String.valueOf(updatedQuantity));
            }
        }
    }

    private Products findProductByName(String productName) {
        return products.stream()
                .filter(product -> product.getProductName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    private int calculateUpdatedQuantity(Products product, int inputQuantity) {
        int currentQuantity = Integer.parseInt(product.getQuantity());
        return currentQuantity - inputQuantity;
    }
}

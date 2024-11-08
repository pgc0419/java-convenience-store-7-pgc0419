package store.Controller;

import store.View.InputView;
import store.Model.Products;

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
            product = product.trim().replace("[", "").replace("]", "");
            String[] parts = product.split("-");
            productNames.add(parts[0].trim());
        }
        return productNames;
    }

    public List<String> parseInputQuantities() {
        List<String> inputQuantities = new ArrayList<>();
        String[] productArray = InputView.readProduct();
        for (String product : productArray) {
            product = product.trim().replace("[", "").replace("]", "");
            String[] parts = product.split("-");
            inputQuantities.add(parts[1].trim());
        }
        return inputQuantities;
    }

    public void updateQuantities() {
        List<String> productNames = parseProductNames();
        List<String> inputQuantities = parseInputQuantities();

        for (int i = 0; i < productNames.size(); i++) {
            String productName = productNames.get(i);
            int inputQuantity = Integer.parseInt(inputQuantities.get(i));
            updateProductQuantity(productName, inputQuantity);
        }
    }

    private void updateProductQuantity(String productName, int inputQuantity) {
        Products product = findProductByName(productName);
        int updatedQuantity = calculateUpdatedQuantity(product, inputQuantity);
        product.setQuantity(String.valueOf(updatedQuantity));

        if (updatedQuantity == 0) {
            product.setQuantity("재고 없음");
        } else {
            product.setQuantity(String.valueOf(updatedQuantity));
        }
    }

    private Products findProductByName(String productName) {
        for (Products product : products) {
            if (product.getProductName().equals(productName)) {
                return product;
            }
        }
        throw new IllegalArgumentException("제품이 존재하지 않습니다. " + productName);
    }

    private int calculateUpdatedQuantity(Products product, int inputQuantity) {
        int currentQuantity = Integer.parseInt(product.getQuantity());
        int updatedQuantity = currentQuantity - inputQuantity;

        if (updatedQuantity < 0) {
            throw new IllegalArgumentException("수량이 존재하지 않습니다. " + product.getProductName());
        }
        return updatedQuantity;
    }
}

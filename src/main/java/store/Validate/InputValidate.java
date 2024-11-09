package store.Validate;

import store.Model.Products;

import java.util.List;

public class InputValidate {

    public static void InputNotNull(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public static void InputFormatValid(String product) {
        if (!product.matches("\\s*\\[\\s*[^\\[\\],]+-\\d+\\s*\\]\\s*")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public static void ProductNotExist(List<String> inputProductNames, List<String> availableProductNames) {
        for (String inputName : inputProductNames) {
            if (!availableProductNames.contains(inputName)) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
            }
        }
    }

    public static void QuantityIsExceed(List<String> inputProductNames, List<Integer> inputQuantities, List<Products> availableProducts) {
        for (int i = 0; i < inputProductNames.size(); i++) {
            String inputName = inputProductNames.get(i);
            int inputQuantity = inputQuantities.get(i);

            Products matchingProduct = availableProducts.stream()
                    .filter(product -> product.getProductName().equals(inputName))
                    .findFirst()
                    .orElse(null);

            if (matchingProduct != null) {
                int availableQuantity = Integer.parseInt(matchingProduct.getQuantity());
                if (inputQuantity > availableQuantity) {
                    throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
                }
            }
        }
    }
}

package store.Updater;

import store.Model.Products;
import store.Reader.ProductsReader;

import java.io.*;
import java.util.List;

public class ProductsMDUpdater {
    private static final String FILE_PATH = "src/main/resources/products.md";

    public void updateProductsFile(List<Products> products) {
        ProductsReader reader = new ProductsReader();
        List<String> originalLines = reader.readRawLines();
        List<String> updatedLines = replaceQuantitiesInLines(originalLines, products);
        writeUpdatedLinesToFile(updatedLines);
    }

    private List<String> replaceQuantitiesInLines(List<String> originalLines, List<Products> products) {
        for (int i = 1; i < originalLines.size(); i++) { // Skip header
            String[] fields = originalLines.get(i).split(",");
            for (Products product : products) {
                if (isMatchingProduct(fields, product)) {
                    fields[2] = product.getQuantity();
                    originalLines.set(i, String.join(",", fields));
                    break;
                }
            }
        }
        return originalLines;
    }

    private boolean isMatchingProduct(String[] fields, Products product) {
        String productName = fields[0];
        String productPromotion = fields.length > 3 ? fields[3] : null;

        return productName.equals(product.getProductName()) &&
                ((productPromotion == null && product.getProductOfPromotion() == null) ||
                        (productPromotion != null && productPromotion.equals(product.getProductOfPromotion())));
    }

    private void writeUpdatedLinesToFile(List<String> updatedLines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : updatedLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to update the MD file", e);
        }
    }
}

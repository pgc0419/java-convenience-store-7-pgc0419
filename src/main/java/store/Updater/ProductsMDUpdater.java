package store.Updater;

import store.Model.Products;

import java.io.*;
import java.util.List;

public class ProductsMDUpdater {
    private static final String FILE_PATH = "/path/to/your/markdown/file.md";

    public static void updateMarkdown(List<Products> products) {
        // StringBuilder를 사용하여 업데이트된 데이터를 저장
        StringBuilder updatedContent = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String header = br.readLine(); // 첫 줄 헤더 읽기
            updatedContent.append(header).append("\n"); // 헤더 유지

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("--")) {
                    updatedContent.append(line).append("\n"); // 빈 줄 또는 구분선 유지
                    continue;
                }

                String[] fields = line.split(",");
                String productName = fields[0];
                String newQuantity = findUpdatedQuantity(productName, products);

                // 기존 데이터를 업데이트된 quantity로 변경
                fields[2] = newQuantity;

                // 업데이트된 데이터를 다시 조합
                updatedContent.append(String.join(",", fields)).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는 중 문제가 발생했습니다.", e);
        }

        // 업데이트된 내용을 파일에 쓰기
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write(updatedContent.toString());
        } catch (IOException e) {
            throw new RuntimeException("파일을 쓰는 중 문제가 발생했습니다.", e);
        }
    }

    private static String findUpdatedQuantity(String productName, List<Products> products) {
        for (Products product : products) {
            if (product.getProductName().equals(productName)) {
                return product.getQuantity();
            }
        }
        // 상품이 없으면 기존 값을 유지
        return "0";
    }
}

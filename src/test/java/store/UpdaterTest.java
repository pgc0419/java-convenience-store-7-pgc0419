package store;

import org.junit.jupiter.api.*;
import store.Model.Products;
import store.Updater.ProductsMDUpdater;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdaterTest {
    private static final String FILE_PATH = "src/main/resources/products.md";
    private ProductsMDUpdater updater;
    private List<Products> testProducts;

    @BeforeEach
    void setUp() throws IOException {
        updater = new ProductsMDUpdater();
        createTestFile();
        testProducts = new ArrayList<>();
        testProducts.add(new Products("콜라", "1000", "7", "탄산2+1"));
        testProducts.add(new Products("콜라", "1000", "9", "null"));
        testProducts.add(new Products("사이다", "1000", "5", "탄산2+1"));
        testProducts.add(new Products("사이다", "1000", "6", "null"));
    }

    @AfterEach
    void tearDown() throws IOException {
        restoreOriginalQuantities();
    }

    @DisplayName("MD 파일 업데이트 테스트")
    @Test
    void MD_파일_업데이트_테스트() throws IOException {
        updater.updateProductsFile(testProducts);

        List<String> updatedLines = Files.readAllLines(Path.of(FILE_PATH));
        assertEquals("productName,price,quantity,productOfPromotion", updatedLines.get(0));
        assertEquals("콜라,1000,7,탄산2+1", updatedLines.get(1));
        assertEquals("콜라,1000,9,null", updatedLines.get(2));
        assertEquals("사이다,1000,5,탄산2+1", updatedLines.get(3));
        assertEquals("사이다,1000,6,null", updatedLines.get(4));
    }

    private void createTestFile() throws IOException {
        List<String> lines = List.of(
                "productName,price,quantity,productOfPromotion",
                "콜라,1000,10,탄산2+1",
                "콜라,1000,10,null",
                "사이다,1000,8,탄산2+1",
                "사이다,1000,7,null"
        );
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private void restoreOriginalQuantities() throws IOException {
        List<String> originalLines = List.of(
                "productName,price,quantity,productOfPromotion",
                "콜라,1000,10,탄산2+1",
                "콜라,1000,10,null",
                "사이다,1000,8,탄산2+1",
                "사이다,1000,7,null",
                "오렌지주스,1800,9,MD추천상품",
                "탄산수,1200,5,탄산2+1",
                "물,500,10,null",
                "비타민워터,1500,6,null",
                "감자칩,1500,5,반짝할인",
                "감자칩,1500,5,null",
                "초코바,1200,5,MD추천상품",
                "초코바,1200,5,null",
                "에너지바,2000,5,null",
                "정식도시락,6400,8,null",
                "컵라면,1700,1,MD추천상품",
                "컵라면,1700,10,null"
        );

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : originalLines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}

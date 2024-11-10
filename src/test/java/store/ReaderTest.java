package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.Reader.ProductsReader;
import store.Reader.PromotionsReader;
import store.Model.Products;
import store.Model.Promotions;
import store.Model.Date;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReaderTest {

    private ProductsReader productsReader;
    private PromotionsReader promotionsReader;

    @BeforeEach
    void setUp() {
        productsReader = new ProductsReader();
        promotionsReader = new PromotionsReader();
    }

    @DisplayName("ProductsReader Products 테스트")
    @Test
    void ProductsReader_Products_테스트() {
        List<Products> products = productsReader.readProducts();

        assertThat(products).isNotNull();
        assertThat(products.size()).isGreaterThan(0);

        Products firstProduct = products.get(0);
        assertThat(firstProduct.getProductName()).isNotNull();
        assertThat(Integer.parseInt(firstProduct.getQuantity())).isGreaterThan(0);
        assertThat(firstProduct.getProductOfPromotion()).isNotNull();
    }

    @DisplayName("PromotionsReader Promotions 테스트")
    @Test
    void PromotionsReader_Promotions_테스트() {
        List<Promotions> promotions = promotionsReader.readPromotions();

        assertThat(promotions).isNotNull();
        assertThat(promotions.size()).isGreaterThan(0);

        Promotions firstPromotion = promotions.get(0);
        assertThat(firstPromotion.getPromotionName()).isNotNull();
        assertThat(Integer.parseInt(firstPromotion.getBuy())).isGreaterThan(0);
        assertThat(Integer.parseInt(firstPromotion.getGet())).isGreaterThan(0);
    }

    @DisplayName("PromotionsReader Date 테스트")
    @Test
    void PromotionsReader_Date_테스트() {
        List<Date> date = promotionsReader.readDate();

        assertThat(date).isNotNull();
        assertThat(date.size()).isGreaterThan(0);

        Date firstPromotion = date.get(0);
        assertThat(firstPromotion.getStart_date()).isNotNull();
        assertThat(firstPromotion.getStart_date()).matches("\\d{4}-\\d{2}-\\d{2}");
        assertThat(firstPromotion.getEnd_date()).isNotNull();
        assertThat(firstPromotion.getEnd_date()).matches("\\d{4}-\\d{2}-\\d{2}");
    }
}

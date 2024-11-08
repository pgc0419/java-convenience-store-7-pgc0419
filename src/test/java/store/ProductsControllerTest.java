package store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import store.Model.Products;
import store.Controller.ProductsController;
import store.View.InputView;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsControllerTest {

    @DisplayName("UpdateQuantities 성공 테스트")
    @Test
    void UpdateQuantities_성공_테스트() {
        List<Products> products = new ArrayList<>();
        products.add(new Products("콜라", "10", null));
        products.add(new Products("사이다", "8", null));
        ProductsController controller = new ProductsController(products);

        InputView.mockInput("[콜라-5], [사이다-3]");

        controller.updateQuantities();

        assertEquals("5", products.get(0).getQuantity(), "콜라의 수량이 5로 줄어야 합니다.");
        assertEquals("5", products.get(1).getQuantity(), "사이다의 수량이 5로 줄어야 합니다.");
    }

    @DisplayName("재고 없음 테스트")
    @Test
    void 재고_없음_테스트() {
        List<Products> products = new ArrayList<>();
        products.add(new Products("콜라", "5", null));
        products.add(new Products("사이다", "3", null));
        ProductsController controller = new ProductsController(products);

        InputView.mockInput("[콜라-5], [사이다-3]");

        controller.updateQuantities();

        assertEquals("재고 없음", products.get(0).getQuantity(), "콜라의 수량이 '재고 없음'으로 변경되어야 합니다.");
        assertEquals("재고 없음", products.get(1).getQuantity(), "사이다의 수량이 '재고 없음'으로 변경되어야 합니다.");
    }

    @DisplayName("재고 초과 테스트")
    @Test
    void 재고_초과_테스트() {
        List<Products> products = new ArrayList<>();
        products.add(new Products("콜라", "5", null));
        ProductsController controller = new ProductsController(products);

        InputView.mockInput("[콜라-10]");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                controller::updateQuantities,
                "수량 초과 시 예외가 발생해야 합니다."
        );
        assertTrue(exception.getMessage().contains("수량이 존재하지 않습니다. 콜라"));
    }

    @DisplayName("상품명이 목록에 없을 경우 테스트")
    @Test
    void 상품명이_목록에_없을_경우_테스트() {
        List<Products> products = new ArrayList<>();
        products.add(new Products("콜라", "10", null));
        ProductsController controller = new ProductsController(products);

        InputView.mockInput("[사이다-5]");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                controller::updateQuantities,
                "존재하지 않는 상품 입력 시 예외가 발생해야 합니다."
        );
        assertTrue(exception.getMessage().contains("제품이 존재하지 않습니다. 사이다"));
    }
}

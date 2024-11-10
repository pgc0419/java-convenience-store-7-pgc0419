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
        products.add(new Products("콜라", "1000", "10", null));
        products.add(new Products("사이다", "1000", "8", null));
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
        products.add(new Products("콜라", "1000", "5", null));
        products.add(new Products("사이다", "1000", "3", null));
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
        products.add(new Products("콜라", "1000", "5", null));
        ProductsController controller = new ProductsController(products);

        InputView.mockInput("[콜라-10]");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                controller::updateQuantities,
                "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."
        );
        assertTrue(exception.getMessage().contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."));
    }

    @DisplayName("상품명이 목록에 없을 경우 테스트")
    @Test
    void 상품명이_목록에_없을_경우_테스트() {
        List<Products> products = new ArrayList<>();
        products.add(new Products("콜라", "1000", "10", null));
        ProductsController controller = new ProductsController(products);

        InputView.mockInput("[사이다-5]");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                controller::updateQuantities,
                "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."
        );
        assertTrue(exception.getMessage().contains("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."));
    }

    @DisplayName("공백 입력 시 테스트")
    @Test
    void 공백_입력_시_테스트() {
        List<Products> products = new ArrayList<>();
        products.add(new Products("콜라", "1000", "10", null));
        ProductsController controller = new ProductsController(products);

        InputView.mockInput(" ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                controller::updateQuantities,
                "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."
        );
        assertTrue(exception.getMessage().contains("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."));
    }

    @DisplayName("올바르지 않은 형식 입력 시 예외 처리 테스트")
    @Test
    void 올바르지_않은_형식_입력_예외_처리_테스트() {
        List<Products> products = new ArrayList<>();
        products.add(new Products("콜라", "1000", "10", null));
        ProductsController controller = new ProductsController(products);

        InputView.mockInput("[콜라],[10],[콜라-10-10]");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                controller::updateQuantities,
                "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."
        );
        assertTrue(exception.getMessage().contains("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."));
    }
}

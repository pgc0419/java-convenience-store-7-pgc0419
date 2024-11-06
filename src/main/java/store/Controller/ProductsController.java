package store.Controller;

import store.View.InputView;

import java.util.ArrayList;
import java.util.List;

public class ProductsController {
    public List<String> parseItems() {
        String input = InputView.readItem();
        String[] itemsArray = input.split(",");
        List<String> itemList = new ArrayList<>();
        for (String item : itemsArray) {
            itemList.add(item.trim());
        }
        return itemList;
    }
}

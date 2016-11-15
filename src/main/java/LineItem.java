import com.codecool.shop.model.Product;

import java.util.Currency;

/**
 * Created by makaimark on 2016.11.15..
 */
public class LineItem {

    private Product product;
    private Currency currency;
    private int quantity;

    public LineItem(Product product, Currency currency, int quantity) {
        this.product = product;
        this.currency = currency;
        this.quantity = quantity;
    }
}

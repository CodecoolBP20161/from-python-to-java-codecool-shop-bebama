import com.codecool.shop.model.Product;

/**
 * Created by makaimark on 2016.11.15..
 */
public class LineItem {

    private Product product;
    private int quantity;

    public LineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}

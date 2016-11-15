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

    public Product getProduct(){
        return this.product;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void incQuantity(int quantity){
        this.quantity += quantity;
    }

    public int getTotalPrice(){
        return this.product.getPrice() * this.quantity;
    }
}

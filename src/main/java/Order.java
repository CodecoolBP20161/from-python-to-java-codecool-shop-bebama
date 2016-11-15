import java.util.ArrayList;
import java.util.List;

/**
 * Created by makaimark on 2016.11.15..
 */
public class Order {

    private static List<LineItem> listOfSelectedItems = new ArrayList<>();
    private static Order instance = null;

    private Order() {}

    public static Order getInstance() {
        if ( instance == null ) {
            instance = new Order();
        }
        return instance;
    }

    public void add(LineItem item){
        Boolean match = false;
        for (int i = 0; i < listOfSelectedItems.size(); i++) {
            if (listOfSelectedItems.get(i).getProduct() == item.getProduct() {
                listOfSelectedItems.get(i).incQuantity(item.getQuantity());
                match = true;
            }
        }
        if (!match){
            listOfSelectedItems.add(item);
        }
    }

    public void remove(LineItem item){
        listOfSelectedItems.remove(item);
    }
}

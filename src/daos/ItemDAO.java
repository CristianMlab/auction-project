package daos;

import model.Default_item;
import model.Item;
import model.Ring;

import java.util.ArrayList;

public class ItemDAO {
    //lot_id la Item
    private ArrayList<Item> items = new ArrayList<>();

    public ItemDAO(){
        items.add(new Ring(1, 2022, "XD2245", "Diamond", 0.5, "Unisex", ""));
        items.add(new Default_item(2, "super cool stuff"));
        items.add(new Default_item(3, "very nice item"));
        items.add(new Default_item(4, "bad item"));
    }

    public void save(){

    }
    public ArrayList<Item> getItems() {
        return items;
    }

    public Item get_item_by_lot(int lot_id){
        for (Item item: items) {
            if(item.get_lot_id() == lot_id)
                return item;
        }
        return null;
    }
}

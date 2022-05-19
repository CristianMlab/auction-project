package daos;

import csv.services.CustomCSVReader;
import model.DefaultItem;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private List<DefaultItem> items = new ArrayList<>();

    public ItemDAO(){
        try {
            items.addAll(CustomCSVReader.getInstance().readAll(DefaultItem.class, "src/main/resources/csv/default_items.csv"));
        } catch(Exception e){
            items = null;
            e.printStackTrace();
        }
    }

    public void save(DefaultItem item){
        items.add(item);
    }

    public void delete(DefaultItem item){
        items.remove(item);
    }

    public void update(DefaultItem item, String[] params){

    }

    public List<DefaultItem> getItems() {
        return items;
    }

    public DefaultItem getItemByLot(int lot_id){
        for (DefaultItem item: items) {
            if(item.get_lot_id() == lot_id)
                return item;
        }
        return null;
    }
}

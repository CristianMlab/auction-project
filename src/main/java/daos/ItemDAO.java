package daos;

import csv.services.CustomCSVReader;
import model.Default_item;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private List<Default_item> items = new ArrayList<>();

    public ItemDAO(){
        try {
            items.addAll(CustomCSVReader.getInstance().readAll(Default_item.class, "src/main/resources/csv/default_items.csv"));
        } catch(Exception e){
            items = null;
            e.printStackTrace();
        }
    }

    public void save(Default_item item){
        items.add(item);
    }

    public void delete(Default_item item){
        items.remove(item);
    }

    public void update(Default_item item, String[] params){

    }

    public List<Default_item> getItems() {
        return items;
    }

    public Default_item getItemByLot(int lot_id){
        for (Default_item item: items) {
            if(item.get_lot_id() == lot_id)
                return item;
        }
        return null;
    }
}

package daos;

import csv.services.CustomCSVReader;
import model.DefaultItem;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO implements DAO<DefaultItem> {
    private List<DefaultItem> items = new ArrayList<>();

    public ItemDAO(){
        try {
            items.addAll(CustomCSVReader.getInstance().readAll(DefaultItem.class, "src/main/resources/csv/defaultItems.csv"));
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

    public List<DefaultItem> getAll() {
        return items;
    }

    public DefaultItem get(int lot_id){
        for (DefaultItem item: items) {
            if(item.getLotId() == lot_id)
                return item;
        }
        return null;
    }
}

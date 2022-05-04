package daos;


import model.Bid_History;
import model.Item;

import java.util.ArrayList;

public class BidHistoryDAO {
    //should read and write to file for these
    //just creating an empty bid history for every item for now

    //lot_id to bid_history
    private ArrayList<Bid_History> histories = new ArrayList<>();

    public BidHistoryDAO() {
        ItemDAO x = new ItemDAO();
        ArrayList<Item> items = x.getItems();
        for(Item item : items){
                histories.add(new Bid_History(item.get_lot_id()));
        }
    }

    public ArrayList<Bid_History> getHistories() {
        return histories;
    }

    public Bid_History get_history_by_lot(int lot_id){
        for (Bid_History history: histories) {
            if(history.get_lot_id() == lot_id)
                return history;
        }
        return null;
    }
}

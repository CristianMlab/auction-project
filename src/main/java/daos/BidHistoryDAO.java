package daos;


import model.Bid;
import model.Bid_History;
import model.Item;

import java.util.ArrayList;

public class BidHistoryDAO {

    private ArrayList<Bid_History> histories = new ArrayList<>();

    public BidHistoryDAO() {
        //creating an empty bid history for every item
        ItemDAO x = new ItemDAO();
        ArrayList<Item> items = x.getItems();
        for(Item item : items){
                histories.add(new Bid_History(item.get_lot_id()));
        }

        //adding bids to the history
        BidDAO bidDAO = new BidDAO();
        ArrayList<Bid> bids = bidDAO.getBids();
        for(Bid bid : bids){
            get_history_by_lot(bid.getLot_id()).add_bid(bid);
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

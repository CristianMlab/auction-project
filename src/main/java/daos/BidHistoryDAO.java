package daos;


import model.Bid;
import model.Bid_History;
import model.Default_item;

import java.util.ArrayList;
import java.util.List;

public class BidHistoryDAO {

    private ArrayList<Bid_History> histories = new ArrayList<>();

    public BidHistoryDAO() {
        //creating an empty bid history for every item
        ItemDAO x = new ItemDAO();
        List<Default_item> items = x.getItems();
        for(Default_item item : items){
                histories.add(new Bid_History(item.get_lot_id()));
        }

        //adding bids to the history
        BidDAO bidDAO = new BidDAO();
        List<Bid> bids = bidDAO.getBids();
        for(Bid bid : bids){
            getHistoryByLot(bid.getLotId()).addBid(bid);
        }
    }

    public ArrayList<Bid_History> getHistories() {
        return histories;
    }

    public Bid_History getHistoryByLot(int lot_id){
        for (Bid_History history: histories) {
            if(history.getLotId() == lot_id)
                return history;
        }
        return null;
    }
}

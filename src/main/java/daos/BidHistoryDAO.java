package daos;


import model.Bid;
import model.BidHistory;
import model.DefaultItem;

import java.util.ArrayList;
import java.util.List;

public class BidHistoryDAO {

    private ArrayList<BidHistory> histories = new ArrayList<>();

    public BidHistoryDAO() {
        //creating an empty bid history for every item
        ItemDAO x = new ItemDAO();
        List<DefaultItem> items = x.getItems();
        for(DefaultItem item : items){
                histories.add(new BidHistory(item.get_lot_id()));
        }

        //adding bids to the history
        BidDAO bidDAO = new BidDAO();
        List<Bid> bids = bidDAO.getBids();
        for(Bid bid : bids){
            getHistoryByLot(bid.getLotId()).addBid(bid);
        }
    }

    public ArrayList<BidHistory> getHistories() {
        return histories;
    }

    public BidHistory getHistoryByLot(int lot_id){
        for (BidHistory history: histories) {
            if(history.getLotId() == lot_id)
                return history;
        }
        return null;
    }
}

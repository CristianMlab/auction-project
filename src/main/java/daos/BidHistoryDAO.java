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
        List<DefaultItem> items = x.getAll();
        for(DefaultItem item : items){
                histories.add(new BidHistory(item.getLotId()));
        }

        //adding bids to the history
        BidDAO bidDAO = new BidDAO();
        List<Bid> bids = bidDAO.getAll();
        for(Bid bid : bids){
            get(bid.getLotId()).addBid(bid);
        }
    }

    public ArrayList<BidHistory> getAll() {
        return histories;
    }

    public BidHistory get(int lot_id){
        for (BidHistory history: histories) {
            if(history.getLotId() == lot_id)
                return history;
        }
        return null;
    }
}

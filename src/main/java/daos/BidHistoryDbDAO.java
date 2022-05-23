package daos;

import model.Bid;
import model.BidHistory;
import model.DefaultItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BidHistoryDbDAO {
    private ArrayList<BidHistory> histories = new ArrayList<>();

    public BidHistoryDbDAO() throws SQLException {
        //creating an empty bid history for every item
        ItemDbDAO temp = new ItemDbDAO();
        List<DefaultItem> items = temp.getAll();
        for(DefaultItem item : items){
            histories.add(new BidHistory(item.getLotId()));
        }

        //adding bids to the history
        BidDbDAO bidDbDAO = new BidDbDAO();
        List<Bid> bids = bidDbDAO.getAll();
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

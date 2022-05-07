package daos;

import model.Bid;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BidDAO {
    private ArrayList<Bid> bids = new ArrayList<>();

    public BidDAO() {
        bids.add(new Bid(2300, LocalDateTime.of(2022, 5, 5, 5, 5, 5), 1, 1));
        bids.add(new Bid(2800, LocalDateTime.of(2022, 5, 5, 5, 10, 10), 2, 1));
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }
}

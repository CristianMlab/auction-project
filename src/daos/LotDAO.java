package daos;

import model.Bid_History;
import model.Item;
import model.Lot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class LotDAO {

    private ArrayList<Lot> lots = new ArrayList<>();

    public LotDAO() {
        Map<Integer, Integer> auction_ids = new TreeMap<>();
        Map<Integer, String> item_titles = new TreeMap<>();
        Map<Integer, Integer> starting_bids = new TreeMap<>();
        Map<Integer, LocalDateTime> closing_datetimes = new TreeMap<>();

        auction_ids.put(1, 1);
        item_titles.put(1, "big item");
        starting_bids.put(1, 2000);
        closing_datetimes.put(1, LocalDateTime.of(2022,6,13,1,0,0));

        auction_ids.put(2, 1);
        item_titles.put(2, "big item 2");
        starting_bids.put(2, 3000);
        closing_datetimes.put(2, LocalDateTime.of(2022,5,12,6,0,0));

        auction_ids.put(3, 2);
        item_titles.put(3, "big item 3");
        starting_bids.put(3, 4000);
        closing_datetimes.put(3, LocalDateTime.of(2022,6,13,2,0,0));

        auction_ids.put(4, 2);
        item_titles.put(4, "big item 4");
        starting_bids.put(4, 5000);
        closing_datetimes.put(4, LocalDateTime.of(2022,5,15,8,0,0));

        //creating the lots using the items and bid histories
        ItemDAO itemDAO = new ItemDAO();
        BidHistoryDAO bidHistoryDAO = new BidHistoryDAO();
        ArrayList<Item> items = itemDAO.getItems();
        for(Item item : items){
            int id = item.get_lot_id();
            Bid_History history = bidHistoryDAO.get_history_by_lot(id);

            Lot new_lot = new Lot( id, auction_ids.get(id), item_titles.get(id), item, starting_bids.get(id), closing_datetimes.get(id));
            new_lot.set_bid_history(history);
            lots.add(new_lot);
        }
    }

    public void add(Lot new_lot){
        lots.add(new_lot);
    }

    public Lot get_lot_by_id(int id){
        for (Lot lot: lots) {
            if(lot.get_lot_id() == id)
                return lot;
        }
        return null;
    }

    public ArrayList<Lot> getLots() {
        return lots;
    }
}

package daos;

import csv.services.CustomCSVReader;
import model.BidHistory;
import model.DefaultItem;
import model.Lot;

import java.util.ArrayList;
import java.util.List;

public class LotDAO {

    private List<Lot> lots = new ArrayList<>();

    public LotDAO() {
        /*
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
        closing_datetimes.put(2, LocalDateTime.of(2022,6,12,6,0,0));

        auction_ids.put(3, 2);
        item_titles.put(3, "big item 3");
        starting_bids.put(3, 4000);
        closing_datetimes.put(3, LocalDateTime.of(2022,6,13,2,0,0));

        auction_ids.put(4, 2);
        item_titles.put(4, "big item 4");
        starting_bids.put(4, 5000);
        closing_datetimes.put(4, LocalDateTime.of(2022,6,15,8,0,0));
        */

        try {
            lots = CustomCSVReader.getInstance().readAll(Lot.class, "src/main/resources/csv/lots.csv");
        } catch(Exception e){
            lots = null;
            e.printStackTrace();
        }

        //creating the lots using the items and bid histories
        ItemDAO itemDAO = new ItemDAO();
        BidHistoryDAO bidHistoryDAO = new BidHistoryDAO();
        List<DefaultItem> items = itemDAO.getItems();
        for(DefaultItem item : items){
            int id = item.get_lot_id();
            BidHistory history = bidHistoryDAO.getHistoryByLot(id);

            getLotById(id).setBidHistory(history);
            getLotById(id).setItem(item);
        }
    }

    public void save(Lot new_lot){
        lots.add(new_lot);
    }

    public void delete(Lot lot){
        lots.remove(lot);
    }

    public void update(Lot lot, String[] params){

    }

    public Lot getLotById(int id){
        for (Lot lot: lots) {
            if(lot.getLotId() == id)
                return lot;
        }
        return null;
    }

    public List<Lot> getLots() {
        return lots;
    }
}

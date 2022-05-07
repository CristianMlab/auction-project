package daos;

import model.Auction;
import model.Lot;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AuctionDAO {
    private ArrayList<Auction> auctions = new ArrayList<>();

    public AuctionDAO() {
        auctions.add(new Auction(1, "Bijoux exclusifs en Diamants", LocalDateTime.of(2022, 6, 20, 4, 0, 0), "Online Auction"));
        auctions.add(new Auction(2, "random auction", LocalDateTime.of(2022, 6, 15, 7, 0, 0), "Online Auction"));

        //adding all lots to their auctions
        LotDAO lotDAO = new LotDAO();
        ArrayList<Lot> lots = lotDAO.getLots();
        for (Lot lot: lots) {
            if(get_auction_by_id(lot.get_auction_id()) != null)
                get_auction_by_id(lot.get_auction_id()).add_lot(lot);
        }
    }

    public void add(Auction auc){
        auctions.add(auc);
    }

    public void remove(int auction_id){
        auctions.removeIf(auc -> auc.get_id() == auction_id);
    }

    public ArrayList<Auction> getAuctions() {
        return auctions;
    }

    public Auction get_auction_by_id(int id){
        for (Auction auc: auctions) {
            if(auc.get_id() == id)
                return auc;
        }
        return null;
    }
}

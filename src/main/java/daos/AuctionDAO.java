package daos;

import csv.services.CustomCSVReader;
import model.Auction;
import model.Lot;

import java.util.ArrayList;
import java.util.List;

public class AuctionDAO {
    private List<Auction> auctions = new ArrayList<>();

    public AuctionDAO() {
        try {
            auctions = CustomCSVReader.getInstance().readAll(Auction.class, "src/main/resources/csv/auctions.csv");
        } catch(Exception e){
            auctions = null;
            e.printStackTrace();
        }

        //adding all lots to their auctions
        LotDAO lotDAO = new LotDAO();
        List<Lot> lots = lotDAO.getLots();
        for (Lot lot: lots) {
            if(get_auction_by_id(lot.get_auction_id()) != null)
                get_auction_by_id(lot.get_auction_id()).add_lot(lot);
        }
    }

    public void save(Auction auc){
        auctions.add(auc);
    }

    public void delete(Auction auc){
        auctions.remove(auc);
    }

    public void delete(int auction_id){
        auctions.removeIf(auc -> auc.get_id() == auction_id);
    }

    public void update(Auction auc, String[] params){

    }

    public List<Auction> getAuctions() {
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

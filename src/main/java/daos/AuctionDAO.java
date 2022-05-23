package daos;

import csv.services.CustomCSVReader;
import model.Auction;
import model.Lot;

import java.util.ArrayList;
import java.util.List;

public class AuctionDAO implements DAO<Auction> {
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
        List<Lot> lots = lotDAO.getAll();
        for (Lot lot: lots) {
            if(get(lot.getAuctionId()) != null)
                get(lot.getAuctionId()).addLot(lot);
        }
    }

    public void save(Auction auc){
        auctions.add(auc);
    }

    public void delete(Auction auc){
        auctions.remove(auc);
    }

    public void update(Auction auc, String[] params){

    }

    public List<Auction> getAll() {
        return auctions;
    }

    public Auction get(int id){
        for (Auction auc: auctions) {
            if(auc.getId() == id)
                return auc;
        }
        return null;
    }
}

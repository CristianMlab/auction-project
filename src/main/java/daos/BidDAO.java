package daos;

import csv.services.CustomCSVReader;
import model.Bid;

import java.util.ArrayList;
import java.util.List;

public class BidDAO implements DAO<Bid> {
    private List<Bid> bids = new ArrayList<>();

    public BidDAO() {
        try {
            bids = CustomCSVReader.getInstance().readAll(Bid.class, "src/main/resources/csv/bids.csv");
        } catch(Exception e){
            bids = null;
            e.printStackTrace();
        }
    }

    public void save(Bid bid){
        bids.add(bid);
    }

    public void delete(Bid bid){
        bids.remove(bid);
    }

    public void update(Bid bid, String[] params){

    }

    public Bid get(int id){
        return null;
    }

    public List<Bid> getAll() {
        return bids;
    }
}

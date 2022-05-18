package model;

import java.util.ArrayList;

public class Bid_History {
    private int lotId;
    private ArrayList<Bid> bids = new ArrayList<Bid>();

    public void display(){
        for (Bid bid:bids) {
            bid.display();
        }
    }

    public Bid_History(int lotId) {
        this.lotId = lotId;
    }

    public int getLotId() {
        return lotId;
    }

    public Bid getLastBid(){
        if(bids.isEmpty())
            return null;

        return bids.get(bids.size()-1);
    }

    public void removeLastBid(){
        if(!bids.isEmpty()){
            bids.remove(bids.size()-1);
        }
    }

    public Bid findUserBid(int user_id){
        for (Bid bid: bids) {
            if(bid.getUserId() == user_id){
                return bid;
            }
        }
        return null;
    }

    public int getNumberOfBids(){
        return bids.size();
    }

    public void addBid(Bid x){
        bids.add(x);
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }
}

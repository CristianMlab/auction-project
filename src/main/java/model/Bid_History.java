package model;

import java.util.ArrayList;

public class Bid_History {
    private int lot_id;
    private ArrayList<Bid> bids = new ArrayList<Bid>();

    public void display(){
        for (Bid bid:bids) {
            bid.display();
        }
    }

    public Bid_History(int lot_id) {
        this.lot_id = lot_id;
    }

    public int get_lot_id() {
        return lot_id;
    }

    public Bid get_last_bid(){
        if(bids.isEmpty())
            return null;

        return bids.get(bids.size()-1);
    }

    public void remove_last_bid(){
        if(!bids.isEmpty()){
            bids.remove(bids.size()-1);
        }
    }

    public Bid find_user_bid(int user_id){
        for (Bid bid: bids) {
            if(bid.getUser_id() == user_id){
                return bid;
            }
        }
        return null;
    }

    public int get_number_of_bids(){
        return bids.size();
    }

    public void add_bid(Bid x){
        bids.add(x);
    }
}

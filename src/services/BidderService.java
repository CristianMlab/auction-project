package services;

import daos.*;
import model.*;

import java.util.ArrayList;

public class BidderService {
    private LotDAO lotDAO;
    private AuctionDAO auctionDAO;
    private UserDAO userDAO;
    private BidHistoryDAO bidhistoryDAO;

    public BidderService(LotDAO lotDAO, AuctionDAO auctionDAO, UserDAO userDAO, BidHistoryDAO bidhistoryDAO) {
        this.lotDAO = lotDAO;
        this.auctionDAO = auctionDAO;
        this.userDAO = userDAO;
        this.bidhistoryDAO = bidhistoryDAO;
    }

    public void displayAuction(int auction_id){
        auctionDAO.get_auction_by_id(auction_id).display();
    }

    public void displayLot(int lot_id){
        lotDAO.get_lot_by_id(lot_id).display();
    }

    public void displayBidsByUser(int user_id){
        ArrayList<Bid_History> histories = bidhistoryDAO.getHistories();
        ArrayList<Bid> results = new ArrayList<>();
        for (Bid_History history: histories) {
            if(history.find_user_bid(user_id) != null){
                results.add(history.find_user_bid(user_id));
                history.find_user_bid(user_id).display();
            }
        }
    }

    public void place_bid(int lot_id, int value, int user_id){
        lotDAO.get_lot_by_id(lot_id).bid(value, user_id);
    }

    public void change_bid(int lot_id, int new_value, int user_id){
        if(lotDAO.get_lot_by_id(lot_id).last_bid_user() == user_id){
            //todo: have to check that the new_value isn't too low
            lotDAO.get_lot_by_id(lot_id).getBids().get_last_bid().setValue(new_value);
        } else {
            System.out.println("Bid can't be changed since you did not place the last bid");
        }
    }

    public void retract_bid(int lot_id, int user_id){
        if(lotDAO.get_lot_by_id(lot_id).last_bid_user() == user_id){
            lotDAO.get_lot_by_id(lot_id).getBids().remove_last_bid();
        } else {
            System.out.println("Bid can't be changed since you did not place the last bid");
        }
    }
}

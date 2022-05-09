package services;

import csv.services.AuditService;
import csv.services.CustomCSVWriter;
import daos.*;
import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BidderService {
    private BidDAO bidDAO;
    private LotDAO lotDAO;
    private AuctionDAO auctionDAO;
    private UserDAO userDAO;


    public BidderService(LotDAO lotDAO, AuctionDAO auctionDAO, UserDAO userDAO, BidDAO bidDAO) {
        this.lotDAO = lotDAO;
        this.auctionDAO = auctionDAO;
        this.userDAO = userDAO;
        this.bidDAO = bidDAO;
    }

    public void displayAuction(int auction_id){
        auctionDAO.get_auction_by_id(auction_id).display();
        try {
            AuditService.getInstance().log("display auction", "src/main/resources/csv/audit.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void displayLot(int lot_id){
        lotDAO.get_lot_by_id(lot_id).display();
        try {
            AuditService.getInstance().log("display lot", "src/main/resources/csv/audit.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void displayBidsByUser(int user_id){
        List<Lot> lots = lotDAO.getLots();
        ArrayList<Bid> results = new ArrayList<>();
        for (Lot lot: lots) {
            Bid_History history = lot.getBids();
            if(history.find_user_bid(user_id) != null){
                results.add(history.find_user_bid(user_id));
                history.find_user_bid(user_id).display();
            }
        }
        try {
            AuditService.getInstance().log("display bids", "src/main/resources/csv/audit.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void place_bid(int lot_id, int value, int user_id){
        Bid bid = new Bid(value, LocalDateTime.now(), user_id, lot_id);
        lotDAO.get_lot_by_id(lot_id).bid(value, user_id);
        bidDAO.save(bid);
        try {
            CustomCSVWriter.getInstance().appendObject(Bid.class, bid, "src/main/resources/csv/bids.csv");
            AuditService.getInstance().log("place bid", "src/main/resources/csv/audit.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void retract_bid(int lot_id, int user_id){
        Bid bid = lotDAO.get_lot_by_id(lot_id).getBids().get_last_bid();
        if(lotDAO.get_lot_by_id(lot_id).last_bid_user() == user_id){
            lotDAO.get_lot_by_id(lot_id).getBids().remove_last_bid();
            bidDAO.delete(bid);
            try {
                CustomCSVWriter.getInstance().writeAll(Bid.class, bidDAO.getBids(), "src/main/resources/csv/bids.csv", Bid.getHeader());
                AuditService.getInstance().log("retract bid", "src/main/resources/csv/audit.csv");
            } catch(Exception e){
                e.printStackTrace();
            }
        } else {
            System.out.println("Bid can't be changed since you did not place the last bid");
        }
    }

    public void change_bid(int lot_id, int new_value, int user_id){
        Bid bid = lotDAO.get_lot_by_id(lot_id).getBids().get_last_bid();
        if(bid.getUser_id() == user_id){
            retract_bid(lot_id, user_id);
            place_bid(lot_id, new_value, user_id);
        } else {
            System.out.println("Bid can't be changed since you did not place the last bid");
        }
    }
}

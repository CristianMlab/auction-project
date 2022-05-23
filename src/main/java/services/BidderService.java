package services;

import csv.services.AuditService;
import csv.services.CustomCSVWriter;
import daos.*;
import model.*;

import java.time.LocalDateTime;
import java.util.List;

public class BidderService {
    private DAO<Bid> bidDAO;
    private DAO<Lot> lotDAO;
    private DAO<Auction> auctionDAO;
    private DAO<User> userDAO;


    public BidderService(DAO<Lot> lotDAO, DAO<Auction> auctionDAO, DAO<User> userDAO, DAO<Bid> bidDAO) {
        this.lotDAO = lotDAO;
        this.auctionDAO = auctionDAO;
        this.userDAO = userDAO;
        this.bidDAO = bidDAO;
    }

    public void displayAll(){
        List<Auction> auctions = auctionDAO.getAll();
        System.out.println("Available auctions:");
        for (Auction auc : auctions) {
            System.out.println( "AUCTION ID " + auc.getId() + ": " + auc.getName());
        }
    }

    public void displayAuction(int auctionId){
        auctionDAO.get(auctionId).display();
        try {
            AuditService.getInstance().log("display auction", "src/main/resources/csv/audit.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void displayLot(int lotId){
        lotDAO.get(lotId).display();
        try {
            AuditService.getInstance().log("display lot", "src/main/resources/csv/audit.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void displayBidsByUser(int userId){
        List<Lot> lots = lotDAO.getAll();
        for (Lot lot: lots) {
            BidHistory history = lot.getBids();
            if(history.findUserBid(userId) != null){
                history.findUserBid(userId).display();
            }
        }
        try {
            AuditService.getInstance().log("display bids", "src/main/resources/csv/audit.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void placeBid(int lotId, double value, int userId){
        Bid bid = new Bid(value, LocalDateTime.now(), userId, lotId);
        lotDAO.get(lotId).bid(value, userId);
        bidDAO.save(bid);
        try {
            CustomCSVWriter.getInstance().appendObject(Bid.class, bid, "src/main/resources/csv/bids.csv");
            AuditService.getInstance().log("place bid", "src/main/resources/csv/audit.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void retractBid(int lotId, int userId){
        Bid bid = lotDAO.get(lotId).getBids().getLastBid();
        if(lotDAO.get(lotId).lastBidUser() == userId){
            lotDAO.get(lotId).getBids().removeLastBid();
            bidDAO.delete(bid);
            try {
                CustomCSVWriter.getInstance().writeAll(Bid.class, bidDAO.getAll(), "src/main/resources/csv/bids.csv", Bid.getHeader());
                AuditService.getInstance().log("retract bid", "src/main/resources/csv/audit.csv");
            } catch(Exception e){
                e.printStackTrace();
            }
        } else {
            System.out.println("Bid can't be changed since you did not place the last bid");
        }
    }

    public void changeBid(int lotId, double newValue, int userId){
        Bid bid = lotDAO.get(lotId).getBids().getLastBid();
        if(bid.getUserId() == userId){
            retractBid(lotId, userId);
            placeBid(lotId, newValue, userId);
        } else {
            System.out.println("Bid can't be changed since you did not place the last bid");
        }
    }
}

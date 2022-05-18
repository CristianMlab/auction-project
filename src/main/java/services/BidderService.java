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

    public void displayAuction(int auctionId){
        auctionDAO.getAuctionById(auctionId).display();
        try {
            AuditService.getInstance().log("display auction", "src/main/resources/csv/audit.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void displayLot(int lotId){
        lotDAO.getLotById(lotId).display();
        try {
            AuditService.getInstance().log("display lot", "src/main/resources/csv/audit.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void displayBidsByUser(int userId){
        List<Lot> lots = lotDAO.getLots();
        ArrayList<Bid> results = new ArrayList<>();
        for (Lot lot: lots) {
            Bid_History history = lot.getBids();
            if(history.findUserBid(userId) != null){
                results.add(history.findUserBid(userId));
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
        lotDAO.getLotById(lotId).bid(value, userId);
        bidDAO.save(bid);
        try {
            CustomCSVWriter.getInstance().appendObject(Bid.class, bid, "src/main/resources/csv/bids.csv");
            AuditService.getInstance().log("place bid", "src/main/resources/csv/audit.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void retractBid(int lotId, int userId){
        Bid bid = lotDAO.getLotById(lotId).getBids().getLastBid();
        if(lotDAO.getLotById(lotId).lastBidUser() == userId){
            lotDAO.getLotById(lotId).getBids().removeLastBid();
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

    public void changeBid(int lotId, double newValue, int userId){
        Bid bid = lotDAO.getLotById(lotId).getBids().getLastBid();
        if(bid.getUserId() == userId){
            retractBid(lotId, userId);
            placeBid(lotId, newValue, userId);
        } else {
            System.out.println("Bid can't be changed since you did not place the last bid");
        }
    }
}

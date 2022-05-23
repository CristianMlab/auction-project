package services;

import csv.services.AuditService;
import csv.services.CustomCSVWriter;
import daos.*;
import model.*;

import java.util.List;

public class AdminService {

    private DAO<Bid> bidDAO;
    private DAO<Lot> lotDAO;
    private DAO<Auction> auctionDAO;
    private DAO<User> userDAO;
    private DAO<DefaultItem> itemDAO;

    public AdminService(DAO<Lot> lotDAO, DAO<Auction> auctionDAO, DAO<User> userDAO, DAO<Bid> bidDAO, DAO<DefaultItem> itemDAO) {
        this.lotDAO = lotDAO;
        this.auctionDAO = auctionDAO;
        this.userDAO = userDAO;
        this.bidDAO = bidDAO;
        this.itemDAO = itemDAO;
    }


    public void deleteBid(Bid bid){
        bidDAO.delete(bid);
        try {
            CustomCSVWriter.getInstance().writeAll(Bid.class, bidDAO.getAll(), "src/main/resources/csv/bids.csv", Bid.getHeader());
            AuditService.getInstance().log("delete bid", "src/main/resources/csv/audit.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteDefaultLot(Lot lot){
        List<Bid> bids = lot.getBids().getBids();
        for (Bid bid: bids) {
            deleteBid(bid);
        }

        DefaultItem item = itemDAO.get(lot.getId());
        itemDAO.delete(item);
        lotDAO.delete(lot);

        try {
            CustomCSVWriter.getInstance().writeAll(DefaultItem.class, itemDAO.getAll(), "src/main/resources/csv/defaultItems.csv", DefaultItem.getHeader());
            CustomCSVWriter.getInstance().writeAll(Lot.class, lotDAO.getAll(), "src/main/resources/csv/lots.csv", Lot.getHeader());
            AuditService.getInstance().log("delete lot", "src/main/resources/csv/audit.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteAuction(Auction auc){
        List<Lot> lots = auc.getLots();
        for (Lot lot: lots) {
            deleteDefaultLot(lotDAO.get(lot.getId()));
        }

        auctionDAO.delete(auc);
        try {
            CustomCSVWriter.getInstance().writeAll(Auction.class, auctionDAO.getAll(), "src/main/resources/csv/auctions.csv", Auction.getHeader());
            AuditService.getInstance().log("delete auction", "src/main/resources/csv/audit.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}

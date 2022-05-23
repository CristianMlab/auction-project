package services;

import csv.services.AuditService;
import csv.services.CustomCSVWriter;
import daos.*;
import model.*;

import java.time.LocalDateTime;

public class SellerService {
    private DAO<Lot> lotDAO;
    private DAO<Auction> auctionDAO;
    private DAO<User> userDAO;
    private DAO<Bid> bidDAO;
    private DAO<DefaultItem> itemDAO;

    public SellerService(DAO<Lot> lotDAO, DAO<Auction> auctionDAO, DAO<User> userDAO, DAO<Bid> bidDAO, DAO<DefaultItem> itemDAO) {
        this.lotDAO = lotDAO;
        this.auctionDAO = auctionDAO;
        this.userDAO = userDAO;
        this.bidDAO = bidDAO;
        this.itemDAO = itemDAO;
    }

    public void createDefaultLot(int lotId, int auctionId, String lotName, String description, double startingBid, LocalDateTime closingDatetime){
        DefaultItem item = new DefaultItem(lotId, description);
        itemDAO.save(item);
        Lot new_lot = new Lot(lotName, lotId, startingBid, auctionId, closingDatetime);
        new_lot.setBids(new BidHistory(lotId));
        new_lot.setItem(item);
        lotDAO.save(new_lot);
        try {
            CustomCSVWriter.getInstance().appendObject(DefaultItem.class, new DefaultItem(lotId, description), "src/main/resources/csv/defaultItems.csv");
            CustomCSVWriter.getInstance().appendObject(Lot.class, new_lot, "src/main/resources/csv/lots.csv");
            AuditService.getInstance().log("create lot", "src/main/resources/csv/audit.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
        auctionDAO.get(auctionId).addLot(new_lot);
    }

    public void createAuction(int auctionId, String name, LocalDateTime closingDatetime, String details){
        Auction new_auc = new Auction(auctionId, name, closingDatetime, details);
        auctionDAO.save(new_auc);
        try {
            CustomCSVWriter.getInstance().appendObject(Auction.class, new_auc, "src/main/resources/csv/auctions.csv");
            AuditService.getInstance().log("create auction", "src/main/resources/csv/audit.csv");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

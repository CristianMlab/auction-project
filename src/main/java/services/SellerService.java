package services;

import csv.services.CustomCSVWriter;
import daos.*;
import model.*;

import java.time.LocalDateTime;

public class SellerService {
    private LotDAO lotDAO;
    private AuctionDAO auctionDAO;
    private UserDAO userDAO;
    private BidDAO bidDAO;
    private ItemDAO itemDAO;

    public SellerService(LotDAO lotDAO, AuctionDAO auctionDAO, UserDAO userDAO, BidDAO bidDAO, ItemDAO itemDAO) {
        this.lotDAO = lotDAO;
        this.auctionDAO = auctionDAO;
        this.userDAO = userDAO;
        this.bidDAO = bidDAO;
        this.itemDAO = itemDAO;
    }

    public void createDefaultLot(int lot_id, int auction_id, String lot_name, String description, int starting_bid, LocalDateTime closing_datetime){
        Default_item item = new Default_item(lot_id, description);
        itemDAO.save(item);
        try {
            CustomCSVWriter.getInstance().appendObject(Default_item.class, new Default_item(lot_id, description), "src/main/resources/csv/default_items.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
        Lot new_lot = new Lot(lot_name, lot_id, starting_bid, auction_id, closing_datetime);
        new_lot.set_bid_history(new Bid_History(lot_id));
        new_lot.setItem(item);
        lotDAO.save(new_lot);
        try {
            CustomCSVWriter.getInstance().appendObject(Lot.class, new_lot, "src/main/resources/csv/lots.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
        auctionDAO.get_auction_by_id(auction_id).add_lot(new_lot);
    }

    public void createAuction(int auction_id, String name, LocalDateTime closing_datetime, String details){
        Auction new_auc = new Auction(auction_id, name, closing_datetime, details);
        auctionDAO.save(new_auc);
        try {
            CustomCSVWriter.getInstance().appendObject(Auction.class, new_auc, "src/main/resources/csv/auctions.csv");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

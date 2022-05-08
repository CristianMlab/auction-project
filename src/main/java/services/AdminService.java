package services;

import csv.services.CustomCSVWriter;
import daos.*;
import model.*;

import java.util.List;

public class AdminService {

    private BidDAO bidDAO;
    private LotDAO lotDAO;
    private AuctionDAO auctionDAO;
    private UserDAO userDAO;
    private ItemDAO itemDAO;

    public AdminService(LotDAO lotDAO, AuctionDAO auctionDAO, UserDAO userDAO, BidDAO bidDAO, ItemDAO itemDAO) {
        this.lotDAO = lotDAO;
        this.auctionDAO = auctionDAO;
        this.userDAO = userDAO;
        this.bidDAO = bidDAO;
        this.itemDAO = itemDAO;
    }


    public void deleteBid(Bid bid){
        bidDAO.delete(bid);
        try {
            CustomCSVWriter.getInstance().writeAll(Bid.class, bidDAO.getBids(), "src/main/resources/csv/bids.csv", Bid.getHeader());
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteDefaultLot(Lot lot){
        List<Bid> bids = lot.getBids().getBids();
        for (Bid bid: bids) {
            deleteBid(bid);
        }

        Default_item item = itemDAO.get_item_by_lot(lot.get_lot_id());
        itemDAO.delete(item);
        try {
            CustomCSVWriter.getInstance().writeAll(Default_item.class, itemDAO.getItems(), "src/main/resources/csv/default_items.csv", Default_item.getHeader());
        } catch(Exception e){
            e.printStackTrace();
        }

        lotDAO.delete(lot);
        try {
            CustomCSVWriter.getInstance().writeAll(Lot.class, lotDAO.getLots(), "src/main/resources/csv/lots.csv", Lot.getHeader());
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteAuction(Auction auc){
        List<Lot> lots = auc.getLots();
        for (Lot lot: lots) {
            deleteDefaultLot(lotDAO.get_lot_by_id(lot.get_lot_id()));
        }

        auctionDAO.delete(auc);
        try {
            CustomCSVWriter.getInstance().writeAll(Auction.class, auctionDAO.getAuctions(), "src/main/resources/csv/auctions.csv", Auction.getHeader());
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}

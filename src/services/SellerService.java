package services;

import daos.AuctionDAO;
import daos.BidHistoryDAO;
import daos.LotDAO;
import daos.UserDAO;
import model.*;

import java.time.LocalDateTime;

public class SellerService {
    private LotDAO lotDAO;
    private AuctionDAO auctionDAO;
    private UserDAO userDAO;
    private BidHistoryDAO bidhistoryDAO;

    public SellerService(LotDAO lotDAO, AuctionDAO auctionDAO, UserDAO userDAO, BidHistoryDAO bidhistoryDAO) {
        this.lotDAO = lotDAO;
        this.auctionDAO = auctionDAO;
        this.userDAO = userDAO;
        this.bidhistoryDAO = bidhistoryDAO;
    }

    public void createDefaultLot(int lot_id, int auction_id, String item_title, String description, int starting_bid, LocalDateTime closing_datetime){
        Item item = new Default_item(lot_id, description);
        Lot new_lot = new Lot(lot_id, auction_id, item_title, item, starting_bid, closing_datetime);
        new_lot.set_bid_history(new Bid_History(lot_id));
        lotDAO.add(new_lot);
        auctionDAO.get_auction_by_id(auction_id).add_lot(new_lot);
    }

    public void createAuction(int auction_id, String name, LocalDateTime closing_datetime, String details){
        Auction new_auc = new Auction(auction_id, name, closing_datetime, details);
        auctionDAO.add(new_auc);
    }
}

import daos.*;
import model.*;
import services.BidderService;
import services.SellerService;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        AuctionDAO auctionDAO = new AuctionDAO();
        LotDAO lotDAO = new LotDAO();
        UserDAO userDAO = new UserDAO();
        BidHistoryDAO bidhistoryDAO = new BidHistoryDAO();

        BidderService bidderService = new BidderService(lotDAO, auctionDAO, userDAO, bidhistoryDAO);

        bidderService.displayAuction(1);
        bidderService.displayLot(1);
        bidderService.place_bid(1, 2300, 1);
        bidderService.displayLot(1);
        bidderService.place_bid(1, 2800, 2);
        bidderService.displayLot(1);
        bidderService.retract_bid(1, 2);
        bidderService.displayLot(1);

        SellerService sellerService = new SellerService(lotDAO, auctionDAO, userDAO, bidhistoryDAO);

        sellerService.createAuction(3, "test auction", LocalDateTime.of(2022, 6,2,5,0,0), "detalii");
        bidderService.displayAuction(3);
        sellerService.createDefaultLot(5, 3, "test_lot", "description", 1000, LocalDateTime.of(2022,6, 1, 5, 0, 0));
        bidderService.displayAuction(3);
        bidderService.displayLot(5);
    }
}

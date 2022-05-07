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

        BidderService bidderService = new BidderService(lotDAO, auctionDAO, userDAO);

        bidderService.displayAuction(1);
        bidderService.displayLot(1);
        bidderService.retract_bid(1, 2);
        bidderService.displayLot(1);

        bidderService.displayBidsByUser(1);

        System.out.println("ALTCEVA");

        SellerService sellerService = new SellerService(lotDAO, auctionDAO, userDAO);

        sellerService.createAuction(3, "test auction", LocalDateTime.of(2022, 6,2,5,0,0), "detalii");
        bidderService.displayAuction(3);
        sellerService.createDefaultLot(5, 3, "test_lot", "description", 1000, LocalDateTime.of(2022,6, 1, 5, 0, 0));
        bidderService.displayAuction(3);
        bidderService.displayLot(5);
    }
}

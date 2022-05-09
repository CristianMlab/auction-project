import daos.*;
import model.*;
import csv.services.*;
import services.AdminService;
import services.BidderService;
import services.SellerService;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        AuctionDAO auctionDAO = new AuctionDAO();
        LotDAO lotDAO = new LotDAO();
        UserDAO userDAO = new UserDAO();
        BidDAO bidDAO = new BidDAO();
        ItemDAO itemDAO = new ItemDAO();

        BidderService bidderService = new BidderService(lotDAO, auctionDAO, userDAO, bidDAO);
        SellerService sellerService = new SellerService(lotDAO, auctionDAO, userDAO, bidDAO, itemDAO);
        AdminService adminService = new AdminService(lotDAO, auctionDAO, userDAO, bidDAO, itemDAO);

        /*
        bidderService.displayAuction(1);
        bidderService.displayLot(1);
        bidderService.retract_bid(1, 2);
        bidderService.displayLot(1);

        bidderService.displayBidsByUser(1);

        System.out.println("ALTCEVA");

        sellerService.createAuction(3, "test auction", LocalDateTime.of(2022, 6, 2, 5, 0, 0), "detalii");
        bidderService.displayAuction(3);
        sellerService.createDefaultLot(5, 3, "test_lot", "description", 1000, LocalDateTime.of(2022, 6, 1, 5, 0, 0));
        bidderService.displayAuction(3);
        bidderService.displayLot(5);
        */


        /*sellerService.createAuction(3, "test auction", LocalDateTime.of(2022, 6, 2, 5, 0, 0), "detalii");
        bidderService.displayAuction(3);
        sellerService.createDefaultLot(5, 3, "test_lot", "description", 1000, LocalDateTime.of(2022, 6, 1, 5, 0, 0));
        bidderService.displayAuction(3);
        bidderService.displayLot(5);*/


        //adminService.deleteAuction(auctionDAO.get_auction_by_id(3));
        //adminService.deleteLot(lotDAO.get_lot_by_id(5));
        //adminService.deleteAuction(auctionDAO.get_auction_by_id(3));

        bidderService.displayAuction(1);
        bidderService.displayAuction(2);
        bidderService.displayLot(2);
    }
}

import daos.*;
import model.*;
import csv.services.*;
import services.AdminService;
import services.BidderService;
import services.SellerService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

        Scanner in = new Scanner(System.in);
        String line;

        while ((line = in.nextLine()) != null) {
            List<String> cmd = Arrays.asList(line.split(" "));

            System.out.println(cmd.get(0));
            switch (cmd.get(0)) {
                case "display_auction" -> {
                    int id = Integer.parseInt(cmd.get(1));
                    bidderService.displayAuction(id);
                }

                case "display_lot" -> {
                    int id = Integer.parseInt(cmd.get(1));
                    bidderService.displayLot(id);
                }

                case "place_bid" -> {
                    int lotId = Integer.parseInt(cmd.get(1));
                    double value = Double.parseDouble(cmd.get(2));
                    int userId = Integer.parseInt(cmd.get(3));
                    bidderService.placeBid(lotId, value, userId);
                }

                case "retract_bid" -> {
                    int lotId = Integer.parseInt(cmd.get(1));
                    int userId = Integer.parseInt(cmd.get(2));
                    bidderService.retractBid(lotId, userId);
                }

                case "change_bid" -> {
                    int lotId = Integer.parseInt(cmd.get(1));
                    double newValue = Double.parseDouble(cmd.get(2));
                    int userId = Integer.parseInt(cmd.get(3));
                    bidderService.changeBid(lotId, newValue, userId);
                }

                case "display_bids_by_user" -> {
                    int userId = Integer.parseInt(cmd.get(1));
                    bidderService.displayBidsByUser(userId);
                }

                case "delete_lot" -> {
                    int lotId = Integer.parseInt(cmd.get(1));
                    adminService.deleteDefaultLot(lotDAO.getLotById(lotId));
                }

                case "delete_auction" -> {
                    int auctionId = Integer.parseInt(cmd.get(1));
                    adminService.deleteAuction(auctionDAO.getAuctionById(auctionId));
                }

                case "create_lot" -> {
                    int lotId = Integer.parseInt(cmd.get(1));
                    int auctionId = Integer.parseInt(cmd.get(2));
                    String lotName = cmd.get(3);
                    String description = cmd.get(4);
                    int startingBid = Integer.parseInt(cmd.get(5));
                    LocalDateTime closingDateTime = LocalDateTime.parse(cmd.get(6), DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm:ss"));
                    sellerService.createDefaultLot(lotId, auctionId, lotName, description, startingBid, closingDateTime);
                }

                case "create_auction" -> {
                    int auctionId = Integer.parseInt(cmd.get(1));
                    String name = cmd.get(2);
                    LocalDateTime closingDateTime = LocalDateTime.parse(cmd.get(3), DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm:ss"));
                    String details = cmd.get(4);
                    sellerService.createAuction(auctionId, name, closingDateTime, details);
                }
            }
        }
    }
}

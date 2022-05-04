package services;

import daos.AuctionDAO;
import daos.BidHistoryDAO;
import daos.LotDAO;
import daos.UserDAO;

public class AdminService {

    private LotDAO lotDAO;
    private AuctionDAO auctionDAO;
    private UserDAO userDAO;
    private BidHistoryDAO bidhistoryDAO;

    public AdminService(LotDAO lotDAO, AuctionDAO auctionDAO, UserDAO userDAO, BidHistoryDAO bidhistoryDAO) {
        this.lotDAO = lotDAO;
        this.auctionDAO = auctionDAO;
        this.userDAO = userDAO;
        this.bidhistoryDAO = bidhistoryDAO;
    }
}

package model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class Lot {
    @CsvBindByName(column = "ID")
    private int id;

    @CsvBindByName(column = "AUCTION_ID")
    private int auctionId;

    @CsvBindByName(column = "LOT_NAME")
    private String lotName;

    @CsvBindByName(column = "STARTING_BID")
    private double startingBid;

    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    @CsvBindByName(column = "CLOSING_DATETIME")
    private LocalDateTime closingDatetime;

    private BidHistory bids;
    private DefaultItem item;

    public static String getHeader(){
        return "ID,AUCTION_ID,LOT_NAME,STARTING_BID,CLOSING_DATETIME";
    }

    public Lot(String lotName, int id, double startingBid, int auctionId, LocalDateTime closingDatetime) {
        this.lotName = lotName;
        this.id = id;
        this.startingBid = startingBid;
        this.auctionId = auctionId;
        this.closingDatetime = closingDatetime;
    }

    public Lot(){}

    public String toString(){
        return id + "," + auctionId + "," + lotName + "," + startingBid + "," + closingDatetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public DefaultItem getItem() {
        return item;
    }

    public void setItem(DefaultItem item) {
        this.item = item;
    }

    public void setBidHistory(BidHistory old_bids){
        bids = old_bids;
    }

    public String getLotName() {
        return lotName;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public int getLotId(){
        return id;
    }

    public double lastBidValue(){
        return bids.getLastBid().getValue();
    }

    public LocalDateTime lastBidTime(){
        return bids.getLastBid().getTime();
    }

    public int lastBidUser(){
        return bids.getLastBid().getUserId();
    }

    public BidHistory getBids() {
        return bids;
    }

    public void bid(double value, int user_id){
        double lastBid;
        LocalDateTime currentDate = LocalDateTime.now();
        if(currentDate.isAfter(closingDatetime)){
            System.out.println("The lot is closed, a bid cannot be placed");
        } else {
            Bid new_bid = new Bid(value, currentDate, user_id, id);
            if (bids.getLastBid() == null)
                lastBid = startingBid /1.1;
            else
                lastBid = lastBidValue();
            if (value < 1.1 * lastBid) {
                System.out.println("Can't place bid, the minimum bid is " + 1.1 * lastBidValue());
            } else {
                bids.addBid(new_bid);
            }
        }
    }

    public void display(){
        System.out.println();
        System.out.println(lotName);
        System.out.println("Lot ID: " + id);
        System.out.println("Starting Bid: " + startingBid);

        if(bids.getNumberOfBids() != 0)
            System.out.println("Current Bid: " + lastBidValue());

        System.out.println("Number of bids: " + bids.getNumberOfBids());

        Duration diff = Duration.between(LocalDateTime.now(), closingDatetime);
        String hms = String.format("%d days, %dh:%02dm:%02ds", diff.toHours()/24, diff.toHours()%24, diff.toMinutesPart(), diff.toSecondsPart());
        if(LocalDateTime.now().compareTo(closingDatetime) <= 0)
            System.out.println("Closes in: " + hms);
        else
            System.out.println("Bidding is closed");

        System.out.println("");
        System.out.println("Description:");
        item.display();
    }
}

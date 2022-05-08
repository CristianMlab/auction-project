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
    private int auction_id;

    @CsvBindByName(column = "LOT_NAME")
    private String lot_name;

    @CsvBindByName(column = "STARTING_BID")
    private double starting_bid;

    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    @CsvBindByName(column = "CLOSING_DATETIME")
    private LocalDateTime closing_datetime;

    private Bid_History bids;
    private Default_item item;

    public static String getHeader(){
        return "ID,AUCTION_ID,LOT_NAME,STARTING_BID,CLOSING_DATETIME";
    }

    public Lot(String lot_name,int id, double starting_bid, int auction_id, LocalDateTime closing_datetime) {
        this.lot_name = lot_name;
        this.id = id;
        this.starting_bid = starting_bid;
        this.auction_id = auction_id;
        this.closing_datetime = closing_datetime;
    }

    public Lot(){}

    public String toString(){
        return id + "," + auction_id + "," + lot_name + "," + starting_bid + "," + closing_datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Default_item getItem() {
        return item;
    }

    public void setItem(Default_item item) {
        this.item = item;
    }

    public void set_bid_history(Bid_History old_bids){
        bids = old_bids;
    }

    public String getLot_name() {
        return lot_name;
    }

    public int get_auction_id() {
        return auction_id;
    }

    public int get_lot_id(){
        return id;
    }

    public double last_bid_value(){
        return bids.get_last_bid().getValue();
    }

    public LocalDateTime last_bid_time(){
        return bids.get_last_bid().getTime();
    }

    public int last_bid_user(){
        return bids.get_last_bid().getUser_id();
    }

    public Bid_History getBids() {
        return bids;
    }

    public void bid(int value, int user_id){
        double last_bid;
        LocalDateTime current_date = LocalDateTime.now();
        if(current_date.isAfter(closing_datetime)){
            System.out.println("The lot is closed, a bid cannot be placed");
        } else {
            Bid new_bid = new Bid(value, current_date, user_id, id);
            if (bids.get_last_bid() == null)
                last_bid = starting_bid/1.1;
            else
                last_bid = last_bid_value();
            if (value < 1.1 * last_bid) {
                System.out.println("Can't place bid, the minimum bid is " + 1.1 * last_bid_value());
            } else {
                bids.add_bid(new_bid);
            }
        }
    }

    public void display(){
        System.out.println();
        System.out.println(lot_name);
        System.out.println("Lot ID: " + id);
        System.out.println("Starting Bid: " + starting_bid);

        if(bids.get_number_of_bids() != 0)
            System.out.println("Current Bid: " + last_bid_value());

        System.out.println("Number of bids: " + bids.get_number_of_bids());

        Duration diff = Duration.between(LocalDateTime.now(), closing_datetime);
        String hms = String.format("%d days, %dh:%02dm:%02ds", diff.toHours()/24, diff.toHours()%24, diff.toMinutesPart(), diff.toSecondsPart());
        if(LocalDateTime.now().compareTo(closing_datetime) <= 0)
            System.out.println("Closes in: " + hms);
        else
            System.out.println("Bidding is closed");

        System.out.println("");
        System.out.println("Description:");
        item.display();
    }
}

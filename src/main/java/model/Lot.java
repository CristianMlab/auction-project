package model;

import java.time.LocalDateTime;
import java.time.Duration;

public class Lot {
    private String item_title;
    private Item item;
    private int id;
    private double starting_bid;
    private int auction_id;

    private Bid_History bids;
    private LocalDateTime closing_datetime;

    public Lot(int id, int auction_id, String item_title, Item item, int starting_bid, LocalDateTime closing_datetime) {
        this.item_title = item_title;
        if(item.get_lot_id() != id){
            this.item = new Default_item(id, "an error occured");
        } else {
            this.item = item;
        }
        this.item = item;
        this.id = id;
        this.starting_bid = starting_bid;
        this.closing_datetime = closing_datetime;
        this.bids = new Bid_History(id);
        this.auction_id = auction_id;
    }

    public void set_bid_history(Bid_History old_bids){
        bids = old_bids;
    }

    public String getItem_title() {
        return item_title;
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
        System.out.println(item_title);
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

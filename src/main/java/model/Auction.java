package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Auction {
    private int id;
    private String name;
    private ArrayList<Lot> lots = new ArrayList<Lot>();
    private LocalDateTime closing_datetime;
    private String details;

    public Auction(int id, String name, LocalDateTime closing_datetime, String details) {
        this.id = id;
        this.name = name;
        this.closing_datetime = closing_datetime;
        this.details = details;
    }

    public int get_number_of_lots(){
        return lots.size();
    }

    public int get_id() {
        return id;
    }

    public void add_lot(Lot new_lot){
        if(new_lot.get_auction_id() == id)
            lots.add(new_lot);
        else
            System.out.println("error: lot auction id is wrong");
    }

    public void display_lot(int lot_number){
        lots.get(lot_number-1).display();
    }

    public void place_bid(int lot_number, int user_id, int value){
        LocalDateTime current_date = LocalDateTime.now();
        if(current_date.isAfter(closing_datetime)) {
            System.out.println("The Auction is closed, a bid cannot be placed");
        } else {
            lots.get(lot_number - 1).bid(value, user_id);
        }
    }

    public void display(){
        System.out.println();
        System.out.println("Auction " + id);
        System.out.println(name);
        System.out.println("Details: " + details);
        Duration diff = Duration.between(LocalDateTime.now(), closing_datetime);
        String hms = String.format("%d days, %dh:%02dm:%02ds", diff.toHours()/24, diff.toHours()%24, diff.toMinutesPart(), diff.toSecondsPart());
        if(LocalDateTime.now().compareTo(closing_datetime) <= 0)
            System.out.println("Closes in: " + hms);
        else
            System.out.println("Bidding is closed");
        System.out.println();
        System.out.println( get_number_of_lots() + " Lots:");
        for (int i = 0; i < lots.size() ; i++) {
            System.out.println((i+1) + ". " + lots.get(i).getItem_title());
        }
    }
}

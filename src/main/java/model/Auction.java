package model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Auction {
    @CsvBindByName(column = "ID")
    private int id;

    @CsvBindByName(column = "NAME")
    private String name;

    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    @CsvBindByName(column = "CLOSING_DATETIME")
    private LocalDateTime closingDatetime;

    @CsvBindByName(column = "DETAILS")
    private String details;

    public static String getHeader(){
        return "ID,NAME,CLOSING_DATETIME,DETAILS";
    }

    private ArrayList<Lot> lots = new ArrayList<Lot>();

    public Auction(int id, String name, LocalDateTime closingDatetime, String details) {
        this.id = id;
        this.name = name;
        this.closingDatetime = closingDatetime;
        this.details = details;
    }

    public Auction(){}

    public String toString(){
        return id + "," + name + "," + closingDatetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "," + details;
    }

    public int getNumberOfLots(){
        return lots.size();
    }

    public void addLot(Lot new_lot){
        if(new_lot.getAuctionId() == id)
            lots.add(new_lot);
        else
            System.out.println("error: lot auction id is wrong");
    }

    public void displayLot(int lot_number){
        lots.get(lot_number-1).display();
    }

    public void placeBid(int lot_number, int user_id, int value){
        LocalDateTime current_date = LocalDateTime.now();
        if(current_date.isAfter(closingDatetime)) {
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
        Duration diff = Duration.between(LocalDateTime.now(), closingDatetime);
        String hms = String.format("%d days, %dh:%02dm:%02ds", diff.toHours()/24, diff.toHours()%24, diff.toMinutesPart(), diff.toSecondsPart());
        if(LocalDateTime.now().compareTo(closingDatetime) <= 0)
            System.out.println("Closes in: " + hms);
        else
            System.out.println("Bidding is closed");
        System.out.println();
        System.out.println( getNumberOfLots() + " Lots:");
        for (int i = 0; i < lots.size() ; i++) {
            System.out.println((i+1) + ". " + lots.get(i).getLotName());
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getClosingDatetime() {
        return closingDatetime;
    }

    public void setClosingDatetime(LocalDateTime closingDatetime) {
        this.closingDatetime = closingDatetime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ArrayList<Lot> getLots() {
        return lots;
    }

    public void setLots(ArrayList<Lot> lots) {
        this.lots = lots;
    }
}

package model;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bid {

    @CsvBindByName(column = "VALUE")
    private double value;

    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    @CsvBindByName(column = "TIME")
    private LocalDateTime time;

    @CsvBindByName(column = "USER_ID")
    private int user_id;

    @CsvBindByName(column = "LOT_ID")
    private int lot_id;

    public static String getHeader(){
        return "VALUE,TIME,USER_ID,LOT_ID";
    }

    public Bid(int value, LocalDateTime time, int user_id, int lot_id) {
        this.value = value;
        this.time = time;
        this.user_id = user_id;
        this.lot_id = lot_id;
    }

    public Bid() {}

    public void display(){
        System.out.println("Bid for " + value + " on lot ID " + lot_id +" placed by user ID " + user_id + " at time: " + time);
    }

    public String toString(){
        return value + "," + time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "," + user_id + "," + lot_id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getLot_id() {
        return lot_id;
    }
}

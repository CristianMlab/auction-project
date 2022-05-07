package model;
import java.time.LocalDateTime;

public class Bid {
    private double value;
    private LocalDateTime time;
    private int user_id;
    private int lot_id;

    public Bid(int value, LocalDateTime time, int user_id, int lot_id) {
        this.value = value;
        this.time = time;
        this.user_id = user_id;
        this.lot_id = lot_id;
    }

    public void display(){
        System.out.println("Bid for " + value + " on lot ID " + lot_id +" placed by user ID " + user_id + " at time: " + time);
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

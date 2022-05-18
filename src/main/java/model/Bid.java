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
    private int userId;

    @CsvBindByName(column = "LOT_ID")
    private int lotId;

    public static String getHeader(){
        return "VALUE,TIME,USER_ID,LOT_ID";
    }

    public Bid(int value, LocalDateTime time, int userId, int lotId) {
        this.value = value;
        this.time = time;
        this.userId = userId;
        this.lotId = lotId;
    }

    public Bid() {}

    public void display(){
        System.out.println("Bid for " + value + " on lot ID " + lotId +" placed by user ID " + userId + " at time: " + time);
    }

    public String toString(){
        return value + "," + time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "," + userId + "," + lotId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLotId() {
        return lotId;
    }
}

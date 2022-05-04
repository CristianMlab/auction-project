package model;
import java.time.LocalDateTime;

public class Bid {
    private int value;
    private LocalDateTime time;
    private int user_id;

    public Bid(int value, LocalDateTime time, int user_id) {
        this.value = value;
        this.time = time;
        this.user_id = user_id;
    }

    public void display(){
        System.out.println(time + " : " + value + " placed by user " + user_id);
    }

    public int getValue() {
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
}

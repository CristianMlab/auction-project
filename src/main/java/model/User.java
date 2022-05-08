package model;
import com.opencsv.bean.CsvBindByName;

public class User {
    @CsvBindByName(column = "ID")
    private int id;

    @CsvBindByName(column = "NAME")
    private String name;

    @CsvBindByName(column = "EMAIL")
    private String email;

    public static String getHeader(){
        return "ID,NAME,EMAIL";
    }

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(){}

    public String toString(){
        return id + "," + name + "," + email;
    }
}

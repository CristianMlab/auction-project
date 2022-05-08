package model;
import com.opencsv.bean.CsvBindByName;

public class Default_item implements Item {
    @CsvBindByName(column = "LOT_ID")
    private int lot_id;

    @CsvBindByName(column = "DESCRIPTION")
    private String description;

    public static String getHeader(){
        return "LOT_ID,DESCRIPTION";
    }

    public Default_item(int lot_id, String description) {
        this.lot_id = lot_id;
        this.description = description;
    }

    public Default_item(){}

    public String toString(){
        return lot_id + "," + description;
    }

    public int get_lot_id(){
        return lot_id;
    }

    public void display(){
        System.out.println(description);
    }
}

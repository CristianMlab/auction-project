package model;
import com.opencsv.bean.CsvBindByName;

public class DefaultItem {
    @CsvBindByName(column = "LOT_ID")
    private int lotId;

    @CsvBindByName(column = "DESCRIPTION")
    private String description;

    public static String getHeader(){
        return "LOT_ID,DESCRIPTION";
    }

    public DefaultItem(int lotId, String description) {
        this.lotId = lotId;
        this.description = description;
    }

    public DefaultItem(){}

    public String toString(){
        return lotId + "," + description;
    }

    public int get_lot_id(){
        return lotId;
    }

    public void display(){
        System.out.println(description);
    }
}

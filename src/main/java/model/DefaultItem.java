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

    public int getLotId(){
        return lotId;
    }

    public void display(){
        System.out.println(description);
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

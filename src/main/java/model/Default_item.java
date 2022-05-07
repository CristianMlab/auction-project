package model;

public class Default_item implements Item {
    private String description;
    private int lot_id;

    public Default_item(int lot_id, String description) {
        this.lot_id = lot_id;
        this.description = description;
    }

    public int get_lot_id(){
        return lot_id;
    }

    public void display(){
        System.out.println(description);
    }
}

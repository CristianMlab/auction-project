package model;

public class Ring implements Item {
    private int lot_id;
    private int year;
    private String serial_number;
    private String central_stone;
    private double carats;
    private String gender;
    private String extra_information;

    public Ring(int lot_id, int year, String serial_number, String central_stone, double carats, String gender, String extra_information) {
        this.lot_id = lot_id;
        this.year = year;
        this.serial_number = serial_number;
        this.central_stone = central_stone;
        this.carats = carats;
        this.gender = gender;
        this.extra_information = extra_information;
    }

    public int get_lot_id(){
        return lot_id;
    }

    public void display() {
        System.out.println("Year: " + year);
        System.out.println("Serial Number: " + serial_number);
        System.out.println("Central Stone: " + central_stone + ", " + carats + " Carats");
        System.out.println("Gender: " + gender);
        System.out.println(extra_information);
    }
}

package testTask.ulytichev.mortgage.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String city;
    private String street;
    private String houseNumber;
    private int houseRoomNumber;
    private double size;

    public House(String city, String street, String houseNumber, int houseRoomNumber, double size) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.houseRoomNumber = houseRoomNumber;
        this.size = size;
    }

    public House() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getHouseRoomNumber() {
        return houseRoomNumber;
    }

    public void setHouseRoomNumber(int houseRoomNumber) {
        this.houseRoomNumber = houseRoomNumber;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}

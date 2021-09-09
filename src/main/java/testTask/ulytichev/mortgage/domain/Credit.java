package testTask.ulytichev.mortgage.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private long creditAmount;
    private long totalAmount;

    @Min(value = 0, message = "Ставка меньше 0")
    private double creditRate;

    @Min(value = 1, message = "Неправильно введен срок ипотеки")
    @Max(value = 50, message = "Слишком большой срое ипотеки")
    private int years;

    @OneToOne()
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    private Seller seller;

    private String objectOfCredit;

    public Credit(long creditAmount, long totalAmount, double creditRate, int years, Client client, Seller seller, String objectOfCredit) {
        this.creditAmount = creditAmount;
        this.totalAmount = totalAmount;
        this.creditRate = creditRate;
        this.years = years;
        this.client = client;
        this.seller = seller;
        this.objectOfCredit = objectOfCredit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(long creditAmount) {
        this.creditAmount = creditAmount;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getCreditRate() {
        return creditRate;
    }

    public void setCreditRate(double creditRate) {
        this.creditRate = creditRate;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getObjectOfCredit() {
        return objectOfCredit;
    }

    public void setObjectOfCredit(String objectOfCredit) {
        this.objectOfCredit = objectOfCredit;
    }
}

package testTask.ulytichev.mortgage.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private long creditAmount;
    private long totalAmount;

    @Min(value = 0, message = "ставка меньше 0")
    private double creditRate;

    @Min(value = 1, message = "Неправильно введен срок ипотеки")
    private int years;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    private String purposeOfCredit;
}

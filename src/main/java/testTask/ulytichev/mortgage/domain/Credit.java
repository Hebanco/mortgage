package testTask.ulytichev.mortgage.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "creditRate_id")
    private CreditRate creditRate;
    private long creditAmount;
    private long totalAmount;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

//    @ManyToOne
//    @JoinColumn (name = "seller_id")
//    private Seller seller;

    @OneToOne
    @JoinColumn(name = "house_id")
    private House house;
}

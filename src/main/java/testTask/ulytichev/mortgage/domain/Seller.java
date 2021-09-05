package testTask.ulytichev.mortgage.domain;

import javax.persistence.*;

@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    @JoinColumn(name = "salesman_id")
    private Salesman salesman;
    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Seller(Salesman salesman) {
        this.salesman = salesman;
    }

    public Seller(Company company) {
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}

package testTask.ulytichev.mortgage.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    String name;

    @Length (min = 10, max = 10, message = "Больше 10 символов у продавца")
    String personalData;

    public Seller(String name, String personalData) {
        this.name = name;
        this.personalData = personalData;
    }

    public Seller() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalData() {
        return personalData;
    }

    public void setPersonalData(String personalData) {
        this.personalData = personalData;
    }
}

package testTask.ulytichev.mortgage.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Salesman implements Seller{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String fio;

    @Length(max = 10, min = 10, message = "Неправильные паспортные данные")
    private String passportData;

    public Salesman(String fio, String passportData) {
        this.fio = fio;
        this.passportData = passportData;
    }

    public Salesman() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPassportData() {
        return passportData;
    }

    public void setPassportData(String passportData) {
        this.passportData = passportData;
    }
}

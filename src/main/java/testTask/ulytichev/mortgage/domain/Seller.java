package testTask.ulytichev.mortgage.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Отсутствует имя компании")
    private String name;

    @Length (min = 10, max = 10, message = "Больше 10 символов данных у продавца")
    private String personalData; // passport/inn

    @Enumerated(EnumType.STRING)
    private SellerType sellerType;

    public Seller(String name, String personalData, SellerType sellerType) {
        this.name = name;
        this.personalData = personalData;
        this.sellerType = sellerType;
    }

    public Seller(int id, String name, String personalData, SellerType sellerType) {
        this.id = id;
        this.name = name;
        this.personalData = personalData;
        this.sellerType = sellerType;
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

    public SellerType getSellerType() {
        return sellerType;
    }

    public void setSellerType(SellerType sellerType) {
        this.sellerType = sellerType;
    }
}

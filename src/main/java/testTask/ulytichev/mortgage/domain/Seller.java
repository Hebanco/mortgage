package testTask.ulytichev.mortgage.domain;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import testTask.ulytichev.mortgage.annotation.InnValid;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@InnValid
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotBlank(message = "Отсутствует имя компании")
    private String name;

    @Length (min = 10, max = 10, message = "Больше 10 символов данных у продавца")
    private String personalData;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("SALESMAN")
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

    public Seller() {}

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

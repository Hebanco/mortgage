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
    String name;

    @Length (min = 10, max = 10, message = "Больше 10 символов данных у продавца")
    String personalData; // passport/inn

    @Enumerated(EnumType.STRING)
    private SellerType sellerType;

    public Seller(String name, String personalData, SellerType sellerType) {
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

    public boolean innValidate() {
        long inn = Long.parseLong(personalData);
//        if (sellerType.equals(SellerType.SALESMAN))
//            return false;
        long resNumber = 0;
        int lastSymbol = (int) (inn % 10);
        int tempInn = (int) (inn / 10);
        resNumber += tempInn % 10 * 8; //9
        tempInn /= 10;
        resNumber += tempInn % 10 * 6; //8
        tempInn /= 10;
        resNumber += tempInn % 10 * 4; //7
        tempInn /= 10;
        resNumber += tempInn % 10 * 9; //6
        tempInn /= 10;
        resNumber += tempInn % 10 * 5; //5
        tempInn /= 10;
        resNumber += tempInn % 10 * 3; //4
        tempInn /= 10;
        resNumber += tempInn % 10 * 10; //3
        tempInn /= 10;
        resNumber += tempInn % 10 * 4; //2
        tempInn /= 10;
        resNumber += tempInn * 2; //1
        resNumber = resNumber % 11;
        if (resNumber % 10 == lastSymbol)
            return true;
        else
            return false;
    }
}

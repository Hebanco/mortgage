package testTask.ulytichev.mortgage.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Company{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String companyType;

    private String name;

    private long inn;

    public Company(String companyType, String name, long inn) {
        this.companyType = companyType;
        this.name = name;
        this.inn = inn;
    }

    public Company() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getInn() {
        return inn;
    }

    public void setInn(long inn) {
        this.inn = inn;
    }

    private boolean innValidate(){
        if (inn>9999999999L||inn<999999999)
            return false;
        long resNumber = 0;
        int lastSymbol = (int) (inn%10);
        int tempInn = (int) (inn/10);
        resNumber+=tempInn%10*8; //9
        tempInn/=10;
        resNumber+=tempInn%10*6; //8
        tempInn/=10;
        resNumber+=tempInn%10*4; //7
        tempInn/=10;
        resNumber+=tempInn%10*9; //6
        tempInn/=10;
        resNumber+=tempInn%10*5; //5
        tempInn/=10;
        resNumber+=tempInn%10*3; //4
        tempInn/=10;
        resNumber+=tempInn%10*10; //3
        tempInn/=10;
        resNumber+=tempInn%10*4; //2
        tempInn/=10;
        resNumber+=tempInn*2; //1
        resNumber = resNumber%11;
        if(resNumber%10 == lastSymbol)
            return true;
        else
            return false;

    }
}

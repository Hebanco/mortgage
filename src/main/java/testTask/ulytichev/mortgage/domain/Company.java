package testTask.ulytichev.mortgage.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Company implements Seller{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String type;


    private String name;

    private long inn;

    public Company(String type, String name, long inn) {
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

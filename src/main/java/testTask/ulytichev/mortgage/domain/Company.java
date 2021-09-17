package testTask.ulytichev.mortgage.domain;

import testTask.ulytichev.mortgage.utils.InnValid;

public class Company extends Seller{

    private int id;
    private String name;
    @InnValid
    private String inn;

    public Company() {
    }

    public Company(int id, String name, String inn) {
        this.id = id;
        this.name = name;
        this.inn = inn;
    }

    public static Company convertToCompany (Seller seller){
        return new Company(seller.getId(), seller.getName(), seller.getPersonalData());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setPassportData(String inn) {
        this.inn = inn;
    }

    private boolean innValidate() {
        long longInn = Long.parseLong(inn);
        long resNumber = 0;
        int lastSymbol = (int) (longInn % 10);
        int tempInn = (int) (longInn / 10);
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

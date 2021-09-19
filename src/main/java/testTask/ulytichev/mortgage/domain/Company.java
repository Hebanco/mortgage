package testTask.ulytichev.mortgage.domain;

import testTask.ulytichev.mortgage.annotation.InnValid;

@InnValid
public class Company extends Seller{

    private int id;
    private String name;
    private String inn;

    public Company() {}

    public Company(int id, String name, String inn) {
        this.id = id;
        this.name = name;
        this.inn = inn;
    }

    public static Company convertSellerToCompany (Seller seller){
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

}

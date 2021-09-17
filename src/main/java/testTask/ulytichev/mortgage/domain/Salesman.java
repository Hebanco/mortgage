package testTask.ulytichev.mortgage.domain;

public class Salesman extends Seller{

    private int id;
    private String name;
    private String passportData;

    public Salesman() {
    }

    public Salesman(int id, String name, String passportData) {
        this.id = id;
        this.name = name;
        this.passportData = passportData;
    }

    public static Salesman convertSellerToSalesman (Seller seller){
        return new Salesman(seller.getId(), seller.getName(), seller.getPersonalData());
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

    public String getPassportData() {
        return passportData;
    }

    public void setPassportData(String passportData) {
        this.passportData = passportData;
    }
}

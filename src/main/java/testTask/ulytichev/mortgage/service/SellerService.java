package testTask.ulytichev.mortgage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testTask.ulytichev.mortgage.domain.Credit;
import testTask.ulytichev.mortgage.domain.Seller;
import testTask.ulytichev.mortgage.repos.CreditRepo;
import testTask.ulytichev.mortgage.repos.SellerRepo;

@Service
public class SellerService {

    private SellerRepo sellerRepo;

    @Autowired
    public SellerService(SellerRepo sellerRepo) {
        this.sellerRepo = sellerRepo;
    }

    public void create(Seller seller) {
        sellerRepo.save(seller);
    }

    public Seller findById(int id) {
        return sellerRepo.getOne(id);
    }

    public boolean update(Seller seller) {
        sellerRepo.save(seller);
        return true;
    }

    public boolean delete(int id) {
        sellerRepo.delete(findById(id));
        return true;
    }

}

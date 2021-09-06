package testTask.ulytichev.mortgage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testTask.ulytichev.mortgage.domain.Credit;
import testTask.ulytichev.mortgage.repos.CreditRepo;

import java.util.List;

@Service
public class CreditService {

    private CreditRepo creditRepo;

    @Autowired
    public CreditService(CreditRepo creditRepo) {
        this.creditRepo = creditRepo;
    }

    public void create(Credit credit) {
        creditRepo.save(credit);
    }

//    public List<Credit> findAll() {
//        return creditRepo.findAll();
//    }

    public Credit findById(int id) {
        return creditRepo.getOne(id);
    }

    public boolean update(Credit credit) {
        creditRepo.save(credit);
        return true;
    }

    public boolean delete(int id) {
        creditRepo.delete(findById(id));
        return true;
    }
}

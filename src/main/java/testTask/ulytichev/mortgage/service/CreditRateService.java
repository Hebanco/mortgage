package testTask.ulytichev.mortgage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testTask.ulytichev.mortgage.domain.CreditRate;
import testTask.ulytichev.mortgage.repos.CreditRateRepo;

import java.util.List;

@Service
public class CreditRateService {

    private CreditRateRepo creditRateRepo;

    @Autowired
    public CreditRateService(CreditRateRepo creditRepo) {
        this.creditRateRepo = creditRepo;
    }

    public void create(CreditRate creditRate) {
        creditRateRepo.save(creditRate);
    }

    public List<CreditRate> findAll() {
        return creditRateRepo.findAll();
    }

    public CreditRate findById(int id) {
        return creditRateRepo.getOne(id);
    }

    public boolean update(CreditRate creditRate) {
        creditRateRepo.save(creditRate);
        return true;
    }

    public boolean delete(int id) {
        creditRateRepo.delete(findById(id));
        return true;
    }
}

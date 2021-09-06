package testTask.ulytichev.mortgage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testTask.ulytichev.mortgage.domain.House;
import testTask.ulytichev.mortgage.repos.HouseRepo;

import java.util.List;

@Service
public class HouseService {

    private HouseRepo houseRepo;

    @Autowired
    public HouseService(HouseRepo houseRepo) {
        this.houseRepo = houseRepo;
    }

    public void create(House house) {
        houseRepo.save(house);
    }

    public List<House> findAll() {
        return houseRepo.findAll();
    }

    public House findById(int id) {
        return houseRepo.getOne(id);
    }

    public boolean update(House house) {
        houseRepo.save(house);
        return true;
    }

    public boolean delete(int id) {
        houseRepo.delete(findById(id));
        return true;
    }
}

package testTask.ulytichev.mortgage.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import testTask.ulytichev.mortgage.domain.House;

public interface HouseRepo extends JpaRepository<House, Integer> {
}

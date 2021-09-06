package testTask.ulytichev.mortgage.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import testTask.ulytichev.mortgage.domain.Credit;

public interface CreditRepo extends JpaRepository<Credit, Integer> {
}

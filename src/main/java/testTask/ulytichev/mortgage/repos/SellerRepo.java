package testTask.ulytichev.mortgage.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import testTask.ulytichev.mortgage.domain.Seller;

public interface SellerRepo extends JpaRepository<Seller, Integer> {
}

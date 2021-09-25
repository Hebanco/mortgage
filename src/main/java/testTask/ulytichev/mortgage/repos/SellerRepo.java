package testTask.ulytichev.mortgage.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import testTask.ulytichev.mortgage.domain.Seller;
import testTask.ulytichev.mortgage.domain.SellerType;

public interface SellerRepo extends JpaRepository<Seller, Integer> {

    Seller findByPersonalDataAndSellerType(String personalData, SellerType sellerType);
}

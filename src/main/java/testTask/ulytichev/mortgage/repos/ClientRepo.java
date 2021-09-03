package testTask.ulytichev.mortgage.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import testTask.ulytichev.mortgage.domain.Client;

public interface ClientRepo extends JpaRepository<Client, Integer> {
}

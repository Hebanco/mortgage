package testTask.ulytichev.mortgage.service;

import org.springframework.stereotype.Service;
import testTask.ulytichev.mortgage.domain.Client;
import testTask.ulytichev.mortgage.repos.ClientRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepo clientRepo;

    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    public void create(Client client) {
        clientRepo.save(client);
    }

    public List<Client> findAll() {
        return clientRepo.findAll();
    }

    public Client findById(int id) {
        return null;/* clientRepo.getById(id);*/
    }

    public boolean update(Client client) {
        clientRepo.save(client);
        return true;
    }

    public boolean delete(int id) {
        clientRepo.delete(findById(id));
        return true;
    }
}

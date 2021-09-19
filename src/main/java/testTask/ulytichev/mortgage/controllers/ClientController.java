package testTask.ulytichev.mortgage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testTask.ulytichev.mortgage.domain.Client;
import testTask.ulytichev.mortgage.repos.ClientRepo;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ClientController {

    private final ClientRepo clientRepo;

    @Autowired
    public ClientController(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    @PostMapping(value = "/clients")
    public ResponseEntity<Client> create(@Valid @RequestBody Client client) {
        clientRepo.saveAndFlush(client);
        return client.getId()!=0
                ? new ResponseEntity<>(client, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/clients")
    public ResponseEntity<List<Client>> read() {
        final List<Client> clients = clientRepo.findAll();
        return clients != null &&  !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/clients/{id}")
    public ResponseEntity<Client> read(@PathVariable(name = "id") int id) {
         Optional<Client> client = clientRepo.findById(id);
        return client.isPresent()
                ? new ResponseEntity<>(client.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/clients/{id}")
    public ResponseEntity<Client> update(@PathVariable(name = "id") int id, @RequestBody Client updatedClient) {
        Optional<Client> dbClient = clientRepo.findById(id);
        if (dbClient.isPresent()) {
            Client newClient = dbClient.get();
            if (updatedClient.getName()!=null/*||!updatedClient.getName().isEmpty()*/)
                newClient.setName(updatedClient.getName());
            if (updatedClient.getPassportData()!=null/*||!updatedClient.getPassportData().isEmpty()*/)
                newClient.setPassportData(updatedClient.getPassportData());
            clientRepo.saveAndFlush(newClient);
            return new ResponseEntity<>(newClient,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/clients/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        Optional<Client> client = clientRepo.findById(id);
        if (client.isPresent()) {
            clientRepo.delete(client.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}

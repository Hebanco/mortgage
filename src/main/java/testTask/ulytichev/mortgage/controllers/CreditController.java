package testTask.ulytichev.mortgage.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testTask.ulytichev.mortgage.domain.Credit;
import testTask.ulytichev.mortgage.servives.CreditService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @PostMapping(value = "/credits")
    public ResponseEntity<Credit> create(@Valid @RequestBody Credit credit,
                                         @RequestParam(required = false, defaultValue = "-1", name = "clientId") int clientId,
                                         @RequestParam(required = false, defaultValue = "-1", name = "sellerId") int sellerId) {
        return creditService.create(credit, clientId, sellerId);
    }

    @GetMapping(value = "/credits")
    public ResponseEntity<List<Credit>> readAll() {
        return creditService.findAll();
    }

    @GetMapping(value = "/credits/{id}")
    public ResponseEntity<Credit> readById(@PathVariable(name = "id") int id) {
        return creditService.readById(id);
    }

    @PutMapping(value = "/credits/{id}")
    public ResponseEntity<Credit> update(@PathVariable(name = "id") int id,
                                         @RequestParam(required = false, defaultValue = "-1", name = "clientId") int clientId,
                                         @RequestParam(required = false, defaultValue = "-1", name = "sellerId") int sellerId,
                                         @RequestBody Credit updatedCredit) {

        return creditService.update(id, clientId, sellerId, updatedCredit);
    }

    @DeleteMapping(value = "/credits/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        return creditService.delete(id);
    }


}

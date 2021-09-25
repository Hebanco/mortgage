package testTask.ulytichev.mortgage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testTask.ulytichev.mortgage.domain.Client;
import testTask.ulytichev.mortgage.domain.Credit;
import testTask.ulytichev.mortgage.domain.Seller;
import testTask.ulytichev.mortgage.repos.ClientRepo;
import testTask.ulytichev.mortgage.repos.CreditRepo;
import testTask.ulytichev.mortgage.repos.SellerRepo;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CreditController {

    private final CreditRepo creditRepo;
    private final ClientRepo clientRepo;
    private final SellerRepo sellerRepo;

    @Autowired
    public CreditController(CreditRepo creditRepo, ClientRepo clientRepo, SellerRepo sellerRepo) {
        this.creditRepo = creditRepo;
        this.clientRepo = clientRepo;
        this.sellerRepo = sellerRepo;
    }

    @PostMapping(value = "/credits")
    public ResponseEntity<Credit> create(@Valid @RequestBody Credit credit,
                                         @RequestParam(required = false, defaultValue = "-1", name = "clientId") int clientId,
                                         @RequestParam(required = false, defaultValue = "-1", name = "sellerId") int sellerId) {
        if (!addClientForCredit(credit, clientId))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (!addSellerForCredit(credit, sellerId))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        creditRepo.saveAndFlush(credit);
        if (credit.getId() != 0)
            return new ResponseEntity<>(credit, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/credits")
    public ResponseEntity<List<Credit>> readAll() {
        List<Credit> credits = creditRepo.findAll();
        return credits != null &&  !credits.isEmpty()
                ? new ResponseEntity<>(credits, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/credits/{id}")
    public ResponseEntity<Credit> readById(@PathVariable(name = "id") int id) {
        Optional<Credit> credit = creditRepo.findById(id);
        return credit.isPresent()
                ? new ResponseEntity<>(credit.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/credits/{id}")
    public ResponseEntity<Credit> update(@PathVariable(name = "id") int id,
                                         @RequestParam(required = false, defaultValue = "-1", name = "clientId") int clientId,
                                         @RequestParam(required = false, defaultValue = "-1", name = "sellerId") int sellerId,
                                         @RequestBody Credit updatedCredit) {

        Optional<Credit> dbCredit = creditRepo.findById(id);
        if (dbCredit.isPresent()) {
            Credit newCredit = dbCredit.get();
            if (!addClientForCredit(newCredit, clientId))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            if (!addSellerForCredit(newCredit, sellerId))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            fillNewCreditByValues(updatedCredit, newCredit);
                creditRepo.saveAndFlush(newCredit);
                if (newCredit.getId() != 0){
                    return new ResponseEntity<>(newCredit, HttpStatus.OK);
                } else
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    private void fillNewCreditByValues(Credit updatedCredit, Credit newCredit) {
        if (updatedCredit.getCreditAmount() > 0)
            newCredit.setCreditAmount(updatedCredit.getCreditAmount());
        if (updatedCredit.getTotalAmount() > 0)
            newCredit.setTotalAmount(updatedCredit.getTotalAmount());
        if (updatedCredit.getCreditRate() > 0)
            newCredit.setCreditRate(updatedCredit.getCreditRate());
        if (updatedCredit.getYears() > 0)
            newCredit.setYears(updatedCredit.getYears());
        if (updatedCredit.getObjectOfCredit() != null)
            newCredit.setObjectOfCredit(updatedCredit.getObjectOfCredit());
    }

    @DeleteMapping(value = "/credits/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        Optional<Credit> credit = creditRepo.findById(id);
        if (credit.isPresent()) {
            creditRepo.delete(credit.get());
            return new  ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    private boolean addClientForCredit(Credit credit, int clientId) {
        if (clientId != -1) {
            Optional<Client> client = clientRepo.findById(clientId);
            if (client.isPresent()) {
                credit.setClient(client.get());
                return true;
            }
        }
        else if (credit.getClient() != null){
            clientRepo.saveAndFlush(credit.getClient());
            return true;
        }
        return false;
    }

    private boolean addSellerForCredit(Credit credit, int sellerId) {
        if (sellerId != -1) {
            Optional<Seller> seller = sellerRepo.findById(sellerId);
            if (seller.isPresent()){
                credit.setSeller(seller.get());
                return true;
            }
        }
        else if (credit.getSeller() != null) {
            sellerRepo.saveAndFlush(credit.getSeller());
            return true;
        }
        return false;
    }
}

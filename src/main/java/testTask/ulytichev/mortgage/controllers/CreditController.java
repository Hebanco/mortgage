package testTask.ulytichev.mortgage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testTask.ulytichev.mortgage.domain.Client;
import testTask.ulytichev.mortgage.domain.Credit;
import testTask.ulytichev.mortgage.domain.Seller;
import testTask.ulytichev.mortgage.domain.SellerType;
import testTask.ulytichev.mortgage.repos.ClientRepo;
import testTask.ulytichev.mortgage.repos.CreditRepo;
import testTask.ulytichev.mortgage.repos.SellerRepo;

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
    public ResponseEntity<Credit> create(@RequestBody Credit credit,
                                         @RequestParam(required = false, defaultValue = "-1", name = "clientId") int clientId,
                                         @RequestParam(required = false, defaultValue = "-1", name = "sellerId") int sellerId) {

        Client creditClient;
        if (clientId!=-1) {
            Optional<Client> client = clientRepo.findById(clientId);
            if (client.isPresent()) {
                credit.setClient(client.get());
                creditClient = client.get();
            }
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            clientRepo.saveAndFlush(credit.getClient());
            creditClient = credit.getClient();
        }

        if (sellerId!=-1) {
            Optional<Seller> seller = sellerRepo.findById(sellerId);
            if (seller.isPresent())
                credit.setSeller(seller.get());
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            if (credit.getSeller().getSellerType().equals(SellerType.COMPANY)&&credit.getSeller().innValidate()) {
                sellerRepo.save(credit.getSeller());
            }
            else if (credit.getSeller().getSellerType().equals(SellerType.SALESMAN)){
                sellerRepo.save(credit.getSeller());
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (creditValidation(credit)) {
            creditRepo.saveAndFlush(credit);
            creditClient.setCredit(credit);
            return new ResponseEntity<>(credit, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/credits")
    public ResponseEntity<List<Credit>> read() {
        final List<Credit> credits = creditRepo.findAll();

        return credits != null &&  !credits.isEmpty()
                ? new ResponseEntity<>(credits, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/credits/{id}")
    public ResponseEntity<Credit> read(@PathVariable(name = "id") int id) {
        final Optional<Credit> credit = creditRepo.findById(id);

        return credit.isPresent()
                ? new ResponseEntity<>(credit.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/credits/{id}")
    public ResponseEntity<Credit> update(@PathVariable(name = "id") int id, @RequestBody Credit updatedCredit) {

        Optional<Credit> dbCredit = creditRepo.findById(id);
        if (dbCredit.isPresent()) {
            Credit newCredit = dbCredit.get();
//            if (newCredit.getSellerType().equals(SellerType.COMPANY)&&!updatedCredit.getPersonalData().isEmpty())
//                if (!updatedCredit.innValidate())
//                    return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//            if (!updatedCredit.getName().isEmpty())
//                newCredit.setName(updatedCredit.getName());
//            if (!updatedCredit.getPersonalData().isEmpty())
//                newCredit.setPersonalData(updatedCredit.getPersonalData());
//            creditRepo.saveAndFlush(newCredit);
            return new ResponseEntity<>(newCredit,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
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

    public boolean creditValidation(Credit credit){
        if (credit.getCreditAmount()>credit.getTotalAmount())
            return false;
        return true;
    }
}

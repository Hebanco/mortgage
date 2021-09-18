package testTask.ulytichev.mortgage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testTask.ulytichev.mortgage.domain.Seller;
import testTask.ulytichev.mortgage.domain.SellerType;
import testTask.ulytichev.mortgage.repos.SellerRepo;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class SellerController {

    private final SellerRepo sellerRepo;

    @Autowired
    public SellerController(SellerRepo sellerRepo) {
        this.sellerRepo = sellerRepo;
    }

    @PostMapping(value = "/sellers")
    public ResponseEntity<Seller> create(@Valid @RequestBody Seller seller) {
            sellerRepo.saveAndFlush(seller);
            return seller.getId()!=0
                    ? new ResponseEntity<>(seller, HttpStatus.CREATED)
                    : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/sellers")
    public ResponseEntity<List<Seller>> read() {
        final List<Seller> sellers = sellerRepo.findAll();

        return sellers != null &&  !sellers.isEmpty()
                ? new ResponseEntity<>(sellers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sellers/{id}")
    public ResponseEntity<Seller> read(@PathVariable(name = "id") int id) {
        final Optional<Seller> seller = sellerRepo.findById(id);

        return seller.isPresent()
                ? new ResponseEntity<>(seller.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/sellers/{id}")
    public ResponseEntity<Seller> update(@PathVariable(name = "id") int id, @Valid @RequestBody Seller updatedSeller) {

        Optional<Seller> dbSeller = sellerRepo.findById(id);
        if (dbSeller.isPresent()) {
            Seller newSeller = dbSeller.get();
            if (updatedSeller.getName()!=null)
                newSeller.setName(updatedSeller.getName());
            if (updatedSeller.getPersonalData()!=null)
                newSeller.setPersonalData(updatedSeller.getPersonalData());
            sellerRepo.saveAndFlush(newSeller);
            return new ResponseEntity<>(newSeller,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/sellers/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        Optional<Seller> seller = sellerRepo.findById(id);
        if (seller.isPresent()) {
            sellerRepo.delete(seller.get());
            return new  ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}

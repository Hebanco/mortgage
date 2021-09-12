package testTask.ulytichev.mortgage.servives;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import testTask.ulytichev.mortgage.domain.Client;
import testTask.ulytichev.mortgage.domain.Credit;
import testTask.ulytichev.mortgage.domain.Seller;
import testTask.ulytichev.mortgage.domain.SellerType;
import testTask.ulytichev.mortgage.repos.ClientRepo;
import testTask.ulytichev.mortgage.repos.CreditRepo;
import testTask.ulytichev.mortgage.repos.SellerRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CreditService {
    private final CreditRepo creditRepo;
    private final ClientRepo clientRepo;
    private final SellerRepo sellerRepo;

    public CreditService(CreditRepo creditRepo, ClientRepo clientRepo, SellerRepo sellerRepo) {
        this.creditRepo = creditRepo;
        this.clientRepo = clientRepo;
        this.sellerRepo = sellerRepo;
    }

    public ResponseEntity<Credit> create(Credit credit, int clientId, int sellerId)
    {
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
        else if (credit.getClient()!=null){
            clientRepo.saveAndFlush(credit.getClient());
            creditClient = credit.getClient();
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (sellerId!=-1) {
            Optional<Seller> seller = sellerRepo.findById(sellerId);
            if (seller.isPresent())
                credit.setSeller(seller.get());
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else if (credit.getSeller()!=null){
            if (credit.getSeller().getSellerType().equals(SellerType.COMPANY)&&credit.getSeller().innValidate()) {
                sellerRepo.save(credit.getSeller());
            }
            else if (credit.getSeller().getSellerType().equals(SellerType.SALESMAN)){
                sellerRepo.save(credit.getSeller());
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (creditValidation(credit)) {
            creditRepo.saveAndFlush(credit);
//            creditClient.setCredit(credit);
            return new ResponseEntity<>(credit, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    public ResponseEntity<List<Credit>> findAll(){
        List<Credit> credits = creditRepo.findAll();

        return credits != null &&  !credits.isEmpty()
                ? new ResponseEntity<>(credits, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Credit> readById(int id) {
        Optional<Credit> credit = creditRepo.findById(id);

        return credit.isPresent()
                ? new ResponseEntity<>(credit.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Credit> update(int id, int clientId, int sellerId, Credit updatedCredit) {
        Optional<Credit> dbCredit = creditRepo.findById(id);
        if (dbCredit.isPresent()) {
            Credit newCredit = dbCredit.get();

            Client creditClient = null;
            if (clientId != -1) {
                Optional<Client> client = clientRepo.findById(clientId);
                if (client.isPresent()) {
                    updatedCredit.setClient(client.get());
                    creditClient = client.get();
                } else
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } /*else if (updatedCredit.getClient()!= null){
                clientRepo.saveAndFlush(updatedCredit.getClient());
                creditClient = updatedCredit.getClient();
            }*/

            if (sellerId != -1) {
                Optional<Seller> seller = sellerRepo.findById(sellerId);
                if (seller.isPresent())
                    updatedCredit.setSeller(seller.get());
                else
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } /*else if (updatedCredit.getClient()!= null){
                if (updatedCredit.getSeller().getSellerType().equals(SellerType.COMPANY) && updatedCredit.getSeller().innValidate()) {
                    sellerRepo.save(updatedCredit.getSeller());
                } else if (updatedCredit.getSeller().getSellerType().equals(SellerType.SALESMAN)) {
                    sellerRepo.save(updatedCredit.getSeller());
                } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }*/

            if (updatedCredit.getCreditAmount()>0)
                newCredit.setCreditAmount(updatedCredit.getCreditAmount());
            if (updatedCredit.getTotalAmount()>0)
                newCredit.setTotalAmount(updatedCredit.getTotalAmount());
            if (updatedCredit.getCreditRate()>0)
                newCredit.setCreditRate(updatedCredit.getCreditRate());
            if (updatedCredit.getYears()>0)
                newCredit.setYears(updatedCredit.getYears());
            if (updatedCredit.getObjectOfCredit()!=null)
                newCredit.setObjectOfCredit(updatedCredit.getObjectOfCredit());

            if (creditValidation(newCredit)) {
                creditRepo.saveAndFlush(newCredit);
                if (creditClient!=null) {
                    //creditClient.setCredit(updatedCredit);
                    clientRepo.saveAndFlush(creditClient);
                }
                return new ResponseEntity<>(newCredit, HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    public ResponseEntity<?> delete(int id) {
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

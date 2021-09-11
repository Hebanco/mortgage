package testTask.ulytichev.mortgage;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import testTask.ulytichev.mortgage.domain.Seller;
import testTask.ulytichev.mortgage.domain.SellerType;
import testTask.ulytichev.mortgage.repos.SellerRepo;

import static org.mockito.Mockito.mock;

@SpringBootTest
public class SellerTest {
    private SellerRepo sellerRepo = mock(SellerRepo.class);

    @Test
    public void addSellerInnValidateTest(){
        Seller seller = new Seller("ooo", "7704407589", SellerType.COMPANY);
        Assertions.assertTrue(seller.innValidate());
    }

    @Test
    public void addSellerInnValidateFailedTest(){
        Seller seller = new Seller("ooo", "7704407588", SellerType.COMPANY);
        Assertions.assertFalse(seller.innValidate());
    }

    @Test
    public void addSellerTest(){
        Seller seller = new Seller("ooo", "7704407589", SellerType.COMPANY);
        Assertions.assertTrue(seller.innValidate());
        sellerRepo.save(seller);
        Mockito.verify(sellerRepo, Mockito.times(1)).save(seller);
    }

    @Test
    public void addSellerFailedTest(){
        Seller seller = new Seller();
        seller.setName("asd");
        seller.setSellerType(SellerType.SALESMAN);
        Seller savedSeller = sellerRepo.save(seller);
        Mockito.verify(sellerRepo, Mockito.times(1)).save(seller);
        Assertions.assertTrue(savedSeller==null);
    }
}

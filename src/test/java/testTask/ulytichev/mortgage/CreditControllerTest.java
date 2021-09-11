package testTask.ulytichev.mortgage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import testTask.ulytichev.mortgage.controllers.CreditController;
import testTask.ulytichev.mortgage.domain.Credit;
import testTask.ulytichev.mortgage.domain.Seller;
import testTask.ulytichev.mortgage.domain.SellerType;
import testTask.ulytichev.mortgage.repos.CreditRepo;
import testTask.ulytichev.mortgage.repos.SellerRepo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/create-client-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CreditControllerTest {

    @Autowired
    private CreditController creditController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    SellerRepo sellerRepo;
    @Autowired
    CreditRepo creditRepo;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void resetDb() {
        creditRepo.deleteAll();
    }

    @Test
    public void addCreditTest() throws Exception {
        Credit credit = new Credit(1000000, 1500000,
                5.6, 30, "Квартира");
        Seller seller = createSeller("seller", "7704407589", SellerType.COMPANY);
        sellerRepo.saveAndFlush(seller);

        mockMvc.perform(post("/credits")
                .content(objectMapper.writeValueAsString(credit))
                .param("clientId", "1")
                .param("sellerId", String.valueOf(seller.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.client.id").value("1"));
    }

    @Test
    public void addCreditWithoutUserFailTest() throws Exception {
        Credit credit = new Credit(1000000, 1500000,
                5.6, 30, "Квартира");
        Seller seller = createSeller("seller", "7704407589", SellerType.COMPANY);
        sellerRepo.saveAndFlush(seller);

        mockMvc.perform(post("/credits")
                .content(objectMapper.writeValueAsString(credit))
//                .param("clientId", "1")
                .param("sellerId", String.valueOf(seller.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private Seller createSeller (String name, String personalData, SellerType sellerType) {
        Seller seller = new Seller(name, personalData, sellerType);
        return seller;
    }
}

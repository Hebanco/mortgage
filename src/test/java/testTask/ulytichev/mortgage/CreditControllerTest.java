package testTask.ulytichev.mortgage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import testTask.ulytichev.mortgage.controllers.CreditController;
import testTask.ulytichev.mortgage.domain.Client;
import testTask.ulytichev.mortgage.domain.Credit;
import testTask.ulytichev.mortgage.domain.Seller;
import testTask.ulytichev.mortgage.domain.SellerType;
import testTask.ulytichev.mortgage.repos.ClientRepo;
import testTask.ulytichev.mortgage.repos.CreditRepo;
import testTask.ulytichev.mortgage.repos.SellerRepo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/create-client-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreditControllerTest {

    @Autowired
    private CreditController creditController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SellerRepo sellerRepo;
    @Autowired
    private CreditRepo creditRepo;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void resetDb() {
        creditRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void addCreditTest() throws Exception {
        Credit credit = new Credit(1000000, 1500000,
                5.6, 30, "Квартира");
        Seller seller = createSeller("seller", "7704407589", SellerType.COMPANY);

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
    @Order(5)
    public void addCreditWithoutUserFailTest() throws Exception {
        Credit credit = new Credit(1000000, 1500000,
                5.6, 30, "Квартира");

        mockMvc.perform(post("/credits")
                .content(objectMapper.writeValueAsString(credit))
                .param("clientId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    public void addCreditWithoutSellersFailTest() throws Exception {
        Credit credit = new Credit(1000000, 1500000,
                5.6, 30, "Квартира");
        Seller seller = createSeller("seller", "7704407589", SellerType.COMPANY);

        mockMvc.perform(post("/credits")
                .content(objectMapper.writeValueAsString(credit))
                .param("sellerId", String.valueOf(seller.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(7)
    public void addCreditValidFailTest() throws Exception {
        Credit credit = new Credit(1500000, 1400000,
                5.6, 30, "Квартира");
        Seller seller = createSeller("seller", "7704407589", SellerType.COMPANY);

        mockMvc.perform(post("/credits")
                .content(objectMapper.writeValueAsString(credit))
                .param("sellerId", String.valueOf(seller.getId()))
                .param("clientId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(2)
    public void getCreditTest() throws Exception {
        Seller seller = createSeller("seller", "7704407589", SellerType.COMPANY);
        Credit credit = new Credit(1000000, 1500000,
                5.6, 30, "Квартира", clientRepo.getOne(1), seller);
        creditRepo.saveAndFlush(credit);
        this.mockMvc.perform(get("/credits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    @Order(8)
    public void getCreditConsistTest() throws Exception {
        Seller seller = createSeller("seller", "7704407589", SellerType.COMPANY);
        Credit credit = new Credit(1000000, 1500000,
                5.6, 30, "Квартира" ,clientRepo.getOne(1), seller);
        creditRepo.saveAndFlush(credit);

        mockMvc.perform(get("/credits/"+credit.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.client.id").value(1))
                .andExpect(jsonPath("$.seller.id").value(seller.getId()))
                .andExpect(jsonPath("$.creditAmount").value(1000000))
                .andExpect(jsonPath("$.totalAmount").value(1500000))
                .andExpect(jsonPath("$.creditRate").value(5.6))
                .andExpect(jsonPath("$.years").value(30));
    }

    @Test
    @Order(3)
    public void updateCreditTest() throws Exception{
        Seller seller = createSeller("seller", "7704407589", SellerType.COMPANY);
        Credit credit = new Credit(1000000, 1500000,
                5.6, 30, "Квартира" ,clientRepo.getOne(1), seller);
        creditRepo.saveAndFlush(credit);
        Credit updatedCredit = new Credit();
        updatedCredit.setCreditAmount(1500000);
        updatedCredit.setTotalAmount(1900000);
        this.mockMvc.perform(put("/credits/"+credit.getId())
                .content(objectMapper.writeValueAsString(updatedCredit))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(credit.getId()))
                .andExpect(jsonPath("$.creditAmount").value(1500000))
                .andExpect(jsonPath("$.totalAmount").value(1900000));
    }

    @Test
    @Order(4)
    public void deleteCreditTest() throws Exception{
        Seller seller = createSeller("seller", "7704407589", SellerType.COMPANY);
        Credit credit = new Credit(1000000, 1500000,
                5.6, 30, "Квартира" ,clientRepo.getOne(1), seller);
        creditRepo.saveAndFlush(credit);
        this.mockMvc.perform(delete("/credits/"+credit.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    private Seller createSeller (String name, String personalData, SellerType sellerType) {
        Seller seller = new Seller(name, personalData, sellerType);
        sellerRepo.saveAndFlush(seller);
        return seller;
    }
}

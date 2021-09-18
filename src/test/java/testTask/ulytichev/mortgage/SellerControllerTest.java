package testTask.ulytichev.mortgage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import testTask.ulytichev.mortgage.controllers.SellerController;
import testTask.ulytichev.mortgage.domain.Seller;
import testTask.ulytichev.mortgage.domain.SellerType;
import testTask.ulytichev.mortgage.repos.SellerRepo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SellerControllerTest {
    @Autowired
    private SellerController sellerController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SellerRepo sellerRepo;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void resetDb() {
        sellerRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void addSellerTest() throws Exception {
        sellerRepo.deleteAll();
        Seller seller = new Seller("asdasf", "7704407589", SellerType.COMPANY);
        mockMvc.perform(post("/sellers")
                .content(objectMapper.writeValueAsString(seller))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    @Order(5)
    public void addSellerFailTest() throws Exception {
        Seller seller = new Seller("asdasf", "12345678", SellerType.COMPANY);
        mockMvc.perform(post("/sellers")
                .content(objectMapper.writeValueAsString(seller))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(2)
    public void getSellerTest() throws Exception {
        Seller seller = new Seller("asdasf", "7704407589", SellerType.COMPANY);
        sellerRepo.saveAndFlush(seller);
        this.mockMvc.perform(get("/sellers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    @Order(3)
    public void updateSellerTest() throws Exception{
        Seller dbSeller = new Seller("asdasf", "7704407589", SellerType.COMPANY);
        sellerRepo.saveAndFlush(dbSeller);
        Seller updatedSeller = new Seller();
        updatedSeller.setName("ryew");
        this.mockMvc.perform(put("/sellers/"+dbSeller.getId())
                .content(objectMapper.writeValueAsString(updatedSeller))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dbSeller.getId()))
                .andExpect(jsonPath("$.name").value("ryew"));
    }

    @Test
    @Order(4)
    public void deleteSellerTest() throws Exception{
        Seller seller = new Seller("asdasf", "1234567890", SellerType.SALESMAN);
        sellerRepo.saveAndFlush(seller);
        this.mockMvc.perform(delete("/sellers/"+seller.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}

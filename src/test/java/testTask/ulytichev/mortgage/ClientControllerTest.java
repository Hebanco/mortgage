package testTask.ulytichev.mortgage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import testTask.ulytichev.mortgage.controllers.CreditController;
import testTask.ulytichev.mortgage.domain.Client;
import testTask.ulytichev.mortgage.repos.ClientRepo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/create-client-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientControllerTest {

    @Autowired
    private CreditController creditController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(4)
    public void addClientTest() throws Exception {
        clientRepo.deleteAll();
        Client client = new Client("asdasf", "1234567890");
        mockMvc.perform(post("/clients")
                .content(objectMapper.writeValueAsString(client))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(5)
    public void addClientFailPassportValidTest() throws Exception {
        Client client = new Client("asdasf", "12345678");
        mockMvc.perform(post("/clients")
                .content(objectMapper.writeValueAsString(client))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(1)
    public void getClientTest() throws Exception {
        this.mockMvc.perform(get("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    @Order(2)
    public void updateClientTest() throws Exception{
        Client updatedClient = new Client();
        updatedClient.setPassportData("0987654321");
        this.mockMvc.perform(put("/clients/1")
                .content(objectMapper.writeValueAsString(updatedClient))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.passportData").value("0987654321"));
    }

    @Test
    @Order(3)
    public void deleteClientTest() throws Exception{
        this.mockMvc.perform(delete("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}

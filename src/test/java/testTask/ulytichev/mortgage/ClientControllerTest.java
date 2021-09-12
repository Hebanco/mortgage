package testTask.ulytichev.mortgage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
    public void addClientTest() throws Exception {
        clientRepo.deleteAll();
        Client client = new Client("asdasf", "1234567890");
        client.setId(2);
        mockMvc.perform(post("/clients")
                .content(objectMapper.writeValueAsString(client))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    public void addClientFailTest() throws Exception {
        Client client = new Client("asdasf", "12345678");
        mockMvc.perform(post("/clients")
                .content(objectMapper.writeValueAsString(client))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getClientTest() throws Exception {
//        Client client = new Client("asdasf", "1234567890");
//        clientRepo.saveAndFlush(client);
        this.mockMvc.perform(get("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void updateClientTest() throws Exception{
//        Client dbClient = new Client("asdasf", "1234567890");
//        clientRepo.saveAndFlush(dbClient);
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
    public void deleteClientTest() throws Exception{
//        Client Client = new Client("asdasf", "1234567890");
//        clientRepo.saveAndFlush(Client);
        this.mockMvc.perform(delete("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}

package com.Ironhack.FinalProject.usercontrollers;

import com.Ironhack.FinalProject.DTOs.*;
import com.Ironhack.FinalProject.accountmodels.CheckingAccount;
import com.Ironhack.FinalProject.accountmodels.CreditCard;
import com.Ironhack.FinalProject.accountmodels.Savings;
import com.Ironhack.FinalProject.accountmodels.StudentCheckingAccount;
import com.Ironhack.FinalProject.embeddables.Address;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.enums.AccountStatus;
import com.Ironhack.FinalProject.repositories.*;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import com.Ironhack.FinalProject.usermodels.Admin;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class AdminControllerTests {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    SavingsRepository savingsRepository;
    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;
    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());



    @BeforeEach
    public void setUp() {
        adminRepository.deleteAll();
        accountHolderRepository.deleteAll();
        savingsRepository.deleteAll();
        checkingAccountRepository.deleteAll();
        studentCheckingAccountRepository.deleteAll();
        creditCardRepository.deleteAll();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Address address1 = new Address("Passeig de la Mare de Deu del Coll 200, 2nda 3ra", "Barcelona", "08032", "Barcelona", "Spain");
        Address address2 = new Address("Carrer de Cristiano Ronaldo", "Sivenga", "08032", "Fumada", "Jardin");
        AccountHolder accountHolder1 = new AccountHolder("Sergi", "sergi@gmail.com", 644745537l, "1234", "serkelet", LocalDate.of(1985,12,5), address1);
        AccountHolder accountHolder2 = new AccountHolder("Oscar", "oscar@gmail.com", 777555111l, "manzana", "sihombre", LocalDate.of(2000,12,5), address2);
        Savings savings1 = new Savings(new Money(BigDecimal.valueOf(1500)), accountHolder1, 1234);
        CheckingAccount checkingAccount1 = new CheckingAccount(new Money(BigDecimal.valueOf(3000)), accountHolder1, 1234);
        StudentCheckingAccount studentCheckingAccount1 = new StudentCheckingAccount(new Money(BigDecimal.valueOf(3000)), accountHolder2, 1234);
        CreditCard creditCard1 = new CreditCard(new Money(BigDecimal.valueOf(3000)), accountHolder2, 1234);
        Admin admin1 = new Admin("Bengisu", "bengi", "99eni");
        adminRepository.save(admin1);
        accountHolderRepository.save(accountHolder1);
        accountHolderRepository.save(accountHolder2);
        savingsRepository.save(savings1);
        checkingAccountRepository.save(checkingAccount1);
        studentCheckingAccountRepository.save(studentCheckingAccount1);
        creditCardRepository.save(creditCard1);
    }

    @AfterEach
    public void tearUp(){

    }

    @Test
    @DisplayName("Testing whether money is added")
    void patch_addBalance_isOk() throws Exception{
        ModifyBalanceDTO modifyBalanceDTO = new ModifyBalanceDTO(1l, BigDecimal.valueOf(2000));
        String body = objectMapper.writeValueAsString(modifyBalanceDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/add_balance").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("3500"));
    }
    @Test
    @DisplayName("Testing whether money is substracted")
    void patch_decreaseBalance_isOk() throws Exception{
        ModifyBalanceDTO modifyBalanceDTO = new ModifyBalanceDTO(1l, BigDecimal.valueOf(500));
        String body = objectMapper.writeValueAsString(modifyBalanceDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/add_balance").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1000"));
    }
    @Test
    @DisplayName("Testing change of status")
    void patch_changeStatus_isOk() throws Exception{
        AccountDTO accountDTO = new AccountDTO(3l, AccountStatus.FROZEN);
        String body = objectMapper.writeValueAsString(accountDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/change_status").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("FROZEN"));
    }
    @Test
    @DisplayName("Testing creating user and account")
    void post_CreateUserAndAccount_isOk() throws Exception{
        Address newAddress = new Address("Carrer Concili de Trento", "Barcelona", "08018", "Barcelona", "Spain");
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO("Raquel", "raquel@gmail.com", 555666777l, LocalDate.of(1990, 9, 11), newAddress, "checkingAccount", BigDecimal.valueOf(5000));
        String body = objectMapper.writeValueAsString(accountHolderDTO);
        MvcResult mvcResult = mockMvc.perform(post("/create_user_account").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("5000"));
    }
    @Test
    @DisplayName("Testing create account")
    void post_CreateNewAccount_isOk() throws Exception{
        CreateAccountDTO createAccountDTO = new CreateAccountDTO(1l, "creditCard",BigDecimal.valueOf(6000));
        String body = objectMapper.writeValueAsString(createAccountDTO);
        MvcResult mvcResult = mockMvc.perform(post("/create_account").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("6000"));
    }
    @Test
    @DisplayName("Testing delete account")
    void delete_DeleteAccount_isOk() throws Exception{
        MvcResult mvcResult = mockMvc.perform(delete("/delete_account/3")).andExpect(status().isAccepted()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().isEmpty());
    }
    @Test
    @DisplayName("Testing get all users")
    void get_AllUsers_isOk() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/show_users")).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sergi"));
    }
    @Test
    @DisplayName("Testing get all users")
    void get_AllAccounts_isOk() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/show_accounts")).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1500"));
    }
    @Test
    @DisplayName("Testing change of status")
    void patch_AssignSecondaryOwner_isOk() throws Exception {
        SecondaryOwnerDTO secondaryOwnerDTO = new SecondaryOwnerDTO(4l, 1l);
        String body = objectMapper.writeValueAsString(secondaryOwnerDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/assign_secondary_owner").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sergi"));
    }
   /* @Test
    @DisplayName("Testing ")
    void post_CreateThirdParty_isOk{

    }
    */
    @Test
    @DisplayName("Testing create Admin")
    void post_CreateAdmin_isOk() throws Exception {
        Admin admin = new Admin("sergi", "serk", "1234");
        String body = objectMapper.writeValueAsString(admin);
        MvcResult mvcResult = mockMvc.perform(post("/create_admin").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("serk"));
    }

}

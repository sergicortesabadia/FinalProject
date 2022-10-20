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
import org.springframework.boot.CommandLineRunner;
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
public class AccountHolderControllerTests {
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

    Address address1;
    Address address2;
    AccountHolder accountHolder1;
    AccountHolder accountHolder2;
    Savings savings1;
    CheckingAccount checkingAccount1;
    StudentCheckingAccount studentCheckingAccount1;
    CreditCard creditCard1;
    Admin admin1;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        address1 = new Address("Passeig de la Mare de Deu del Coll 200, 2nda 3ra", "Barcelona", "08032", "Barcelona", "Spain");
         address2 = new Address("Carrer de Cristiano Ronaldo", "Sivenga", "08032", "Fumada", "Jardin");
        accountHolder1 = new AccountHolder("Sergi", "sergi@gmail.com", 644745537l, "1234", "serkelet", LocalDate.of(1985, 12, 5), address1);
        accountHolder2 = new AccountHolder("Oscar", "oscar@gmail.com", 777555111l, "manzana", "sihombre", LocalDate.of(2000, 12, 5), address2);
        savings1 = new Savings(new Money(BigDecimal.valueOf(1500)), accountHolder1, 1234);
        checkingAccount1 = new CheckingAccount(new Money(BigDecimal.valueOf(3000)), accountHolder1, 1234);
        studentCheckingAccount1 = new StudentCheckingAccount(new Money(BigDecimal.valueOf(3000)), accountHolder2, 1234);
        creditCard1 = new CreditCard(new Money(BigDecimal.valueOf(3000)), accountHolder2, 1234);
        admin1 = new Admin("Bengisu", "bengi", "99eni");
        adminRepository.save(admin1);
        accountHolderRepository.save(accountHolder1);
        accountHolderRepository.save(accountHolder2);
        savingsRepository.save(savings1);
        checkingAccountRepository.save(checkingAccount1);
        studentCheckingAccountRepository.save(studentCheckingAccount1);
        creditCardRepository.save(creditCard1);
    }

    @AfterEach
    public void tearUp() {
    /*    adminRepository.deleteAll();
        accountHolderRepository.deleteAll();
        savingsRepository.deleteAll();
        checkingAccountRepository.deleteAll();
        studentCheckingAccountRepository.deleteAll();
        creditCardRepository.deleteAll();*/
    }
    @Test
    @DisplayName("Testing transfer money")
    void patch_TransferMoneyByAccountType_isOk() throws Exception{
        TransferDTO transferDTO= new TransferDTO(accountHolder1.getId(), savings1.getAccountNumber(), BigDecimal.valueOf(500), studentCheckingAccount1.getAccountNumber(), accountHolder2.getId());
        String body = objectMapper.writeValueAsString(transferDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/transfer").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1000"));
    }
    @Test
    @DisplayName("Testing showing an account")
    void get_GetBalance_isOk() throws Exception{
    SecondaryOwnerDTO secondaryOwnerDTO = new SecondaryOwnerDTO(savings1.getAccountNumber(), accountHolder1.getId());
    String body = objectMapper.writeValueAsString(secondaryOwnerDTO);
    MvcResult mvcResult = mockMvc.perform(get("/show_account/"+accountHolder1.getId()).content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
    assertTrue(mvcResult.getResponse().getContentAsString().contains("1500"));
}
    @Test
    @DisplayName("Testing showing all accounts by an account holder")
    void get_ShowAllAccountsByAccountHolder() throws Exception{
        String body = objectMapper.writeValueAsString(accountHolder1.getId());
        MvcResult mvcResult = mockMvc.perform(get("/user/show_accounts").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1500"));
    }
    @Test
    @DisplayName("/use/create_user")
    void post_CreateUserAccount_isOk() throws Exception{
        Address address = new Address("Carrer Concili de Trento", "Barcelona", "08018", "Barcelona", "Spain");
        AccountHolder accountHolder = new AccountHolder("Karl", "karl@gmail.com", 555666777l, "1234", "franz", LocalDate.of(1965, 11, 15), address);
        String body = objectMapper.writeValueAsString(accountHolder);
        MvcResult mvcResult = mockMvc.perform(post("/create_user").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Karl"));
    }
}
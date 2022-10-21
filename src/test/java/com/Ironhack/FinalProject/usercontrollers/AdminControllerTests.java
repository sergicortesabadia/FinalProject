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
import com.Ironhack.FinalProject.roles.Role;
import com.Ironhack.FinalProject.roles.RolesEnum;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import com.Ironhack.FinalProject.usermodels.Admin;
import com.Ironhack.FinalProject.usermodels.ThirdParty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
        accountHolder1 = new AccountHolder("Sergi", "1234", "sergi@gmail.com", 644745537l, "Sergi Cortes", LocalDate.of(1985, 12, 5), address1);
        accountHolder2 = new AccountHolder("Oscar", "4321", "oscar@gmail.com", 444555333l, "Oscar Curto", LocalDate.of(2000, 12, 5), address2);
        accountHolder1.addRole(new Role(RolesEnum.ACCOUNT_HOLDER, accountHolder1));
        accountHolder2.addRole(new Role(RolesEnum.ACCOUNT_HOLDER, accountHolder2));
        savings1 = new Savings(new Money(BigDecimal.valueOf(1500)), accountHolder1, 1234);
        checkingAccount1 = new CheckingAccount(new Money(BigDecimal.valueOf(3000)), accountHolder1, 1234);
        studentCheckingAccount1 = new StudentCheckingAccount(new Money(BigDecimal.valueOf(3000)), accountHolder2, 1234);
        creditCard1 = new CreditCard(new Money(BigDecimal.valueOf(3000)), accountHolder2, 1234);
        admin1 = new Admin("Bengisu", "bengi", "99eni");
        adminRepository.save(admin1);
        admin1.addRole(new Role(RolesEnum.ADMIN, admin1));
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
        ModifyBalanceDTO modifyBalanceDTO = new ModifyBalanceDTO(savings1.getAccountNumber(), BigDecimal.valueOf(2000));
        String body = objectMapper.writeValueAsString(modifyBalanceDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/add_balance").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("3500"));
    }
    @Test
    @DisplayName("Testing whether money is substracted")
    void patch_decreaseBalance_isOk() throws Exception{
        ModifyBalanceDTO modifyBalanceDTO = new ModifyBalanceDTO(savings1.getAccountNumber(), BigDecimal.valueOf(500));
        String body = objectMapper.writeValueAsString(modifyBalanceDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/add_balance").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1000"));
    }
    @Test
    @DisplayName("Testing change of status")
    void patch_changeStatus_isOk() throws Exception{
        AccountDTO accountDTO = new AccountDTO(studentCheckingAccount1.getAccountNumber(), AccountStatus.FROZEN);
        String body = objectMapper.writeValueAsString(accountDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/change_status").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("FROZEN"));
    }
    @Test
    @DisplayName("Testing creating user and account")
    void post_CreateUserAndAccount_isOk() throws Exception{
        AccountHolderCreationDTO accountHolderCreationDTO = new AccountHolderCreationDTO("Carrer Concili de Trento", "Barcelona", "08018", "Barcelona", "Spain", "rakmon","Raquel Cortes", "raquel@gmail.com", 555666777l,  LocalDate.of(1990, 9, 11),  "checkingAccount", BigDecimal.valueOf(5000));
        String body = objectMapper.writeValueAsString(accountHolderCreationDTO);
        MvcResult mvcResult = mockMvc.perform(post("/create_user_account").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("5000"));
    }
    @Test
    @DisplayName("Testing create account")
    void post_CreateNewAccount_isOk() throws Exception{
        CreateAccountDTO createAccountDTO = new CreateAccountDTO(accountHolder1.getId(), "creditCard",BigDecimal.valueOf(6000));
        String body = objectMapper.writeValueAsString(createAccountDTO);
        MvcResult mvcResult = mockMvc.perform(post("/create_account").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("6000"));
    }
    @Test
    @DisplayName("Testing delete account")
    void delete_DeleteAccount_isOk() throws Exception{
        MvcResult mvcResult = mockMvc.perform(delete("/delete_account/"+studentCheckingAccount1.getAccountNumber())).andExpect(status().isAccepted()).andReturn();
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
        SecondaryOwnerDTO secondaryOwnerDTO = new SecondaryOwnerDTO(creditCard1.getAccountNumber(), accountHolder1.getId());
        String body = objectMapper.writeValueAsString(secondaryOwnerDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/assign_secondary_owner").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sergi"));
    }

    @Test
    @WithMockUser("bengi")
    @DisplayName("Testing create Admin")
    void post_CreateAdmin_isOk() throws Exception {
        Admin admin = new Admin("sergi", "serk", "1234");
        String body = objectMapper.writeValueAsString(admin);
        MvcResult mvcResult = mockMvc.perform(post("/create_admin").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("serk"));
    }

    @Test
    @DisplayName("testing create address")
    void post_CreateAddress_isOk() throws Exception{
        AddressDTO addressDTO = new AddressDTO(accountHolder1.getId(), "Carrer Rosselló", "Barcelona", "08098", "Barcelona", "Spain");
        String body = objectMapper.writeValueAsString(addressDTO);
        MvcResult mvcResult = mockMvc.perform(post("/create_address").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Rossell"));
    }

    @Test
    @DisplayName("testing create address")
    void post_CreateMailingAddress_isOk() throws Exception{
        AddressDTO addressDTO = new AddressDTO(accountHolder1.getId(), "Carrer Rosselló", "Barcelona", "08098", "Barcelona", "Spain");
        String body = objectMapper.writeValueAsString(addressDTO);
        MvcResult mvcResult = mockMvc.perform(post("/create_mailing_address").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Rossell"));
    }
}

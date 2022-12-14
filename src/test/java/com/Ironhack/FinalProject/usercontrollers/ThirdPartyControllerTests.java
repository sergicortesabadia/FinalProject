package com.Ironhack.FinalProject.usercontrollers;

import com.Ironhack.FinalProject.DTOs.*;
import com.Ironhack.FinalProject.accountmodels.CheckingAccount;
import com.Ironhack.FinalProject.accountmodels.CreditCard;
import com.Ironhack.FinalProject.accountmodels.Savings;
import com.Ironhack.FinalProject.accountmodels.StudentCheckingAccount;
import com.Ironhack.FinalProject.embeddables.Address;
import com.Ironhack.FinalProject.embeddables.Money;
import com.Ironhack.FinalProject.repositories.*;
import com.Ironhack.FinalProject.roles.Role;
import com.Ironhack.FinalProject.roles.RolesEnum;
import com.Ironhack.FinalProject.usermodels.AccountHolder;
import com.Ironhack.FinalProject.usermodels.Admin;
import com.Ironhack.FinalProject.usermodels.ThirdParty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ThirdPartyControllerTests {
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
    ThirdPartyRepository thirdPartyRepository;

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
    ThirdParty thirdParty1;
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
        admin1 = new Admin("bengi", "99eni");
        admin1.addRole(new Role(RolesEnum.ADMIN, admin1));
        thirdParty1 = new ThirdParty("CaixaBank", "3442", "1");
        thirdParty1.addRole(new Role(RolesEnum.THIRD_PARTY, thirdParty1));
        adminRepository.save(admin1);
        accountHolderRepository.save(accountHolder1);
        accountHolderRepository.save(accountHolder2);
        savingsRepository.save(savings1);
        checkingAccountRepository.save(checkingAccount1);
        studentCheckingAccountRepository.save(studentCheckingAccount1);
        creditCardRepository.save(creditCard1);
        thirdPartyRepository.save(thirdParty1);
    }

    @AfterEach
    public void tearUp(){

    }
    @Test
    @DisplayName("Testing whether money is added")
    void patch_ThirdPartyAddBalance_isOk() throws Exception{
        ThirdPartyDTO thirdPartyDTO = new ThirdPartyDTO(BigDecimal.valueOf(2500), savings1.getAccountNumber(), thirdParty1.getId());
        String body = objectMapper.writeValueAsString(thirdPartyDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/third_party/add").header("hashedKey", thirdParty1.getHashedKey()).content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("4000"));
    }
    @Test
    @DisplayName("Testing whether money is subtracted")
    void patch_ThirdPartySubtractBalance_isOk() throws Exception{
        ThirdPartyDTO thirdPartyDTO = new ThirdPartyDTO(BigDecimal.valueOf(500), savings1.getAccountNumber(), thirdParty1.getId());
        String body = objectMapper.writeValueAsString(thirdPartyDTO);
        MvcResult mvcResult = mockMvc.perform(patch("/third_party/decrease").header("hashedKey", thirdParty1.getHashedKey()).content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1000"));
    }
}

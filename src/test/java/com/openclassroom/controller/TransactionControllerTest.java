package com.openclassroom.controller;

import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.model.ProBuddyTransactions;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.modelDTO.ProBuddyDepositToBankDTO;
import com.openclassroom.modelDTO.ProBuddyProfileDTO;
import com.openclassroom.modelDTO.ProBuddyTransactionDTO;
import com.openclassroom.modelDTO.ProBuddyTransferFormDTO;
import com.openclassroom.service.AccountServiceImpl;
import com.openclassroom.service.ContactsService;
import com.openclassroom.service.TransactionsService;
import com.openclassroom.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest()
public class TransactionControllerTest {

    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private ContactsService contactsService;
    @MockBean
    private TransactionsService transactionsService;
    @MockBean
    AccountServiceImpl accountService;
    @Autowired
    WebApplicationContext webContext;

    @Before
    public void setupMockMvc() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    @WithUserDetails("aw@pmb.com")
    public void transfer() throws Exception{

        mockMvc.perform(get("/user/transfer"))
                .andExpect(view().name("transaction"))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @WithUserDetails("aw@pmb.com")
    public void testGetMakeTransfer() throws Exception {


        double  amount = 20.00;
        double balance = 30.00;

        double fee = 0.05;

        String description = "cinema";

        ProBuddyAccount receiverAccount = new ProBuddyAccount();
        receiverAccount.setId(1);
        receiverAccount.setBalance(balance);

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setId(1);
        proBuddyUser.setEmail("aw@pmb.com");
        proBuddyUser.setAccount(receiverAccount);
        proBuddyUser.setLastName("Wall");
        proBuddyUser.setFirstName("Anthony");
        List<ProBuddyUser> userList1 = new ArrayList<>();

        ProBuddyTransactions createTransaction = new ProBuddyTransactions();
        createTransaction.setUserName(proBuddyUser.getFirstName());
        createTransaction.setFee(fee);
        createTransaction.setReceiver(proBuddyUser);
        createTransaction.setSender(proBuddyUser);
        createTransaction.setReceiverAccount(receiverAccount);
        createTransaction.setDescription(description);
        createTransaction.setAmount(amount);
        createTransaction.setDate(Timestamp.from(Instant.now()));

        List<ProBuddyTransactions> transactionsList = new ArrayList<>();
        transactionsList.add(createTransaction);

        when(userService.findUserByEmail(proBuddyUser.getEmail())).thenReturn(proBuddyUser);
        when(contactsService.findConnectedUserByConnectorUser(proBuddyUser)).thenReturn(userList1);
        when(transactionsService.findAll()).thenReturn(transactionsList);

        mockMvc.perform(get("/user/makeTransfer"))
                .andExpect(status().is2xxSuccessful());

    }



    @Test
    @WithUserDetails("aw@pmb.com")
    public void testPostMakeTransfer() throws Exception {

        double  amount = 20.00;
        double balance = 30.00;

        double fee = 0.05;

        String description = "cinema";

        ProBuddyAccount senderAccount = new ProBuddyAccount();
        senderAccount.setId(1);
        senderAccount.setBalance(balance);

        ProBuddyAccount receiverAccount = new ProBuddyAccount();
        receiverAccount.setId(1);
        receiverAccount.setBalance(0.00);

        ProBuddyUser senderUser = new ProBuddyUser();
        senderUser.setId(1);
        senderUser.setEmail("aw@pmb.com");
        senderUser.setAccount(senderAccount);
        senderUser.setLastName("Wall");
        senderUser.setFirstName("Anthony");
        List<ProBuddyUser> senderUserList = new ArrayList<>();


        ProBuddyUser receiverUser = new ProBuddyUser();
        receiverUser.setId(2);
        receiverUser.setEmail("jd@pmb.com");
        receiverUser.setAccount(receiverAccount);
        receiverUser.setLastName("David");
        receiverUser.setFirstName("Jeffrey");
        List<ProBuddyUser> receiverUserList = new ArrayList<>();

        ProBuddyTransactions createTransaction = new ProBuddyTransactions();
        createTransaction.setUserName(receiverUser.getFirstName());
        createTransaction.setFee(fee);
        createTransaction.setReceiver(receiverUser);
        createTransaction.setSender(senderUser);
        createTransaction.setReceiverAccount(receiverAccount);
        createTransaction.setDescription(description);
        createTransaction.setAmount(amount);
        createTransaction.setDate(Timestamp.from(Instant.now()));

        List<ProBuddyTransactions> transactionsList = new ArrayList<>();
        transactionsList.add(createTransaction);

        ProBuddyTransferFormDTO transferFormDTO = new ProBuddyTransferFormDTO();
        transferFormDTO.setConnectedUserEmail(transferFormDTO.getConnectedUserEmail());
        transferFormDTO.setAmount(amount);
        transferFormDTO.setDescription(description);

        when(userService.findUserByEmail(transferFormDTO.getConnectedUserEmail())).thenReturn(receiverUser);
        when(userService.findUserByEmail(senderUser.getEmail())).thenReturn(senderUser);
        when(transactionsService.findAll()).thenReturn(transactionsList);
        when(contactsService.findConnectedUserByConnectorUser(receiverUser)).thenReturn(receiverUserList);

        mockMvc.perform(post("/user/makeTransfer")
                .requestAttr("transferForm",  transferFormDTO))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @WithUserDetails("aw@pmb.com")
    public void errorsInTransfer() throws Exception {

        double  amount = 0.00;
        double balance = 0.00;

        double fee = 0.05;

        String description = "cinema";

        ProBuddyAccount senderAccount = new ProBuddyAccount();
        senderAccount.setId(1);
        senderAccount.setBalance(balance);

        ProBuddyAccount receiverAccount = new ProBuddyAccount();
        receiverAccount.setId(1);
        receiverAccount.setBalance(0.00);

        ProBuddyUser senderUser = new ProBuddyUser();
        senderUser.setId(1);
        senderUser.setEmail("aw@pmb.com");
        senderUser.setAccount(senderAccount);
        senderUser.setLastName("Wall");
        senderUser.setFirstName("Anthony");
        List<ProBuddyUser> senderUserList = new ArrayList<>();


        ProBuddyUser receiverUser = new ProBuddyUser();
        receiverUser.setId(2);
        receiverUser.setEmail("jd@pmb.com");
        receiverUser.setAccount(receiverAccount);
        receiverUser.setLastName("David");
        receiverUser.setFirstName("Jeffrey");
        List<ProBuddyUser> receiverUserList = new ArrayList<>();

        ProBuddyTransactions createTransaction = new ProBuddyTransactions();
        createTransaction.setUserName(receiverUser.getFirstName());
        createTransaction.setFee(fee);
        createTransaction.setReceiver(receiverUser);
        createTransaction.setSender(senderUser);
        createTransaction.setReceiverAccount(receiverAccount);
        createTransaction.setDescription(description);
        createTransaction.setAmount(amount);
        createTransaction.setDate(Timestamp.from(Instant.now()));

        List<ProBuddyTransactions> transactionsList = new ArrayList<>();
        transactionsList.add(createTransaction);

        ProBuddyTransferFormDTO transferFormDTO = new ProBuddyTransferFormDTO();
        transferFormDTO.setConnectedUserEmail(transferFormDTO.getConnectedUserEmail());
        transferFormDTO.setAmount(amount);
        transferFormDTO.setDescription(description);

        when(userService.findUserByEmail(transferFormDTO.getConnectedUserEmail())).thenReturn(receiverUser);
        when(userService.findUserByEmail(senderUser.getEmail())).thenReturn(senderUser);
        when(transactionsService.findAll()).thenReturn(transactionsList);
        when(contactsService.findConnectedUserByConnectorUser(receiverUser)).thenReturn(receiverUserList);

        mockMvc.perform(post("/user/makeTransfer"))
                .andExpect(view().name("transaction"));

    }

    @Test
    @WithUserDetails("aw@pmb.com")
    public void connectedUserDoesNotExist() throws Exception {

        double  amount = 20.00;
        double balance = 30.00;

        double fee = 0.05;

        String description = "cinema";

        ProBuddyAccount senderAccount = new ProBuddyAccount();
        senderAccount.setId(1);
        senderAccount.setBalance(balance);

        ProBuddyAccount receiverAccount = new ProBuddyAccount();
        receiverAccount.setId(1);
        receiverAccount.setBalance(0.00);

        ProBuddyUser senderUser = new ProBuddyUser();
        senderUser.setId(1);
        senderUser.setEmail("aw@pmb.com");
        senderUser.setAccount(senderAccount);
        senderUser.setLastName("Wall");
        senderUser.setFirstName("Anthony");
        List<ProBuddyUser> senderUserList = new ArrayList<>();


        ProBuddyUser receiverUser = new ProBuddyUser();
        receiverUser.setId(2);
        receiverUser.setEmail("");
        receiverUser.setAccount(receiverAccount);
        receiverUser.setLastName("David");
        receiverUser.setFirstName("Jeffrey");
        List<ProBuddyUser> receiverUserList = new ArrayList<>();

        ProBuddyTransactions createTransaction = new ProBuddyTransactions();
        createTransaction.setUserName(receiverUser.getFirstName());
        createTransaction.setFee(fee);
        createTransaction.setReceiver(receiverUser);
        createTransaction.setSender(senderUser);
        createTransaction.setReceiverAccount(receiverAccount);
        createTransaction.setDescription(description);
        createTransaction.setAmount(amount);
        createTransaction.setDate(Timestamp.from(Instant.now()));

        List<ProBuddyTransactions> transactionsList = new ArrayList<>();
        transactionsList.add(createTransaction);

        ProBuddyTransferFormDTO transferFormDTO = new ProBuddyTransferFormDTO();
        transferFormDTO.setConnectedUserEmail(transferFormDTO.getConnectedUserEmail());
        transferFormDTO.setAmount(amount);
        transferFormDTO.setDescription(description);

        when(userService.findUserByEmail(transferFormDTO.getConnectedUserEmail())).thenReturn(receiverUser);
        when(userService.findUserByEmail(senderUser.getEmail())).thenReturn(senderUser);
        when(transactionsService.findAll()).thenReturn(transactionsList);
        when(contactsService.findConnectedUserByConnectorUser(receiverUser)).thenReturn(receiverUserList);

        mockMvc.perform(post("/user/makeTransfer"))
                .andExpect(view().name("transaction"));

    }


    @Test
    @WithUserDetails("aw@pmb.com")
    public void testGetProfile() throws Exception {

        double balance = 30.00;
        ProBuddyAccount receiverAccount = new ProBuddyAccount();
        receiverAccount.setId(1);
        receiverAccount.setBalance(balance);
        receiverAccount.setBankName("Bank of France");
        receiverAccount.setBankAccountNumber("Account55");

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setId(1);
        proBuddyUser.setEmail("aw@pmb.com");
        proBuddyUser.setAccount(receiverAccount);
        proBuddyUser.setLastName("Wall");
        proBuddyUser.setFirstName("Anthony");

        ProBuddyProfileDTO proBuddyProfileDTO = new ProBuddyProfileDTO();
        proBuddyProfileDTO.setFirstName(proBuddyUser.getFirstName());
        proBuddyProfileDTO.setLastName(proBuddyUser.getLastName());
        proBuddyProfileDTO.setBankAccountNumber(receiverAccount.getBankAccountNumber());
        proBuddyProfileDTO.setBankName(receiverAccount.getBankName());
        proBuddyProfileDTO.setBalance(receiverAccount.getBalance());

        when(userService.findUserByEmail(proBuddyUser.getEmail())).thenReturn(proBuddyUser);

        mockMvc.perform(get("/user/profile")
                .requestAttr("proBuddyProfileDTO", proBuddyProfileDTO))
                .andExpect(view().name("profile"))
                .andExpect(status().is2xxSuccessful());


    }

    @Test
    @WithUserDetails("aw@pmb.com")
    public void testGetProfileWithErrors() throws Exception {

        double balance = 30.00;
        ProBuddyAccount receiverAccount = new ProBuddyAccount();
        receiverAccount.setId(1);
        receiverAccount.setBalance(balance);
        receiverAccount.setBankName("Bank of France");
        receiverAccount.setBankAccountNumber("");

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setId(1);
        proBuddyUser.setEmail("aw@pmb.com");
        proBuddyUser.setAccount(receiverAccount);
        proBuddyUser.setLastName("Wall");
        proBuddyUser.setFirstName("Anthony");

        ProBuddyProfileDTO proBuddyProfileDTO = new ProBuddyProfileDTO();
        proBuddyProfileDTO.setFirstName(proBuddyUser.getFirstName());
        proBuddyProfileDTO.setLastName(proBuddyUser.getLastName());
        proBuddyProfileDTO.setBankAccountNumber(receiverAccount.getBankAccountNumber());
        proBuddyProfileDTO.setBankName(receiverAccount.getBankName());
        proBuddyProfileDTO.setBalance(receiverAccount.getBalance());

        when(userService.findUserByEmail(proBuddyUser.getEmail())).thenReturn(null);

        mockMvc.perform(get("/user/profile")
                .requestAttr("proBuddyProfileDTO", proBuddyProfileDTO))
                .andExpect(view().name("redirect:/login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/login"));

    }

    @Test
    @WithUserDetails("aw@pmb.com")
    public void testPostDepositMoney() throws Exception {

       double balance = 25.00;
       double amount = 15.00;

        ProBuddyAccount buddyAccount = new ProBuddyAccount();
        buddyAccount.setId(1);
        buddyAccount.setBalance(balance);

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setId(1);
        proBuddyUser.setEmail("aw@pmb.com");
        proBuddyUser.setAccount(buddyAccount);
        proBuddyUser.setLastName("Wall");
        proBuddyUser.setFirstName("Anthony");

        ProBuddyDepositToBankDTO transaction = new ProBuddyDepositToBankDTO();
        transaction.setAmount(amount);

        when(userService.findUserByEmail(proBuddyUser.getEmail())).thenReturn(proBuddyUser);

        mockMvc.perform(post("/user/depositToBank")
                .requestAttr("debit",  transaction))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/user/profile?message=Transaction+successful"));

    }

    @Test
    @WithUserDetails("aw@pmb.com")
    public void testPostDepositMoneyWithNoUser() throws Exception {

        double balance = 25.00;
        double amount = 15.00;

        ProBuddyAccount buddyAccount = new ProBuddyAccount();
        buddyAccount.setId(1);
        buddyAccount.setBalance(balance);

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setId(1);
        proBuddyUser.setEmail("aw@pmb.com");
        proBuddyUser.setAccount(buddyAccount);
        proBuddyUser.setLastName("Wall");
        proBuddyUser.setFirstName("Anthony");

        ProBuddyDepositToBankDTO transaction = new ProBuddyDepositToBankDTO();
        transaction.setAmount(amount);

        when(userService.findUserByEmail(proBuddyUser.getEmail())).thenReturn(null);

        mockMvc.perform(post("/user/depositToBank")
                .requestAttr("debit",  transaction))
                .andExpect(view().name("login"))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @WithUserDetails("aw@pmb.com")
    public void testPostWithdrawMoney() throws Exception {
        double balance = 25.00;
        double amount = 15.00;

        ProBuddyAccount buddyAccount = new ProBuddyAccount();
        buddyAccount.setId(1);
        buddyAccount.setBalance(balance);

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setId(1);
        proBuddyUser.setEmail("aw@pmb.com");
        proBuddyUser.setAccount(buddyAccount);
        proBuddyUser.setLastName("Wall");
        proBuddyUser.setFirstName("Anthony");

        ProBuddyDepositToBankDTO transaction = new ProBuddyDepositToBankDTO();
        transaction.setAmount(amount);

        when(userService.findUserByEmail(proBuddyUser.getEmail())).thenReturn(proBuddyUser);

        mockMvc.perform(post("/user/withdrawFromBank")
                .requestAttr("credit", transaction))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/user/profile?errorMessage=Transaction+successful"));
    }

        @Test
        @WithUserDetails("aw@pmb.com")
        public void testPostWithdrawMoneyWithNoUser() throws Exception {
            double balance = 25.00;
            double amount = 15.00;

            ProBuddyAccount buddyAccount = new ProBuddyAccount();
            buddyAccount.setId(1);
            buddyAccount.setBalance(balance);

            ProBuddyUser proBuddyUser = new ProBuddyUser();
            proBuddyUser.setId(1);
            proBuddyUser.setEmail("aw@pmb.com");
            proBuddyUser.setAccount(buddyAccount);
            proBuddyUser.setLastName("Wall");
            proBuddyUser.setFirstName("Anthony");

            ProBuddyDepositToBankDTO transaction = new ProBuddyDepositToBankDTO();
            transaction.setAmount(amount);

            when(userService.findUserByEmail(proBuddyUser.getEmail())).thenReturn(null);

            mockMvc.perform(post("/user/depositToBank")
                    .requestAttr("credit",  transaction))
                    .andExpect(view().name("login"))
                    .andExpect(status().is2xxSuccessful());

    }


}
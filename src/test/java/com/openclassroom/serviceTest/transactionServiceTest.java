package com.openclassroom.serviceTest;
import com.openclassroom.configuration.InsufficientBalanceException;
import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.model.ProBuddyTransactions;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.openclassroom.service.TransactionsServiceImpl;
import  com.openclassroom.service.AccountServiceImpl;
import com.openclassroom.repositories.TransactionsRepository;

import java.util.List;
import java.util.ArrayList;

import java.sql.Timestamp;
import java.time.Instant;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;



@RunWith(MockitoJUnitRunner.class)
public class transactionServiceTest {

    @InjectMocks
    TransactionsServiceImpl transactionService;

    @Mock
    TransactionsRepository transactionRepository;

    @Mock
    UserServiceImpl userService;

    @Mock
    AccountServiceImpl accountService;

    /*int proBuddyUser2 = new ProBuddyUser();
        proBuddyUser2.setEmail("buddy2@pmb.com");
        proBuddyUser2.setId(2);

    int senderAccount = new ProBuddyAccount();
        senderAccount.setUser(proBuddyUser);
    ProBuddyAccount receiverAccount = new ProBuddyAccount();
        receiverAccount.setUser(proBuddyUser2);*/

    @Test
    public void createTransaction_returnCreatedTransaction() throws InsufficientBalanceException {

        //arrange
        double  amount = 20.00;

        double fee = 0.05;

        String description = "cinema";

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setId(1);
        proBuddyUser.setEmail("buddy@pmb.com");

        ProBuddyUser proBuddyUser2 = new ProBuddyUser();
        proBuddyUser2.setId(2);
        proBuddyUser2.setEmail("buddy2@pmb.com");

        ProBuddyAccount senderAccount = new ProBuddyAccount();
        senderAccount.setUser(proBuddyUser);
        ProBuddyAccount receiverAccount = new ProBuddyAccount();
        receiverAccount.setUser(proBuddyUser2);

        ProBuddyTransactions createTransaction = new ProBuddyTransactions();
        createTransaction.setReceiver(proBuddyUser);
        createTransaction.setSender(proBuddyUser2);
        createTransaction.setFee(fee);
        createTransaction.setSenderAccount(senderAccount);
        createTransaction.setReceiverAccount(receiverAccount);
        createTransaction.setDescription(description);
        createTransaction.setAmount(amount);
        createTransaction.setDate(Timestamp.from(Instant.now()));

        when(accountService.findAccountById(senderAccount.getId())).thenReturn(senderAccount);
        when(accountService.findAccountById(receiverAccount.getId())).thenReturn(receiverAccount);


        //act
        transactionService.createTransactionByTransferMoney(senderAccount.getId(), receiverAccount.getId(), amount, description);

        //assert
        verify(transactionRepository, times(1)).save(any(ProBuddyTransactions.class));

    }


    @Test
    public void findAll_return(){

        //arrange
        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("buddy@@pmb.com");

        ProBuddyTransactions findAllTransactions = new ProBuddyTransactions();
        findAllTransactions.setSender(proBuddyUser);

        List<ProBuddyTransactions> transactionsList = new ArrayList<>();
        transactionsList.add(findAllTransactions);

        when(transactionRepository.findAll()).thenReturn(transactionsList);

        //act
        List<ProBuddyTransactions> result = transactionService.findAll();

        //assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }


    @Test
    public void withdrawCash_returnWithdraw(){

        //arrange
        double  amount = 20.00;

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("buddy@pmb.com");
        ProBuddyAccount proBuddyAccount = new ProBuddyAccount();
        proBuddyAccount.setUser(proBuddyUser);
        proBuddyAccount.setBalance(amount);

        when(accountService.findAccountByUserEmail(proBuddyUser.getEmail())).thenReturn(proBuddyAccount);

        //act
        transactionService.withdraw(proBuddyUser, amount);

        //assert
      //  verify(transactionRepository

    }

    @Test
    public void depositCash_returnDepositCash(){

    }
}

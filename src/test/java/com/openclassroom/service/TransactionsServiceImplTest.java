package com.openclassroom.service;

import com.openclassroom.configuration.InsufficientBalanceException;
import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.model.ProBuddyTransactions;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.repositories.TransactionsRepository;
import com.openclassroom.repositories.UserRepository;
import com.openclassroom.repositories.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionsServiceImplTest {



    @InjectMocks
    TransactionsServiceImpl transactionService;

    @Mock
    TransactionsRepository transactionRepository;

    @Mock
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    AccountServiceImpl accountService;

    @Test
    public void createTransaction_returnCreatedTransaction() throws InsufficientBalanceException {

        //arrange
        double  amount = 20.00;
        double balance = 30.00;

        double fee = 0.05;

        String description = "cinema";

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setId(1);
        proBuddyUser.setEmail("buddy@pmb.com");
        java.util.Optional<ProBuddyUser> optionalUser1 = java.util.Optional.of(proBuddyUser);

        ProBuddyUser proBuddyUser2 = new ProBuddyUser();
        proBuddyUser2.setId(2);
        proBuddyUser2.setEmail("buddy2@pmb.com");
        java.util.Optional<ProBuddyUser> optionalUser2 = java.util.Optional.of(proBuddyUser2);

        ProBuddyAccount senderAccount = new ProBuddyAccount();
        senderAccount.setUser(proBuddyUser);
        senderAccount.setBalance(balance);
        senderAccount.setId(1);
        ProBuddyAccount receiverAccount = new ProBuddyAccount();
        receiverAccount.setUser(proBuddyUser2);
        receiverAccount.setId(2);

        ProBuddyTransactions createTransaction = new ProBuddyTransactions();
        createTransaction.setReceiver(proBuddyUser);
        createTransaction.setSender(proBuddyUser2);
        createTransaction.setFee(fee);
        createTransaction.setSenderAccount(senderAccount);
        createTransaction.setReceiverAccount(receiverAccount);
        createTransaction.setDescription(description);
        createTransaction.setAmount(amount);
        createTransaction.setDate(Timestamp.from(Instant.now()));

        when(userRepository.findById(proBuddyUser.getId())).thenReturn(optionalUser1);
        when(userRepository.findById(proBuddyUser2.getId())).thenReturn(optionalUser2);
        when(accountRepository.findAccountByUserEmail(proBuddyUser.getEmail())).thenReturn(senderAccount);
        when(accountRepository.findAccountByUserEmail(proBuddyUser2.getEmail())).thenReturn(receiverAccount);


        //act
        transactionService.createTransactionByTransferMoney(senderAccount.getId(), receiverAccount.getId(),
                amount, description);

        //assert
        verify(transactionRepository, times(1)).save(any(ProBuddyTransactions.class));

    }


    @Test
    public void findAll_return(){

        //arrange
        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("buddy@pmb.com");

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
    public void withdrawCash_returnWithdraw() throws InsufficientBalanceException{

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
        verify(accountRepository, times(1)).save(any(ProBuddyAccount.class));

    }

    @Test
    public void depositCash_returnDepositCash() throws InsufficientBalanceException{

        //arrange
        double  amount = 20.00;

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("buddy@pmb.com");
        ProBuddyAccount proBuddyAccount = new ProBuddyAccount();
        proBuddyAccount.setUser(proBuddyUser);
        proBuddyAccount.setBalance(amount);

        when(accountService.findAccountByUserEmail(proBuddyUser.getEmail())).thenReturn(proBuddyAccount);

        //act
        transactionService.deposit(proBuddyUser, amount);

        //assert
        verify(accountRepository, times(1)).save(any(ProBuddyAccount.class));


    }
}

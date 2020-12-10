package com.openclassroom.service;
import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.repositories.AccountRepository;
import com.openclassroom.service.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void createAccount_returnCreatedAccount(){

        //arrange
        double balance = 25.00;

        ProBuddyUser proBuddyUser = new ProBuddyUser();

        ProBuddyAccount proBuddyAccount = new ProBuddyAccount();
        proBuddyAccount.setUser(proBuddyUser);
        proBuddyAccount.setBankAccountNumber("Account22");
        proBuddyAccount.setBankName("Bank of France");
        proBuddyAccount.setBalance(balance);
        when(accountRepository.save(any(ProBuddyAccount.class))).thenReturn(new ProBuddyAccount());

        //act
        ProBuddyAccount createAccount = accountService.createAccount(proBuddyAccount);

        //assert
        verify(accountRepository, times(1)).save(any(ProBuddyAccount.class));

    }

    @Test
    public void findAccountByUserEmail_returnUserEmail(){

        //arrange
        ProBuddyUser proBuddyUser = new ProBuddyUser();
        proBuddyUser.setEmail("email@pmb.com");

        ProBuddyAccount proBuddyAccount = new ProBuddyAccount();
        proBuddyAccount.setUser(proBuddyUser);

        when(accountRepository.findAccountByUserEmail(proBuddyAccount.getUser().getEmail())).thenReturn(proBuddyAccount);

        //act
        ProBuddyAccount findAccountByUserEmail = accountService.findAccountByUserEmail("email@pmb.com");

        //assert
        assertEquals(proBuddyAccount, findAccountByUserEmail);
    }

    @Test
    public void findAccountById_returnId(){

        //arrange
        ProBuddyUser proBuddyUser = new ProBuddyUser();
        ProBuddyAccount proBuddyAccount = new ProBuddyAccount();
        proBuddyAccount.setUser(proBuddyUser);
        proBuddyAccount.setId(45);

        when(accountRepository.findById(proBuddyAccount.getId())).thenReturn(Optional.of(proBuddyAccount));

        //act
        ProBuddyAccount findAccountById = accountService.findAccountById(45);

        //assert
        assertEquals(proBuddyAccount.getId(), findAccountById.getId(), 0);


    }

    @Test
    public void updateAccount_returnUpdatedAccount(){

        //arrange
        double balance = 25.00;

        ProBuddyUser proBuddyUser = new ProBuddyUser();
        ProBuddyAccount proBuddyAccount = new ProBuddyAccount();
        proBuddyAccount.setUser(proBuddyUser);
        proBuddyAccount.setId(1);
        proBuddyAccount.setBankName("Bank of America");
        proBuddyAccount.setBankAccountNumber("Account 68");
        proBuddyAccount.setBalance(balance);

        when(accountRepository.findById(1)).thenReturn(Optional.of(proBuddyAccount));

         //act
         accountService.updateAccount(proBuddyAccount);

        //assert
        verify(accountRepository, times(1)).save(any(ProBuddyAccount.class));

    }
}

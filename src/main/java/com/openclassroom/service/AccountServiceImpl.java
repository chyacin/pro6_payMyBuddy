package com.openclassroom.service;

import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ProBuddyAccount createAccount(ProBuddyAccount account) {
        return accountRepository.save(account);
    }

    @Override
    public ProBuddyAccount findAccountByUserEmail(String email) {
        return accountRepository.findAccountByUserEmail(email);
    }

    @Override
    public ProBuddyAccount findAccountById(int id) {
        Optional<ProBuddyAccount> proBuddyAccountOptional = accountRepository.findById(id);
        if(proBuddyAccountOptional.isPresent()){
            ProBuddyAccount proBuddyAccount = proBuddyAccountOptional.get();
            return proBuddyAccount;
        }
        return null;
    }

    @Override
    public void updateAccount(ProBuddyAccount account) {
        ProBuddyAccount updatedAccount = findAccountById(account.getId());
        updatedAccount.setBalance(account.getBalance());
        accountRepository.save(updatedAccount);

    }
}

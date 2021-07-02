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

    /**
     * The service method which saves a probuddy account to the database
     * @param account the account to be saved
     * @return the object is the saved account
     */
    @Override
    public ProBuddyAccount createAccount(ProBuddyAccount account) {
        return accountRepository.save(account);
    }

    /**
     * The service method which finds the account by user's email
     * @param email the email of the user
     * @return the found probuddy account
     */
    @Override
    public ProBuddyAccount findAccountByUserEmail(String email) {
        return accountRepository.findAccountByUserEmail(email);
    }

    /**
     * The service method which finds the account by id
     * @param id the id of the user
     * @return the found probuddy account
     */
    @Override
    public ProBuddyAccount findAccountById(int id) {
        Optional<ProBuddyAccount> proBuddyAccountOptional = accountRepository.findById(id);
        if(proBuddyAccountOptional.isPresent()){
            ProBuddyAccount proBuddyAccount = proBuddyAccountOptional.get();
            return proBuddyAccount;
        }
        return null;
    }

    /**
     * the service method which updates the user's account
     * @param account the account to be updated
     */
    @Override
    public void updateAccount(ProBuddyAccount account) {
        ProBuddyAccount updatedAccount = findAccountById(account.getId());
        updatedAccount.setBankName(account.getBankName());
        updatedAccount.setBankAccountNumber(account.getBankAccountNumber());
        updatedAccount.setBalance(account.getBalance());
        accountRepository.save(updatedAccount);

    }
}

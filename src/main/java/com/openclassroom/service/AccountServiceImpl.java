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

    /* Each javadoc needs to have this:
            1. Description of the method: what it does in layman's terms
            2. Parameters: name & description of each parameter in layman's terms
            3. Return: type & description of return value in layman's terms
    You only need javadoc for controller & service methods
    Nothing else needs it*/

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

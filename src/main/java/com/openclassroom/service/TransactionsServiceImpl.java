package com.openclassroom.service;

import com.openclassroom.configuration.InsufficientBalanceException;
import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.model.ProBuddyTransactions;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.repositories.AccountRepository;
import com.openclassroom.repositories.TransactionsRepository;
import com.openclassroom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;


    // 5% fee for every transaction in the app...user to user.
    private double transactionFee = 0.05;

    @Override
    @Transactional
    public void createTransactionByTransferMoney(int sendingUserID, int receivingUserID, Double amount,
                                                                 String description) throws InsufficientBalanceException {

        ProBuddyUser receiver= userRepository.findById(receivingUserID).get();
        ProBuddyUser sender= userRepository.findById(sendingUserID).get();
        ProBuddyAccount senderAccount = accountRepository.findAccountByUserEmail(sender.getEmail()) ;
        ProBuddyAccount receiverAccount = accountRepository.findAccountByUserEmail(receiver.getEmail());

        double fee = amount * transactionFee;
        double totalCharge = amount - fee;

        //message if you have insufficient funds so that you cannot transfer
        if(senderAccount.getBalance() < amount){
            throw new InsufficientBalanceException("Not enough money to fund the transfer of " + amount);
        }

        // debit the sender account of the amount + fee
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        // credit the receiver account with the amount
        receiverAccount.setBalance(receiverAccount.getBalance() + totalCharge);

        // update both sender and receiver accounts
        accountService.updateAccount(senderAccount);
        accountService.updateAccount(receiverAccount);

        Timestamp date= Timestamp.from(Instant.now());

        ProBuddyTransactions createTransaction = new ProBuddyTransactions();
        createTransaction.setReceiver(receiver);
        createTransaction.setSender(sender);
        createTransaction.setFee(fee);
        createTransaction.setSenderAccount(senderAccount);
        createTransaction.setReceiverAccount(receiverAccount);
        createTransaction.setDescription(description);
        createTransaction.setAmount(totalCharge);
        createTransaction.setDate(date);

        transactionsRepository.save(createTransaction);

    }

    @Override
    public List<ProBuddyTransactions> findAll() {
        return transactionsRepository.findAll();
    }

    @Override
    public List<ProBuddyTransactions> findAllByAccount(ProBuddyAccount account) {

        //List<ProBuddyTransactions> buddyTransactionsList = new ArrayList<>();
        //buddyTransactionsList.addAll(transactionsRepository.findAllBySenderAccount(account));
        //buddyTransactionsList.addAll(transactionsRepository.findAllByReceiverAccount(account));

        return transactionsRepository.findAllBySenderAccountOrReceiverAccount(account, account);

    }

    @Override
    public void withdraw(ProBuddyUser user, double amount) throws InsufficientBalanceException
    {

        // Get the logged in user
        // Get his ProBuddyAccount object
        ProBuddyAccount account = accountService.findAccountByUserEmail(user.getEmail());
        if((account.getBalance() - amount) < 0){
            throw new InsufficientBalanceException("You have insufficient funds for this transaction");
        }

        // credit the ProBuddyAccount with the given amount
        account.setBalance(account.getBalance() + amount);
        // Call account service updateAccount method to save it
        //accountService.updateAccount(account);
        accountRepository.save(account);

        Timestamp date= Timestamp.from(Instant.now());

        ProBuddyTransactions createTransaction = new ProBuddyTransactions();
        createTransaction.setReceiver(account.getUser());
        createTransaction.setSender(null);
        createTransaction.setFee(0.0);
        createTransaction.setSenderAccount(null);
        createTransaction.setReceiverAccount(account);
        createTransaction.setDescription("Withdrawal from bank to user account");
        createTransaction.setAmount(amount);
        createTransaction.setDate(date);

        transactionsRepository.save(createTransaction);


    }


    @Override
    public void deposit(ProBuddyUser user, double amount) throws InsufficientBalanceException
    {
        // Get his ProBuddyAccount object
        ProBuddyAccount account = accountService.findAccountByUserEmail(user.getEmail());
        if((account.getBalance() - amount) < 0){
            throw new InsufficientBalanceException("You have insufficient funds for this transaction");
        }
        // debit the ProBuddyAccount with the given amount
        account.setBalance(account.getBalance() - amount);
        // Call account service updateAccount method to save it
        //accountService.updateAccount(account);
        accountRepository.save(account);

        Timestamp date= Timestamp.from(Instant.now());

        ProBuddyTransactions createTransaction = new ProBuddyTransactions();
        createTransaction.setReceiver(null);
        createTransaction.setSender(account.getUser());
        createTransaction.setFee(0.0);
        createTransaction.setSenderAccount(account);
        createTransaction.setReceiverAccount(null);
        createTransaction.setDescription("Deposit to bank from user account");
        createTransaction.setAmount(amount);
        createTransaction.setDate(date);

        transactionsRepository.save(createTransaction);

    }

}

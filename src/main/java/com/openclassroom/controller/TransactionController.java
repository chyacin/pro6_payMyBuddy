package com.openclassroom.controller;

import com.openclassroom.configuration.InsufficientBalanceException;
import com.openclassroom.model.ProBuddyTransactions;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.model.ProBuddyUserDetails;
import com.openclassroom.modelDTO.*;
import com.openclassroom.service.ContactsService;
import com.openclassroom.service.TransactionsService;
import com.openclassroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    private UserService userService;
    @Autowired
    private ContactsService contactsService;
    @Autowired
    private TransactionsService transactionsService;

    /**
     *The controller method which routes to the transaction page but doesn't load the transactions
     * @param modelAndView this is a request scoped object injected for us by spring and it's stores attributes.
     * @return ModelAndView which contains the stored attributes and objects we pass to the web page.
     */
    @GetMapping("/user/transfer")
    public ModelAndView transfer(ModelAndView modelAndView) {
        modelAndView.addObject("transferForm", new ProBuddyTransferFormDTO());
        modelAndView.setViewName("transaction");
        return modelAndView;
    }

    private double transactionFee = 0.05; // to put in a table in the database(mysql)

    /**
     * The controller method which routes to the transaction page and loads the transactions
     * @param user this is the logged in user details(information)
     * @param transferFormDTO this is a data transfer object used for the transfer form on the page
     * @param modelAndView this is a request scoped object injected for us by spring and it's stores attributes.
     * @param result the validation status of each input field in the form
     * @param model this contains the object and attributes which can be passed to the web page
     * @return ModelAndView which contains the stored attributes and objects we pass to the web page
     */
    @GetMapping("/user/transaction")
    public ModelAndView makeTransfer(@AuthenticationPrincipal ProBuddyUserDetails user, ProBuddyTransferFormDTO transferFormDTO,
                                     ModelAndView modelAndView, BindingResult result, Model model) {

        String loggedInName = user.getUsername(); //get logged in username

        ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);
            model.addAttribute("transferForm", new ProBuddyTransferFormDTO());
            modelAndView.addObject(model);


        if (loggedInUser != null) {
            List<ProBuddyUser> connectedUserList = contactsService.findConnectedUserByConnectorUser(loggedInUser);
            List<ProBuddyTransactions> transactionsList = transactionsService.findAllByAccount(loggedInUser.getAccount());
            List<ProBuddyTransactionDTO> dtoList = new ArrayList<>();

            for (ProBuddyTransactions proBuddytransactions : transactionsList) {
                ProBuddyTransactionDTO dto = new ProBuddyTransactionDTO();
                if (proBuddytransactions.getSender() != null) {
                    dto.setSendingUserID(proBuddytransactions.getSender().getId());
                }
                if (proBuddytransactions.getReceiver() != null) {
                    dto.setUserName(proBuddytransactions.getReceiver().getFirstName()
                            + " " + proBuddytransactions.getReceiver().getLastName());
                } else {
                    dto.setUserName("Bank");
                }
                dto.setDescription(proBuddytransactions.getDescription());
                dto.setAmount(proBuddytransactions.getAmount());
                dto.setFee(proBuddytransactions.getFee());
                dtoList.add(dto);
            }
            if (dtoList.isEmpty()) {
                dtoList.add(new ProBuddyTransactionDTO(0.00, ""));
            }
            Collections.reverse(dtoList);
            modelAndView.setViewName("transaction");
            modelAndView.addObject("connectedUserList", connectedUserList);
            modelAndView.addObject("transferForm", new ProBuddyTransferFormDTO());
            modelAndView.addObject("dtoList", dtoList);
            modelAndView.addObject("dto", new ProBuddyTransactionDTO());
            modelAndView.addObject("message", "");
        } else {
            modelAndView.setViewName("transaction");
        }
        return modelAndView;
    }


    /**
     * The controller method which processes the transfer form values and perform debit and credit from user to user
     * @param user this is the logged in user details(information)
     * @param transferFormDTO this is a data transfer object used for the transfer form on the page
     * @param result the validation status of each input field in the form
     * @param modelAndView this is a request scoped object injected for us by spring and it's stores attributes.
     * @return ModelAndView which contains the stored attributes and objects we pass to the web page
     */
    @PostMapping("/user/transaction")
    public ModelAndView makeTransfer(@AuthenticationPrincipal ProBuddyUserDetails user,
                                     @ModelAttribute("transferForm") @Valid ProBuddyTransferFormDTO transferFormDTO,
                                     BindingResult result, ModelAndView modelAndView) {


        String loggedInName = user.getUsername(); //get logged in username
        ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);
        System.out.println("The sender is " + loggedInUser.getEmail());
        System.out.println("The amount to transfer is " + transferFormDTO.getAmount());

        ProBuddyUser connectedBuddy = userService.findUserByEmail(transferFormDTO.getConnectedUserEmail());

        if (result.hasErrors()) {

                List<ProBuddyUser> connectedUserList = contactsService.findConnectedUserByConnectorUser(loggedInUser);
                modelAndView.addObject("connectedUserList", connectedUserList);
                System.out.println("Size " + connectedUserList.size());

            modelAndView.addObject("dtoList", loadTransactionByUser(loggedInUser));
            modelAndView.setViewName("transaction");
            return modelAndView;
        }



        if (connectedBuddy == null ) {
            modelAndView.setViewName("transaction");
            result.reject("User with email" + transferFormDTO.getConnectedUserEmail() + "does not exist");
            return modelAndView;


        }



        if (loggedInUser != null) {
            try {
                transactionsService.createTransactionByTransferMoney(loggedInUser.getId(), connectedBuddy.getId(),
                        transferFormDTO.getAmount(), transferFormDTO.getDescription());

                modelAndView.addObject("message", "Transaction successful");
                System.out.println("Transaction successful");
            } catch (InsufficientBalanceException e) {
                modelAndView.setViewName("transaction");
                modelAndView.addObject("dtoList", loadTransactionByUser(loggedInUser));
             //   List<ProBuddyUser> connectedUserList = contactsService.findConnectedUserByConnectorUser(loggedInUser);
                modelAndView.addObject("errorMessage", "Insufficient balance!");


                System.out.println("Insufficient balance");
                return modelAndView;
            }
            List<ProBuddyTransactions> transactionsList = transactionsService.findAllByAccount(loggedInUser.getAccount());
            List<ProBuddyTransactionDTO> dtoList = new ArrayList<>();

            for (ProBuddyTransactions proBuddytransactions : transactionsList) {
                ProBuddyTransactionDTO dto = new ProBuddyTransactionDTO();
                if(proBuddytransactions.getSender() != null) {
                    dto.setSendingUserID(proBuddytransactions.getSender().getId());
                }
                if (proBuddytransactions.getReceiver() != null) {
                    dto.setUserName(proBuddytransactions.getReceiver().getFirstName()
                            + " " + proBuddytransactions.getReceiver().getLastName());
                } else {
                    dto.setUserName("Bank");
                }
                dto.setDescription(proBuddytransactions.getDescription());
                dto.setAmount(proBuddytransactions.getAmount());
                dto.setFee(proBuddytransactions.getFee());
                dtoList.add(dto);
            }
            if (dtoList.isEmpty()) {
                dtoList.add(new ProBuddyTransactionDTO(0.00, " "));
            }
            Collections.reverse(dtoList);
            modelAndView.setViewName("redirect:/user/makeTransfer");
            modelAndView.addObject("transferForm", new ProBuddyTransferFormDTO());
            modelAndView.addObject("dtoList", dtoList);
            modelAndView.addObject("dto", new ProBuddyTransactionDTO());

            List<ProBuddyUser> connectedUserList = contactsService.findConnectedUserByConnectorUser(connectedBuddy);

            modelAndView.setViewName("redirect:/user/transaction");
            modelAndView.addObject("connectedUserList", connectedUserList);
        } else {
            modelAndView.setViewName("transaction");
        }

        return modelAndView;
    }

    /**
     * The method that loads the transaction for a user and is used in several controller methods
     * @param loggedInUser this is the logged in user
     * @return List of probuddy transactions for a user
     */
    private List<ProBuddyTransactionDTO> loadTransactionByUser(ProBuddyUser loggedInUser){

        List<ProBuddyTransactions> transactionsList = transactionsService.findAllByAccount(loggedInUser.getAccount());
        List<ProBuddyTransactionDTO> dtoList = new ArrayList<>();

        for (ProBuddyTransactions proBuddytransactions : transactionsList) {
            ProBuddyTransactionDTO dto = new ProBuddyTransactionDTO();
            if(proBuddytransactions.getSender() != null) {
                dto.setSendingUserID(proBuddytransactions.getSender().getId());
            }
            if (proBuddytransactions.getReceiver() != null) {
                dto.setUserName(proBuddytransactions.getReceiver().getFirstName()
                        + " " + proBuddytransactions.getReceiver().getLastName());
            } else {
                dto.setUserName("Bank");
            }
            dto.setDescription(proBuddytransactions.getDescription());
            dto.setAmount(proBuddytransactions.getAmount());
            dto.setFee(proBuddytransactions.getFee());
            dtoList.add(dto);
        }
        if (dtoList.isEmpty()) {
            dtoList.add(new ProBuddyTransactionDTO(0.00, " "));
        }
        Collections.reverse(dtoList);

        return dtoList;
    }

    /**
     * The controller method which routes the user to the profile page
     * @param user this is the logged in user details(information)
     * @param modelAndView this is a request scoped object injected for us by spring and it's stores attributes.
     * @return ModelAndView which contains the stored attributes and objects we pass to the web page.
     */
    @GetMapping("/user/profile")
    public ModelAndView profile(@AuthenticationPrincipal ProBuddyUserDetails user, ModelAndView modelAndView) {
        String loggedInName = user.getUsername(); //get logged in username
        ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);
        if (loggedInUser != null) {
            modelAndView.setViewName("profile");
            modelAndView.addObject("user", loggedInName);
            ProBuddyProfileDTO proBuddyProfileDTO = new ProBuddyProfileDTO();
            proBuddyProfileDTO.setFirstName(loggedInUser.getFirstName());
            proBuddyProfileDTO.setLastName(loggedInUser.getLastName());
            proBuddyProfileDTO.setBankAccountNumber(loggedInUser.getAccount().getBankAccountNumber());
            proBuddyProfileDTO.setBankName(loggedInUser.getAccount().getBankName());
            proBuddyProfileDTO.setBalance(loggedInUser.getAccount().getBalance());

            modelAndView.addObject("account", proBuddyProfileDTO);
            modelAndView.addObject("debit", new ProBuddyDepositToBankDTO());
            modelAndView.addObject("credit", new ProBuddyWithdrawFromBankDTO());
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }



    /**This method that creates a profile object for the user and is used in some controller methods
     * @param loggedInUser this is the logged in user
     * @return profile object for the user
     */
    private ProBuddyProfileDTO loadProfilePerson(ProBuddyUser loggedInUser){
        ProBuddyProfileDTO proBuddyProfileDTO = new ProBuddyProfileDTO();
        proBuddyProfileDTO.setFirstName(loggedInUser.getFirstName());
        proBuddyProfileDTO.setLastName(loggedInUser.getLastName());
        proBuddyProfileDTO.setBankAccountNumber(loggedInUser.getAccount().getBankAccountNumber());
        proBuddyProfileDTO.setBankName(loggedInUser.getAccount().getBankName());
        proBuddyProfileDTO.setBalance(loggedInUser.getAccount().getBalance());
        return proBuddyProfileDTO;
    }

    /**
     * The controller method which processes deposit to the user's bank account from the user's probuddy account
     * @param user this is the logged in user details(information)
     * @param transaction is a data transfer object that contains the amount to be deposited
     * @param result the validation status of each input field in the form
     * @param modelAndView this is a request scoped object injected for us by spring and it's stores attributes.
     * @return ModelAndView which contains the stored attributes and objects we pass to the web page.
     * @throws InsufficientBalanceException is an application defined exception that get thrown when the user balance is insufficient for transaction
     */
    @PostMapping("/user/depositToBank")
    public ModelAndView depositMoney(@AuthenticationPrincipal ProBuddyUserDetails user, @ModelAttribute("debit")
    @Valid ProBuddyDepositToBankDTO transaction,
                                     BindingResult result, ModelAndView modelAndView) throws InsufficientBalanceException{


        // get the amount to debit
        double amount = transaction.getAmount();

        String loggedInName = user.getUsername(); //get logged in username

        ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);
        if (loggedInUser != null) {
            transactionsService.withdraw(loggedInUser, amount);
            modelAndView.setViewName("redirect:/user/profile");
            modelAndView.addObject("message", "Transaction successful");
            return modelAndView;
        }

        if(result.hasErrors()) {
            modelAndView.setViewName("profile");
            modelAndView.addObject("errorMessage", "Transaction unsuccessful");

            return modelAndView;
        }
        else{
            modelAndView.setViewName("login");


        }
        return modelAndView;
    }


    /**
     * The controller method which processes withdrawn from the user's bank account to the user's probuddy account
     * @param user this is the logged in user details(information)
     * @param transaction is a data transfer object that contains the amount to be withdrawn
     * @param result the validation status of each input field in the form
     * @param modelAndView this is a request scoped object injected for us by spring and it's stores attributes.
     * @return ModelAndView which contains the stored attributes and objects we pass to the web page.
     */
    @PostMapping("/user/withdrawFromBank")
    public ModelAndView withdrawMoney(@AuthenticationPrincipal ProBuddyUserDetails user, @ModelAttribute("credit") @Valid ProBuddyWithdrawFromBankDTO transaction,
                                      BindingResult result, ModelAndView modelAndView){

        // get the amount to debit
        double amount = transaction.getAmount();

        String loggedInName = user.getUsername(); //get logged in username

        ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);
        if (loggedInUser != null) {
            try {
                transactionsService.deposit(loggedInUser, amount);
            }
            catch(InsufficientBalanceException e) {
                modelAndView.setViewName("profile");
                modelAndView.addObject("account", loadProfilePerson(loggedInUser));
                modelAndView.addObject("debit", new ProBuddyDepositToBankDTO());
                modelAndView.addObject("credit", new ProBuddyWithdrawFromBankDTO());
                modelAndView.addObject("errorMessage", "You don't have enough balance for the transaction");
                return modelAndView;
            }

            modelAndView.setViewName("redirect:/user/profile");
            modelAndView.addObject("errorMessage", "Transaction successful");
            return modelAndView;
        }
        else{
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }
}

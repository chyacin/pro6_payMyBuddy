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

    @GetMapping("/user/transfer")
    public ModelAndView transfer(ModelAndView modelAndView) {
        modelAndView.addObject("transferForm", new ProBuddyTransferFormDTO());
        modelAndView.setViewName("transaction");
        return modelAndView;
    }

    private double transactionFee = 0.05; // to put in a table in the database(mysql)

    @GetMapping("/user/makeTransfer")
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

    @PostMapping("/user/makeTransfer")
    public ModelAndView makeTransfer(@AuthenticationPrincipal ProBuddyUserDetails user,
                                     @ModelAttribute("transferForm") @Valid ProBuddyTransferFormDTO transferFormDTO,
                                     BindingResult result, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {


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
                modelAndView.addObject("message", "Insufficient balance");
                System.out.println("Insufficient balance");
                e.printStackTrace();
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

            modelAndView.setViewName("redirect:/user/makeTransfer");
            modelAndView.addObject("connectedUserList", connectedUserList);
        } else {
            modelAndView.setViewName("transaction");
        }

        return modelAndView;
    }

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

    private ProBuddyProfileDTO loadProfilePerson(ProBuddyUser loggedInUser){
        ProBuddyProfileDTO proBuddyProfileDTO = new ProBuddyProfileDTO();
        proBuddyProfileDTO.setFirstName(loggedInUser.getFirstName());
        proBuddyProfileDTO.setLastName(loggedInUser.getLastName());
        proBuddyProfileDTO.setBankAccountNumber(loggedInUser.getAccount().getBankAccountNumber());
        proBuddyProfileDTO.setBankName(loggedInUser.getAccount().getBankName());
        proBuddyProfileDTO.setBalance(loggedInUser.getAccount().getBalance());
        return proBuddyProfileDTO;
    }


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

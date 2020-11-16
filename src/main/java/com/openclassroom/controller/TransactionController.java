package com.openclassroom.controller;

import com.openclassroom.configuration.InsufficientBalanceException;
import com.openclassroom.model.ProBuddyTransactions;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.model.ProBuddyUserDetails;
import com.openclassroom.modelDTO.ProBuddyProfileDTO;
import com.openclassroom.modelDTO.ProBuddyTransactionDTO;
import com.openclassroom.modelDTO.ProBuddyTransferFormDTO;
import com.openclassroom.service.ContactsService;
import com.openclassroom.service.TransactionsService;
import com.openclassroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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
    public ModelAndView transfer(ModelAndView modelAndView){
        modelAndView.addObject("transferForm", new ProBuddyTransferFormDTO());
        modelAndView.setViewName("transaction");
        return modelAndView;
    }

    private double transactionFee = 0.05; // to put in a table in the database(mysql)

    @GetMapping("/user/makeTransfer")
    public ModelAndView makeTransfer(@AuthenticationPrincipal ProBuddyUserDetails user, ProBuddyTransferFormDTO transferFormDTO,
                                     ModelAndView modelAndView) {


        String loggedInName = user.getUsername(); //get logged in username

        ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);
        if (loggedInUser != null) {
            List<ProBuddyUser> connectedUserList = contactsService.findConnectedUserByConnectorUser(loggedInUser);
            List<ProBuddyTransactions> transactionsList = transactionsService.findAll();
            List<ProBuddyTransactionDTO> dtoList = new ArrayList<>();

            for (ProBuddyTransactions proBuddytransactions : transactionsList) {
                ProBuddyTransactionDTO dto = new ProBuddyTransactionDTO();
                dto.setSendingUserID(proBuddytransactions.getSender().getId());
                dto.setUserName(proBuddytransactions.getReceiver().getFirstName() +" "+proBuddytransactions.getReceiver().getLastName());
                dto.setDescription(proBuddytransactions.getDescription());
                dto.setAmount(proBuddytransactions.getAmount());
                dto.setFee(proBuddytransactions.getFee());
                dtoList.add(dto);
            }
            if(dtoList.isEmpty()) {
                dtoList.add(new ProBuddyTransactionDTO(0.00, "Account is empty"));
            }
            modelAndView.setViewName("transaction");
            modelAndView.addObject("connectedUserList", connectedUserList);
            modelAndView.addObject("transferForm", new ProBuddyTransferFormDTO());
            modelAndView.addObject("dtoList", dtoList);
            modelAndView.addObject("dto", new ProBuddyTransactionDTO());
            modelAndView.addObject("message", "");
        }
        else{
            modelAndView.setViewName("transaction");
        }
        return modelAndView;
    }

    @PostMapping("/user/makeTransfer")
    public ModelAndView makeTransfer(@AuthenticationPrincipal ProBuddyUserDetails user, @ModelAttribute("transferForm") ProBuddyTransferFormDTO transferFormDTO,
                                     BindingResult result, ModelAndView modelAndView){

        ProBuddyUser connectedBuddy = userService.findUserByEmail(transferFormDTO.getConnectedUserEmail());
        System.out.println("The receiver is " + connectedBuddy.getEmail());
        System.out.println(connectedBuddy.getEmail());
        if(connectedBuddy == null) {
            modelAndView.setViewName("transaction");
            result.reject("User with email" + transferFormDTO.getConnectedUserEmail() + "does not exist");
            return modelAndView;

        }

        String loggedInName = user.getUsername(); //get logged in username
        ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);
        System.out.println("The sender is " + loggedInUser.getEmail());
        System.out.println("The amount to transfer is " + transferFormDTO.getAmount());

        if (loggedInUser != null) {
            try {
                transactionsService.createTransactionByTransferMoney(loggedInUser.getId(), connectedBuddy.getId(),
                        transferFormDTO.getAmount(), transferFormDTO.getDescription());

                modelAndView.addObject("message", "Transaction successful");
                System.out.println("Transaction successful");
            }
            catch (InsufficientBalanceException e) {
                modelAndView.addObject("message", "Insufficient balance");
                System.out.println("Insufficient balance");
                e.printStackTrace();
            }
            List<ProBuddyTransactions> transactionsList = transactionsService.findAll();
            List<ProBuddyTransactionDTO> dtoList = new ArrayList<>();

            for (ProBuddyTransactions proBuddytransactions : transactionsList) {
                ProBuddyTransactionDTO dto = new ProBuddyTransactionDTO();
                dto.setSendingUserID(proBuddytransactions.getSender().getId());
                dto.setReceivingUserID(proBuddytransactions.getReceiver().getId());
                dto.setDescription(proBuddytransactions.getDescription());
                dto.setAmount(proBuddytransactions.getAmount());
                dto.setFee(proBuddytransactions.getFee());
                dtoList.add(dto);
            }
            if(dtoList.isEmpty()) {
                dtoList.add(new ProBuddyTransactionDTO(0.00, "Account is empty"));
            }
            modelAndView.setViewName("transaction");
            modelAndView.addObject("transferForm", new ProBuddyTransferFormDTO());
            modelAndView.addObject("dtoList", dtoList);
            modelAndView.addObject("dto", new ProBuddyTransactionDTO());

            List<ProBuddyUser> connectedUserList = contactsService.findConnectedUserByConnectorUser(connectedBuddy);

            modelAndView.setViewName("transaction");
            modelAndView.addObject("connectedUserList", connectedUserList);
        }
        else{
            modelAndView.setViewName("transaction");
        }
        return modelAndView;
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
        }
        else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }



    private void createTransactionHistory(List<ProBuddyTransactionDTO> dtoList,
                                          ProBuddyTransactions proBuddyTransactions) {

        ProBuddyTransactionDTO dto = new ProBuddyTransactionDTO();
        dto.setSendingUserID(proBuddyTransactions.getSender().getId());
        dto.setDescription(proBuddyTransactions.getDescription());
        dto.setAmount(proBuddyTransactions.getAmount());
        dtoList.add(dto);
    }
}

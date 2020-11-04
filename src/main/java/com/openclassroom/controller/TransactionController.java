package com.openclassroom.controller;

import com.openclassroom.model.ProBuddyTransactions;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.modelDTO.ProBuddyTransactionDTO;
import com.openclassroom.modelDTO.ProBuddyTransferFormDTO;
import com.openclassroom.service.ContactsService;
import com.openclassroom.service.TransactionsService;
import com.openclassroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
    private ContactsService contactsService;
    private TransactionsService transactionsService;

    @GetMapping("/user/transfer")
    public ModelAndView transfer(ModelAndView modelAndView){
        modelAndView.addObject("transferForm", new ProBuddyTransferFormDTO());
        modelAndView.setViewName("transaction");
        return modelAndView;
    }

    private double transactionFee = 0.05;

    @PostMapping("/user/makeTransfer")
    public ModelAndView makeTransfer(@ModelAttribute("TransferForm") ProBuddyTransferFormDTO transferFormDTO,
                                     ModelAndView modelAndView, BindingResult result){

        double fee = transferFormDTO.getAmount() * transactionFee;
        double totalCharge = transferFormDTO.getAmount() + fee;

        ProBuddyUser connectedBuddy = userService.findUserByEmail(transferFormDTO.getConnectedUserEmail());

        if(connectedBuddy == null) {
            modelAndView.setViewName("transaction");
            result.reject("User with email" + transferFormDTO.getConnectedUserEmail() + "does not exist");
            return modelAndView;

        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedInName = user.getUsername(); //get logged in username
        ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);
        if (loggedInUser != null) {
            List<ProBuddyUser> connectedUserList = contactsService.findConnectedUserByConnectorUser(connectedBuddy);
            List<ProBuddyTransactions> transactionsList = transactionsService.findAll();
            List<ProBuddyTransactionDTO> dtoList = new ArrayList<>();

            for (ProBuddyTransactions proBuddytransactions : transactionsList) {
                ProBuddyTransactionDTO dto = new ProBuddyTransactionDTO();
                dto.setSendingUserID(proBuddytransactions.getSender().getId());
                dto.setReceivingUserID(proBuddytransactions.getReceiver().getId());
                dto.setDescription(proBuddytransactions.getDescription());
                dto.setAmount(transferFormDTO.getAmount());
                dto.setFee(proBuddytransactions.getFee());
                dtoList.add(dto);
            }
            if(dtoList.isEmpty()) {
                dtoList.add(new ProBuddyTransactionDTO(0.00, "Account is empty"));
            }
            modelAndView.setViewName("transaction");
            modelAndView.addObject("connectedUserList", connectedUserList);
            modelAndView.addObject("dtoList", dtoList);
            modelAndView.addObject("dto", new ProBuddyTransactionDTO());
        }
        else{
            modelAndView.setViewName("transactionPage");
        }
        return modelAndView;
    }

    @GetMapping("/user/profile")
    public ModelAndView profile(ModelAndView modelAndView) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedInName = user.getUsername(); //get logged in username
        ProBuddyUser loggedInUser = userService.findUserByEmail(loggedInName);
        if (loggedInUser != null) {
            modelAndView.setViewName("profile");
            modelAndView.addObject("user", loggedInName);
            modelAndView.addObject("bankAccountNumber", loggedInUser.getAccount().getBankAccountNumber());
            modelAndView.addObject("balance", loggedInUser.getAccount().getBalance());
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

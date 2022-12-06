package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Transaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class JobService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;
    public void debiter(){
        Date date=new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        date.setMonth(date.getMonth()-1);
        List<Transaction> transactions = transactionRepository.findTransactionsByDateTransactionAfter(date);
        transactions.forEach(transaction -> {
            double montant=transaction.getMontant();
            Account account=transaction.getAccount();
            account.setSolde(account.getSolde()-montant);
            accountRepository.save(account);
        });

    }



}


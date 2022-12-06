package com.example.demo.mapper.implimentation;

import com.example.demo.dto.TransactionDto;
import com.example.demo.entity.Account;
import com.example.demo.entity.Transaction;
import com.example.demo.mapper.ITransactionMapper;
import com.example.demo.repository.AccountRepository;
import com.example.demo.mapper.ITransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper implements ITransactionMapper {
    @Autowired
    AccountRepository accountRepository;
    @Override
    public Transaction dtoToEntity(TransactionDto transactionDto) {
        Account account=accountRepository.findById(transactionDto.getIdAccount()).get();
        return Transaction.builder()
                .idTransaction(transactionDto.getIdTransaction())
                .account(account)
                .dateTransaction(transactionDto.getDateTransaction())
                .montant(transactionDto.getMontant())
                .build();
    }

    @Override
    public TransactionDto entityToDto(Transaction transaction) {

        /*
        return TransactionDto.builder().idTransaction(transaction.getIdTransaction())
                .dateTransaction(transaction.getDateTransaction())
                .idAccount(transaction.getAccount().getIdAccount())
                .montant(transaction.getMontant())
                .build();

         */
        return null;
    }
}

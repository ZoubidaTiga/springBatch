package com.example.demo.mapper;


import com.example.demo.dto.TransactionDto;
import com.example.demo.entity.Transaction;

public interface ITransactionMapper {

    Transaction dtoToEntity (TransactionDto transactionDto);
    TransactionDto entityToDto(Transaction transaction);

}

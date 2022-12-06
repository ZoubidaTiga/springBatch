package com.example.demo.config;

import com.example.demo.dto.TransactionDto;
import com.example.demo.entity.Transaction;
import com.example.demo.mapper.ITransactionMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionDtoProcessor implements ItemProcessor<TransactionDto,Transaction> {
    @Autowired
    ITransactionMapper iTransactionMapper;
    @Override
    public Transaction process(TransactionDto transactionDto) throws Exception {
        return  iTransactionMapper.dtoToEntity(transactionDto);
    }
}

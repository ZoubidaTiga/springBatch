package com.example.demo.dto;

import lombok.Data;

import java.util.Date;
@Data
public class TransactionDto {
    private int idTransaction;
    private double montant;
    private Date dateTransaction;
    private int idAccount;

}

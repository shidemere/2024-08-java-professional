package ru.otus.bank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Long id;
    private BigDecimal amount;

    private Integer type;

    private String number;

    private Long agreementId;


}

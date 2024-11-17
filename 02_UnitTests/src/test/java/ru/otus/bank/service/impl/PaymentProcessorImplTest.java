package ru.otus.bank.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.bank.entity.Account;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.AccountService;
import ru.otus.bank.service.exception.AccountException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentProcessorImplTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private PaymentProcessorImpl paymentProcessor;

    private Agreement sourceAgreement;
    private Agreement destinationAgreement;
    private Account sourceAccount;
    private Account destinationAccount;

    @BeforeEach
    void setUp() {

        sourceAgreement = new Agreement(1L, "Source Agreement");
        destinationAgreement = new Agreement(2L, "Destination Agreement");

        sourceAccount = new Account(1L, new BigDecimal("1000.00"), 101, "123456", sourceAgreement.getId());
        destinationAccount = new Account(2L, new BigDecimal("500.00"), 102, "654321", destinationAgreement.getId());
    }

    @Test
    void testMakeTransfer_Success() {
        when(accountService.getAccounts(sourceAgreement)).thenReturn(List.of(sourceAccount));
        when(accountService.getAccounts(destinationAgreement)).thenReturn(List.of(destinationAccount));
        when(accountService.makeTransfer(sourceAccount.getId(), destinationAccount.getId(), new BigDecimal("100")))
                .thenReturn(true);

        boolean result = paymentProcessor.makeTransfer(sourceAgreement, destinationAgreement, 101, 102, new BigDecimal("100"));

        assertTrue(result);
        verify(accountService).getAccounts(sourceAgreement);
        verify(accountService).getAccounts(destinationAgreement);
        verify(accountService).makeTransfer(sourceAccount.getId(), destinationAccount.getId(), new BigDecimal("100"));
    }

    @Test
    void testMakeTransfer_SourceAccountNotFound() {
        when(accountService.getAccounts(sourceAgreement)).thenReturn(Collections.emptyList());

        AccountException exception = assertThrows(AccountException.class, () ->
                paymentProcessor.makeTransfer(sourceAgreement, destinationAgreement, 101, 102, new BigDecimal("100"))
        );

        assertEquals("Account not found", exception.getMessage());
        verify(accountService).getAccounts(sourceAgreement);
    }

    @Test
    void testMakeTransfer_DestinationAccountNotFound() {
        when(accountService.getAccounts(sourceAgreement)).thenReturn(List.of(sourceAccount));
        when(accountService.getAccounts(destinationAgreement)).thenReturn(Collections.emptyList());

         assertThrows(
                AccountException.class, () ->
                paymentProcessor.makeTransfer(sourceAgreement, destinationAgreement, 101, 102, new BigDecimal("100"))
        );

    }

    @Test
    @DisplayName("Check correct transfer if input correct")
    void makeTransfer_whenAccountsExist_thenCorrectWork() {
        when(accountService.getAccounts(sourceAgreement)).thenReturn(List.of(sourceAccount));
        when(accountService.getAccounts(destinationAgreement)).thenReturn(List.of(destinationAccount));

        when(accountService.makeTransfer(anyLong(), anyLong(), any(BigDecimal.class))).thenReturn(true);

        var result = paymentProcessor.makeTransfer(sourceAgreement, destinationAgreement, 101, 102, BigDecimal.ONE);

        assertTrue(result);
    }

    @Test
    void testMakeTransferWithCommission_Success() {
        when(accountService.getAccounts(sourceAgreement)).thenReturn(List.of(sourceAccount));
        when(accountService.getAccounts(destinationAgreement)).thenReturn(List.of(destinationAccount));

        when(accountService.charge(anyLong(), any(BigDecimal.class))).thenReturn(true);

        when(accountService.makeTransfer(sourceAccount.getId(), destinationAccount.getId(), new BigDecimal("100")))
                .thenReturn(true);

        boolean result = paymentProcessor.makeTransferWithComission(sourceAgreement, destinationAgreement, 101, 102,
                new BigDecimal("100"), new BigDecimal("0.05"));

        assertTrue(result);
        verify(accountService).charge(anyLong(), any(BigDecimal.class));
        verify(accountService).makeTransfer(sourceAccount.getId(), destinationAccount.getId(), new BigDecimal("100"));
    }
}

package ru.otus.bank.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.bank.dao.AccountDao;
import ru.otus.bank.entity.Account;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.exception.AccountException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Agreement agreement;
    private Account account;
    private Account destinationAccount;

    @BeforeEach
    void setUp() {

        agreement = new Agreement(1L, "Test Agreement");

        account = new Account(1L, new BigDecimal("1000.00"), 101, "123456", agreement.getId());
        destinationAccount = new Account(2L, new BigDecimal("500.00"), 102, "654321", agreement.getId());
    }

    @Test
    void testAddAccount() {
        when(accountDao.save(any(Account.class))).thenReturn(account);

        Account result = accountService.addAccount(agreement, "123456", 101, new BigDecimal("1000.00"));

        assertNotNull(result);
        assertEquals(agreement.getId(), result.getAgreementId());
        assertEquals("123456", result.getNumber());
        assertEquals(101, result.getType());
        assertEquals(new BigDecimal("1000.00"), result.getAmount());
        verify(accountDao).save(any(Account.class));
    }

    @Test
    void testGetAccounts() {
        List<Account> accounts = List.of(account);
        when(accountDao.findAll()).thenReturn(accounts);

        List<Account> result = accountService.getAccounts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(account, result.get(0));
        verify(accountDao).findAll();
    }

    @Test
    void testCharge_Success() {
        when(accountDao.findById(1L)).thenReturn(Optional.of(account));

        boolean result = accountService.charge(1L, new BigDecimal("100.00"));

        assertTrue(result);
        assertEquals(new BigDecimal("900.00"), account.getAmount());
        verify(accountDao).findById(1L);
        verify(accountDao).save(account);
    }

    @Test
    void testCharge_AccountNotFound() {
        when(accountDao.findById(1L)).thenReturn(Optional.empty());

        AccountException exception = assertThrows(AccountException.class, () ->
                accountService.charge(1L, new BigDecimal("100.00"))
        );

        assertEquals("No source account", exception.getMessage());
        verify(accountDao).findById(1L);
    }

    @Test
    void testGetAccountsByAgreement() {
        List<Account> accounts = List.of(account);
        when(accountDao.findByAgreementId(agreement.getId())).thenReturn(accounts);

        List<Account> result = accountService.getAccounts(agreement);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(account, result.get(0));
        verify(accountDao).findByAgreementId(agreement.getId());
    }

    @Test
    void testMakeTransfer_Success() {
        when(accountDao.findById(1L)).thenReturn(Optional.of(account));
        when(accountDao.findById(2L)).thenReturn(Optional.of(destinationAccount));

        boolean result = accountService.makeTransfer(1L, 2L, new BigDecimal("100.00"));

        assertTrue(result);
        assertEquals(new BigDecimal("900.00"), account.getAmount());
        assertEquals(new BigDecimal("600.00"), destinationAccount.getAmount());
        verify(accountDao).findById(1L);
        verify(accountDao).findById(2L);
        verify(accountDao).save(account);
        verify(accountDao).save(destinationAccount);
    }

    @Test
    void testMakeTransfer_InsufficientFunds() {
        when(accountDao.findById(1L)).thenReturn(Optional.of(account));
        when(accountDao.findById(2L)).thenReturn(Optional.of(destinationAccount));

        boolean result = accountService.makeTransfer(1L, 2L, new BigDecimal("2000.00"));

        assertFalse(result);
        verify(accountDao).findById(1L);
        verify(accountDao, never()).save(any(Account.class));
    }

    @Test
    void testMakeTransfer_NegativeOrZeroAmount() {
        when(accountDao.findById(1L)).thenReturn(Optional.of(account));
        when(accountDao.findById(2L)).thenReturn(Optional.of(destinationAccount));

        boolean result = accountService.makeTransfer(1L, 2L, new BigDecimal("-100.00"));
        assertFalse(result);

        result = accountService.makeTransfer(1L, 2L, BigDecimal.ZERO);
        assertFalse(result);

    }

    @Test
    void testMakeTransfer_SourceAccountNotFound() {
        when(accountDao.findById(1L)).thenReturn(Optional.empty());

        AccountException exception = assertThrows(AccountException.class, () ->
                accountService.makeTransfer(1L, 2L, new BigDecimal("100.00"))
        );

        assertEquals("No source account", exception.getMessage());
        verify(accountDao).findById(1L);
    }

    @Test
    void testMakeTransfer_DestinationAccountNotFound() {
        when(accountDao.findById(1L)).thenReturn(Optional.of(account));
        when(accountDao.findById(2L)).thenReturn(Optional.empty());

        AccountException exception = assertThrows(AccountException.class, () ->
                accountService.makeTransfer(1L, 2L, new BigDecimal("100.00"))
        );

        assertEquals("No destination account", exception.getMessage());
        verify(accountDao).findById(1L);
        verify(accountDao).findById(2L);
    }
}

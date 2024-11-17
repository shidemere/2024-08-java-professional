package ru.otus.bank.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.bank.dao.AgreementDao;
import ru.otus.bank.entity.Agreement;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AgreementServiceImplTest {

    @Mock
    private AgreementDao dao;

    @InjectMocks
    AgreementServiceImpl agreementServiceImpl;


    @Test
    public void testAddAgreement() {
        Agreement expected = new Agreement(1L, "test");
        when(dao.save(any(Agreement.class))).thenReturn(expected);
        Agreement savedAgreement = agreementServiceImpl.addAgreement("test");
        assertEquals("test", savedAgreement.getName());
    }

    @Test
    public void testFindByName() {
        String name = "test";
        Agreement agreement = new Agreement();
        agreement.setId(10L);
        agreement.setName(name);

        when(dao.findByName(name)).thenReturn(
                Optional.of(agreement));

        Optional<Agreement> result = agreementServiceImpl.findByName(name);

        Assertions.assertTrue(result.isPresent());
        assertEquals(10, agreement.getId());
    }

    /**
     * Здесь испольуется Captor.
     * Эта такая штука, которая позволяет отслеживать состояние переданных в моки аргументов.
     * Сам метод capture означает "захват" и я при помощи captor.capture() указываю, где именно его нужно захватить.
     * После этого значение captor перестанет быть пустым и можно будет с ним как то поработать.
     */
    @Test
    public void testFindByNameWithCaptor() {
        String name = "test";
        Agreement agreement = new Agreement();
        agreement.setId(10L);
        agreement.setName(name);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        when(dao.findByName(captor.capture())).thenReturn(
                Optional.of(agreement));

        Optional<Agreement> result = agreementServiceImpl.findByName(name);

        assertEquals("test", captor.getValue());
        Assertions.assertTrue(result.isPresent());
        assertEquals(10, agreement.getId());
    }


}

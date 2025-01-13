package ru.otus.hw.repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import ru.otus.hw.config.JPAConfiguration;
import ru.otus.hw.model.Phone;

import java.util.Map;
import java.util.Objects;

public class PhoneRepositoryImpl implements PhoneRepository {
    @Override
    public Phone create(Phone phone) {
        EntityManager em = JPAConfiguration.getEntityManager();
        em.getTransaction().begin();
        em.persist(phone);
        em.getTransaction().commit();
        return phone;

    }

    @Override
    public Phone findById(long id) {
        EntityManager em = JPAConfiguration.getEntityManager();
        em.getTransaction().begin();

        EntityGraph<?> phoneClient = em.getEntityGraph("phone_client");

        Phone phone = em.find(Phone.class, id, Map.of("jakarta.persistence.fetchgraph", phoneClient));
        em.getTransaction().commit();
        return phone;

    }

    @Override
    public Phone update(Phone phone) {
        EntityManager em = JPAConfiguration.getEntityManager();
        em.getTransaction().begin();

        Phone findedPhone = findById(phone.getId());
        if (Objects.isNull(findedPhone)) {
            throw new EntityNotFoundException("Phone not found");
        }
        findedPhone.setNumber(phone.getNumber());
        findedPhone.setClient(phone.getClient());
        em.merge(findedPhone);
        em.getTransaction().commit();
        return findedPhone;

    }

    @Override
    public void delete(Phone phone) {
        EntityManager em = JPAConfiguration.getEntityManager();
        em.getTransaction().begin();
        Phone findedPhone = em.find(Phone.class, phone.getId());
        if (findedPhone != null) {
            em.remove(findedPhone);
        }
        em.getTransaction().commit();
    }
}

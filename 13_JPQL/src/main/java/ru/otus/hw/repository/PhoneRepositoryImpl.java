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
        try {
            em.getTransaction().begin();
            em.persist(phone);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return phone;

    }

    @Override
    public Phone findById(long id) {
        EntityManager em = JPAConfiguration.getEntityManager();
        Phone phone = null;
        try {
            em.getTransaction().begin();

            EntityGraph<?> phoneClient = em.getEntityGraph("phone_client");

            phone = em.find(Phone.class, id, Map.of("jakarta.persistence.fetchgraph", phoneClient));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return phone;

    }

    @Override
    public Phone update(Phone phone) {
        EntityManager em = JPAConfiguration.getEntityManager();
        Phone findedPhone = null;
        try {
            em.getTransaction().begin();

            findedPhone = findById(phone.getId());
            if (Objects.isNull(findedPhone)) {
                throw new EntityNotFoundException("Phone not found");
            }
            findedPhone.setNumber(phone.getNumber());
            findedPhone.setClient(phone.getClient());
            em.merge(findedPhone);
            em.getTransaction().commit();
        } catch (EntityNotFoundException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return findedPhone;

    }

    @Override
    public void delete(Phone phone) {
        EntityManager em = JPAConfiguration.getEntityManager();
        try {
            em.getTransaction().begin();
            Phone findedPhone = em.find(Phone.class, phone.getId());
            if (findedPhone != null) {
                em.remove(findedPhone);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}

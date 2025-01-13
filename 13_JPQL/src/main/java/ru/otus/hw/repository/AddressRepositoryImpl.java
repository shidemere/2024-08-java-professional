package ru.otus.hw.repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import ru.otus.hw.config.JPAConfiguration;
import ru.otus.hw.model.Address;

import java.util.Map;
import java.util.Objects;

public class AddressRepositoryImpl implements AddressRepository {
    @Override
    public Address create(Address address) {
        EntityManager entityManager = JPAConfiguration.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(address);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
        return address;

    }

    @Override
    public Address findById(long id) {
        EntityManager entityManager = JPAConfiguration.getEntityManager();
        entityManager.getTransaction().begin();

        EntityGraph<?> addressClient = entityManager.getEntityGraph("address_client");

        Address address = entityManager.find(Address.class, id, Map.of("jakarta.persistence.fetchgraph", addressClient));
        entityManager.getTransaction().commit();
        return address;

    }

    @Override
    public Address update(Address address) {
        EntityManager em = JPAConfiguration.getEntityManager();
        Address findedAddress = null;
        try {
            em.getTransaction().begin();
            findedAddress = findById(address.getId());
            if (Objects.isNull(findedAddress)) {
                throw new EntityNotFoundException("Address not found");
            }
            findedAddress.setStreet(address.getStreet());
            findedAddress.setClient(address.getClient());
            em.merge(findedAddress);
            em.getTransaction().commit();
        } catch (EntityNotFoundException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return findedAddress;

    }

    @Override
    public void delete(Address address) {
        EntityManager em = JPAConfiguration.getEntityManager();
        try {
            em.getTransaction().begin();
            Address findedAddress = em.find(Address.class, address.getId());
            if (findedAddress != null) {
                em.remove(findedAddress);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

    }
}

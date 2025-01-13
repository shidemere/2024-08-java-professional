package ru.otus.hw.repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import ru.otus.hw.config.JPAConfiguration;
import ru.otus.hw.model.Client;

import java.util.Map;
import java.util.Objects;

public class ClientRepositoryImpl implements ClientRepository {
    @Override
    public Client create(Client client) {
        EntityManager em = JPAConfiguration.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(client);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return client;

    }

    @Override
    public Client findById(long id) {
        EntityManager em = JPAConfiguration.getEntityManager();
        Client client = null;
        try {
            em.getTransaction().begin();

            EntityGraph<?> clientAddressPhone = em.getEntityGraph("client_address_phone");

            client = em.find(Client.class, id, Map.of("jakarta.persistence.fetchgraph", clientAddressPhone));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return client;

    }

    @Override
    public Client update(Client client) {
        EntityManager em = JPAConfiguration.getEntityManager();
        Client findedClient = null;
        try {
            em.getTransaction().begin();
            findedClient = findById(client.getId());
            if (Objects.isNull(findedClient)) {
                throw new EntityNotFoundException("Client not found");
            }
            findedClient.setName(client.getName());
            findedClient.setAddress(client.getAddress());
            findedClient.setPhones(client.getPhones());
            em.merge(findedClient);
            em.getTransaction().commit();
        } catch (EntityNotFoundException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return findedClient;

    }

    @Override
    public void delete(Client client) {
        EntityManager em = JPAConfiguration.getEntityManager();
        try {
            em.getTransaction().begin();
        /*
            Вопрос преподавателю: если удалять сущность при помощи em.remove, то вне зависимости от CascadeType и orphanRemoval
            будет происходить удаление связанных сущностей по одному.
            Я пробовал использовать JQPL, но возникала ошибка персистентности. Подробнее в README к модулю.
            Есть ли возможность сделать это как то лучше или лаконичнее?
         */
            Client findedClient = em.find(Client.class, client.getId());
            if (findedClient != null) {
                em.remove(findedClient);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.close();
        } finally {
            em.close();
        }

    }
}

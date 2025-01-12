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
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
        return client;

    }

    @Override
    public Client findById(long id) {
        EntityManager em = JPAConfiguration.getEntityManager();
        em.getTransaction().begin();

        EntityGraph<?> clientAddressPhone = em.getEntityGraph("client_address_phone");

        Client client = em.find(Client.class, id, Map.of("jakarta.persistence.fetchgraph", clientAddressPhone));
        em.getTransaction().commit();
        return client;

    }

    @Override
    public Client update(Client client) {
        EntityManager em = JPAConfiguration.getEntityManager();
        em.getTransaction().begin();
        Client findedClient = findById(client.getId());
        if (Objects.isNull(findedClient)) {
            throw new EntityNotFoundException("Client not found");
        }
        findedClient.setName(client.getName());
        findedClient.setAddress(client.getAddress());
        findedClient.setPhones(client.getPhones());
        em.merge(findedClient);
        em.getTransaction().commit();
        return findedClient;

    }

    @Override
    public void delete(Client client) {
        EntityManager em = JPAConfiguration.getEntityManager();
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

    }
}

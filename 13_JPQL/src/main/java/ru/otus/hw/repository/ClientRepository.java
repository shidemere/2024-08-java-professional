package ru.otus.hw.repository;

import ru.otus.hw.model.Client;

public interface ClientRepository {
    Client create(Client client);
    Client findById(long id);
    Client update(Client client);
    void delete(Client client);
}

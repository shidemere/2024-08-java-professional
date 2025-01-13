package ru.otus.hw.repository;

import ru.otus.hw.model.Address;

public interface AddressRepository {
    Address create(Address address);
    Address findById(long id);
    Address update(Address address);
    void delete(Address address);
}

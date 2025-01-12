package ru.otus.hw.repository;

import ru.otus.hw.model.Phone;

public interface PhoneRepository {
    Phone create(Phone phone);
    Phone findById(long id);
    Phone update(Phone phone);
    void delete(Phone phone);
}

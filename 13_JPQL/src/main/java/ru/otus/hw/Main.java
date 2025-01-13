package ru.otus.hw;

import ru.otus.hw.config.JPAConfiguration;
import ru.otus.hw.model.Address;
import ru.otus.hw.model.Client;
import ru.otus.hw.model.Phone;
import ru.otus.hw.repository.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClientRepository clientRepository = new ClientRepositoryImpl();

        System.out.println("Сохранение");
        List<Phone> phones = List.of(
                new Phone("89991231212"),
                new Phone("89991231313"),
                new Phone("89991231414")
        );

        Address address = new Address("Ленина 5");
        Client client = Client.builder()
                .name("Оптимус Прайм")
                .address(address)
                .phones(phones)
                .build();
        client.getAddress().setClient(client);
        client.getPhones().forEach(phone -> phone.setClient(client));
        clientRepository.create(client);

        System.out.println("Чтение");

        Client findedById = clientRepository.findById(1L);
        System.out.println(findedById);

        System.out.println("Обновление");
        client.setName("Бамблби");
        client.setAddress(new Address("Планета Нибиру"));
        Phone phone = client.getPhones().get(0);
        phone.setNumber("7777777");
        clientRepository.update(client);

        System.out.println("Удаление");
        clientRepository.delete(findedById);

        JPAConfiguration.close();
    }
}
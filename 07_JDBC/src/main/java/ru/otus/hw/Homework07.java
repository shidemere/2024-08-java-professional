package ru.otus.hw;

import ru.otus.hw.config.DataSource;
import ru.otus.hw.repository.AbstractRepository;
import ru.otus.hw.migration.DbMigrator;
import ru.otus.hw.model.User;

import java.util.List;
import java.util.Optional;

public class Homework07 {
    public static void main(String[] args) {
        DataSource dataSource = null;
        try {
            System.out.println("Сервер чата запущен");
            dataSource = new DataSource("jdbc:postgresql://localhost:5432/main");
            DbMigrator migrator = new DbMigrator(dataSource);
            migrator.migrate();


            AbstractRepository<User> usersRepository = new AbstractRepository<>(dataSource, User.class);
            List<User> all = usersRepository.findAll();
            System.out.println("------------------------ALL USERS------------------------------------");
            System.out.println(all);
            System.out.println("------------------------ALL USERS------------------------------------");

            usersRepository.deleteById(24L);
            System.out.println("-------------------------UPDATE---------------------------------------");
            User user = new User(1L, "R", "S", "S");
            usersRepository.update(user);
            System.out.println("-------------------------UPDATE---------------------------------------");
            System.out.println("-------------------------FIND BY ID-----------------------------------");
            Optional<User> byId = usersRepository.findById(1L);
            System.out.println(byId.orElse(null));
            System.out.println("-------------------------FIND BY ID-----------------------------------");


            usersRepository.save(new User(null, "X", "X", "X"));
        } finally {
            if (dataSource != null) {
                dataSource.close();
            }
            System.out.println("Сервер чата завершил свою работу");
        }
    }
}

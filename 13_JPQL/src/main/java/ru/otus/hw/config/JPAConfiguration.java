package ru.otus.hw.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JPAConfiguration {

    private static EntityManagerFactory entityManagerFactory;

    private static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory != null) {
            return entityManagerFactory;
        }
        try {
            log.info("Creating EntityManagerFactory...");

            entityManagerFactory = Persistence.createEntityManagerFactory("postgres-unit");

            log.info("EntityManagerFactory created successfully");
            return entityManagerFactory;
        } catch (Throwable ex) {
            log.error("Initial EntityManagerFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static void close() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
            log.info("EntityManagerFactory closed.");
        }
    }
}

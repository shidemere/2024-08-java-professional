package ru.otus.hw.configuration;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.hw.model.Customer;
import ru.otus.hw.model.Item;

import java.util.Properties;

@Slf4j
public class HibernateConfiguration {

    private static SessionFactory sessionFactory;

    private static SessionFactory getSessionFactory() {
        if (sessionFactory != null) {
            return sessionFactory;
        }
        try {
            Configuration configuration = getConfiguration();

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            log.info("Hibernate Java Config serviceRegistry created");

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        }
        catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed.",  ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }

    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();

        Properties props = new Properties();

        props.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.format_sql", "true");
        props.put("hibernate.use_sql_comments", "true");

        props.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres?useUnicode=true&characterEncoding=UTF-8");
        props.put("hibernate.connection.username", "username");
        props.put("hibernate.connection.password", "password");
        props.put("hibernate.current_session_context_class", "thread");
        props.put("hibernate.hbm2ddl.auto", "update");

        // Добавление аннотированных классов
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Item.class);

        configuration.setProperties(props);
        return configuration;
    }

}

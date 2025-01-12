package ru.otus.hw.repository;

import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import ru.otus.hw.configuration.HibernateConfiguration;
import ru.otus.hw.model.Customer;
import ru.otus.hw.model.Item;

import java.util.List;
import java.util.Map;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public List<Item> getItemsByCustomerId(long customerId) {
        try (Session session = HibernateConfiguration.getSession()) {
            session.beginTransaction();
            RootGraph<?> rootGraph = session.getEntityGraph("customer_items");

            Customer customer = session.find(Customer.class, customerId,
                    Map.of(GraphSemantic.LOAD.getJakartaHintName(), rootGraph));
            session.getTransaction().commit();

            return customer.getItems();
        } catch (RuntimeException e) {
            return List.of();
        }
    }

    @Override
    public void remove(Customer customer) {
        try (Session session = HibernateConfiguration.getSession()) {
            session.beginTransaction();
            session.remove(customer);
            session.getTransaction().commit();
        }
    }
}

package ru.otus.hw.repository;

import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import ru.otus.hw.configuration.HibernateConfiguration;
import ru.otus.hw.model.Customer;
import ru.otus.hw.model.Item;

import java.util.Map;

public class ItemRepositoryImpl implements ItemRepository {
    @Override
    public Customer findCustomersByItemId(long itemId) {
        try (Session session = HibernateConfiguration.getSession()) {
            session.beginTransaction();


            RootGraph<?> rootGraph = session.getEntityGraph("item_customer");

            Item item = session.find(Item.class, itemId,
                    Map.of(GraphSemantic.LOAD.getJakartaHintName(), rootGraph));


            session.getTransaction().commit();
            return item.getCustomer();
        }
    }

    @Override
    public void remove(Item item) {
        try (Session session = HibernateConfiguration.getSession()) {
            session.beginTransaction();
            session.remove(item);
            session.getTransaction().commit();
        }
    }
}

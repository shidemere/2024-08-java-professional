package ru.otus.hw.migration;

import org.hibernate.Session;
import ru.otus.hw.configuration.HibernateConfiguration;


/**
 * Наполняет H2 данными.
 */
public class H2Migration {

    private final static String addCustomers = """
            INSERT INTO customer (name) VALUES\s
            ('Александр Македонский'),
            ('Генрих V'),
            ('Юлий Цезарь'),
            ('Наполеон Бонапарт'),
            ('Суворов Александр Васильевич');
            """;

    private final static String addItems = """
            INSERT INTO item (name, price, customer_id) VALUES
            ('Македонская фаланга', 5000.00, 1),
            ('Длинный лук', 300.00, 2),
            ('Гладиус', 150.00, 3),
            ('Пика', 200.00, 4),
            ('Штыковой меч', 400.00, 5),
            ('Топор викингов', 250.00, 1),
            ('Римский щит', 120.00, 3),
            ('Кавалерийский меч', 350.00, 4),
            ('Доспехи легионера', 1000.00, 2),
            ('Копье', 180.00, 5);
            """;

    public static void databaseInit() {
        try (Session session = HibernateConfiguration.getSession()) {
            session.beginTransaction();

                session.createNativeMutationQuery(addCustomers).executeUpdate();
                session.createNativeMutationQuery(addItems).executeUpdate();
            session.getTransaction().commit();
        }
    }
}

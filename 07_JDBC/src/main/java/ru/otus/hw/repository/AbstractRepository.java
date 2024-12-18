package ru.otus.hw.repository;

import ru.otus.hw.annotation.RepositoryField;
import ru.otus.hw.annotation.RepositoryIdField;
import ru.otus.hw.annotation.RepositoryTable;
import ru.otus.hw.config.DataSource;
import ru.otus.hw.exception.ORMException;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AbstractRepository<T> {
    private final DataSource dataSource;
    private PreparedStatement psInsert;
    private List<Field> cachedFieldsForInsert;
    private List<Field> cachedFieldsForRead;
    private String tableName;
    Class<T> cls;
    private final List<Class<?>> typeListForGetCorrectConstructor = new ArrayList<>();
    private final Map<String, String> columnNameTypeMap = new LinkedHashMap<>();


    public AbstractRepository(DataSource dataSource, Class<T> cls) {
        this.dataSource = dataSource;

        this.cls = cls;

        checkCorrect();
        prepare();
    }

    private void prepare() {
        // Проверка наличия аннотаций над полями
        cachedFieldsForInsert = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RepositoryField.class))
                .filter(f -> !f.isAnnotationPresent(RepositoryIdField.class))
                .collect(Collectors.toList());

        for (Field f : cachedFieldsForInsert) {
            f.setAccessible(true);
        }

        // Получаем все поля.
        cachedFieldsForRead = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RepositoryField.class) || f.isAnnotationPresent(RepositoryIdField.class))
                .collect(Collectors.toList());

        for (Field f : cachedFieldsForRead) {
            f.setAccessible(true);
        }
    }

    /**
     * Различные проверки на класс.
     */
    public void checkCorrect() {
        // Проверка что класс помечен аннотацией RepositoryTable, чтобы с ним можно было работать.
        if (cls.getAnnotation(RepositoryTable.class) == null) {
            throw new ORMException("Класс не помечен аннотацией @RepositoryTable");
        }
        // Получение имени таблицы.
        tableName = cls.getAnnotation(RepositoryTable.class).title();

        // Провекра, что все поля класса помечены специальными аннотациями.
        Optional<Field> optional = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(RepositoryField.class))
                .filter(f -> !f.isAnnotationPresent(RepositoryIdField.class))
                .findFirst();

        if (optional.isPresent()) {
            throw new ORMException("В классе есть непомеченные аннотациями @RepositoryField и RepositoryIdField поля.");
        }

    }

    /**
     * Поиск всех записей.
     */
    public List<T> findAll() {
        String sql = String.format("select * from %s", tableName);
        prepareForRead();
        List<T> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Object[] values = convertFieldsFromResultSet(resultSet);
                result.add(cls.getConstructor(typeListForGetCorrectConstructor.toArray(new Class<?>[0])).newInstance(values));
            }
        } catch (SQLException e) {
            throw new ORMException("Ошибка при получении всех записей: " + e.getMessage());
        } catch (ReflectiveOperationException e) {
            throw new ORMException("Преобразование закончилось с ошибкой: " + e.getMessage());
        }
        return result;
    }

    /**
     * Мне указывают ID.
     * Я должен по этому ID найти запись из таблицы.
     */
    public Optional<T> findById(Object id) {
        String sql = String.format("select * from %s where id = ?", tableName);

        prepareForRead();

        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql)) {
            if (id.getClass() == Long.class) {
                preparedStatement.setLong(1, (long) id);
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Object[] values = convertFieldsFromResultSet(resultSet);
                try {
                    // Создаём объект, вызывая конструктор, передавая в него типы аргументов, а затем и значения аргументов полученные из ResultSet.
                    return Optional.of(cls.getConstructor(typeListForGetCorrectConstructor.toArray(new Class<?>[0])).newInstance(values));
                } catch (ReflectiveOperationException e) {
                    throw new ORMException("Чтение записи из базы закончилось с ошибкой:" + e.getMessage());
                }
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new ORMException("По данному ID не найдено записей: " + e.getMessage());
        }
    }

    /**
     * Подготовка к чтению.
     * Добавляем в специальную коллекцию для получения нужного конструктора.
     */
    private void prepareForRead() {
        // Создаем мапу имя_поля=тип_поля полей для вызова конструктора
        if (typeListForGetCorrectConstructor.isEmpty()) {
            for (Field field : cachedFieldsForRead) {
                typeListForGetCorrectConstructor.add(field.getType());
            }
        }

    }

    /**
     * В зависимости от типа поля в классе - получает нужное поле из ResultSet.
     * Таким образом можно получить массив типизированных значений, чтобы передать их в конструктор, когда создаётся экземпляр объекта.
     * Работает с ограниченным количеством типов.
     */
    private Object[] convertFieldsFromResultSet(ResultSet resultSet) throws SQLException {
        Object[] values = new Object[cachedFieldsForRead.size()];
        for (int i = 0; i < typeListForGetCorrectConstructor.size(); i++) {
            Class<?> aClass = typeListForGetCorrectConstructor.get(i);
            // +1 потому что в PreparedStatement нужно ставить с единицы, а не с нуля.
            // Работает с Java 17 и выше
            switch (aClass.getSimpleName()) {
                case "int", "Integer" -> values[i] = resultSet.getInt(i + 1);
                case "byte", "Byte" -> values[i] = resultSet.getByte(i + 1);
                case "long", "Long" -> values[i] = resultSet.getLong(i + 1);
                case "String" -> values[i] = resultSet.getString(i + 1);
                case "boolean", "Boolean" -> values[i] = resultSet.getBoolean(i + 1);
                default -> throw new ORMException("Unsupported field type: " + aClass.getSimpleName());
            }
        }
        return values;
    }

    public void save(T entity) {
        prepareInsert();
        try {
            for (int i = 0; i < cachedFieldsForInsert.size(); i++) {
                psInsert.setObject(i + 1, cachedFieldsForInsert.get(i).get(entity));
            }
            psInsert.executeUpdate();
        } catch (Exception e) {
            throw new ORMException("Что-то пошло не так при сохранении: " + e.getMessage());
        }
    }

    private void prepareInsert() {


        /*
        Заполняем мапу название_поля=имя_заданное_в_анотации.
        Если имя заданное в аннотации = "" то ложим дефолтное имя поля.
         */
        for (Field field : cachedFieldsForInsert) {
            if (!"".equals(field.getAnnotation(RepositoryField.class).name())) {
                columnNameTypeMap.put(field.getName(), field.getAnnotation(RepositoryField.class).name());
            } else {
                columnNameTypeMap.put(field.getName(), field.getName());
            }
        }

        tableName = cls.getAnnotation(RepositoryTable.class).title();
        StringBuilder query = new StringBuilder("insert into ");
        query.append(tableName).append(" (");
        // 'insert into users ('

        for (Field f : cachedFieldsForInsert) {
            query.append(columnNameTypeMap.get(f.getName())).append(", ");
        }
        // 'insert into users (login, password, nickname, '
        query.setLength(query.length() - 2);
        query.append(") values (");
        // 'insert into users (login, password, nickname) values ('
        for (Field f : cachedFieldsForInsert) {
            query.append("?, ");
        }
        query.setLength(query.length() - 2);
        query.append(");");
        // 'insert into users (login, password, nickname) values (?, ?, ?);'
        try {
            psInsert = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ORMException("Не удалось проинициализировать репозиторий для класса " + cls.getName());
        }
    }

    public void update(T entity) {
        Optional<Field> idField = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RepositoryIdField.class))
                .findFirst();

        if (idField.isEmpty()) {
            throw new ORMException("Над идентификатором не стоит аннотация RepositoryIdField.");
        }
        idField.get().setAccessible(true);

        Object idValue;
        try {
            idValue = idField.get().get(entity);
            if (idValue == null) {
                throw new ORMException("Поле идентификатора не должно быть null.");
            }
        } catch (IllegalAccessException e) {
            throw new ORMException("Ошибка доступа к значению идентификатора: " + e.getMessage());
        }

        Optional<T> targetObject = findById(idValue);
        if (targetObject.isEmpty()) {
            throw new ORMException("Не найдена сущность для обновления");
        }


        List<Field> fieldsToUpdate = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RepositoryField.class)) // Поля с @RepositoryField
                .toList();


        if (fieldsToUpdate.isEmpty()) {
            throw new ORMException("Нет полей для обновления.");
        }

        /*
        Заполняем мапу название_поля=имя_заданное_в_анотации.
        Если имя заданное в аннотации = "" то ложим дефолтное имя поля.
         */
        Map<String, String> fieldNameToColumn = new HashMap<>();
        for (Field field : fieldsToUpdate) {
            if (!"".equals(field.getAnnotation(RepositoryField.class).name())) {
                fieldNameToColumn.put(field.getName(), field.getAnnotation(RepositoryField.class).name());
            } else {
                fieldNameToColumn.put(field.getName(), field.getName());
            }
        }

        StringBuilder queryBuilder = new StringBuilder();
        // update users set
        queryBuilder.append("UPDATE ").append(tableName).append(" SET ");

        // update users set name = ?, password = ?, nickname = ?,
        for (Field field : fieldsToUpdate) {

            queryBuilder.append(fieldNameToColumn.get(field.getName())).append(" = ?, ");
        }
        // update users set name = ?, password = ?, nickname = ?
        queryBuilder.setLength(queryBuilder.length() - 2);
        // update users set name = ?, password = ?, nickname = ? where id = ?
        queryBuilder.append(" WHERE ").append(idField.get().getName()).append(" = ?");

        String updateQuery = queryBuilder.toString();

        try (PreparedStatement psUpdate = dataSource.getConnection().prepareStatement(updateQuery)) {
            // Заполняем значения для полей
            for (int i = 0; i < fieldsToUpdate.size(); i++) {
                Field field = fieldsToUpdate.get(i);
                field.setAccessible(true);
                psUpdate.setObject(i + 1, field.get(entity));
            }

            // Добавляем значение ID в конец
            psUpdate.setObject(fieldsToUpdate.size() + 1, idValue);

            // Выполняем запрос
            int rowsUpdated = psUpdate.executeUpdate();
            if (rowsUpdated == 0) {
                throw new ORMException("Запись с ID " + idValue + " не найдена для обновления.");
            }
        } catch (SQLException | IllegalAccessException e) {
            throw new ORMException("Ошибка при выполнении обновления: " + e.getMessage());
        }
    }

    public void deleteById(Object id) {
        String sql = String.format("delete from %s where id = ?", tableName);

        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql)) {
            if (id.getClass() == Long.class) {
                preparedStatement.setLong(1, (long) id);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ORMException("По данному ID не найдено записей.");
        }
    }
}

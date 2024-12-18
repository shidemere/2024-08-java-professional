package ru.otus.hw.migration;

import ru.otus.hw.config.DataSource;
import ru.otus.hw.exception.ORMException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Objects;

/**
 * Занимается миграцией баз данных.
 * Считывает из ресурсов файл init.sql и исполняет его команды.
 * Так же отчёт о выполнении заносит в специальную, служебную таблицу с историей миграций.
 */
public class DbMigrator {
    private DataSource dataSource;
    private String initSQLFilename = "init.sql";

    public DbMigrator(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    /**
     * Может быть 3 кейса:
     * 1. Данные в таблице миграции есть. Значение поля actual для определенного файла миграции true -> ничего не делаем.
     * 2. Данные в таблице миграции есть. Значение поля actual для определенного файла миграции false -> накатываем миграцию и обновляем значение в таблице миграции.
     * 3. Данных в таблице миграции нет. -> накатываем миграцию и добавляем новую запись в таблицу миграции.
     */
    public void migrate() {
        // Создание таблицы с историей миграции
        createMigrationHistoryTable();

        String initApplicationMigration = readFileFromResources(initSQLFilename);
        try (Connection connection = dataSource.getConnection()) {
            // Узнаём в истории миграции, была ли выполнена миграция из файлика с именем (?) и актуальна ли она.
            ResultSet resultSet = checkIfExistInMigrationHistoryTable(connection);
            // кейс 1 и 2
            if (resultSet.next()) {
                boolean actual = resultSet.getBoolean("actual");
                // кейс 2
                if (!actual) {
                    Statement stmt = connection.createStatement();
                    stmt.execute(initApplicationMigration);
                    PreparedStatement statement = connection.prepareStatement("insert into migration_history (filename, actual) VALUES (?, ?)");
                    statement.setString(1, initSQLFilename);
                    statement.setBoolean(2, true);
                    statement.executeUpdate();
                }
                // кейс 1
            } else {
                // кейс 3
                createInitTableWithInsertInHistory(connection, initApplicationMigration);
            }
        } catch (SQLException e) {
            throw new ORMException(String.format("Error initializing database: incorrect command={%s}", initApplicationMigration));
        }
    }

    private void createInitTableWithInsertInHistory(Connection connection, String initApplicationMigration) throws SQLException {
        connection.prepareStatement(initApplicationMigration).executeUpdate();
        PreparedStatement statement = connection.prepareStatement("insert into migration_history (filename, actual) VALUES (?, ?)");
        statement.setString(1, initSQLFilename);
        statement.setBoolean(2, true);
        statement.executeUpdate();
    }

    private ResultSet checkIfExistInMigrationHistoryTable(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from migration_history where filename = (?)");
        preparedStatement.setString(1, initSQLFilename);
        return preparedStatement.executeQuery();
    }

    private void createMigrationHistoryTable() {
        String migrationHistoryCreateSQLCommand = createMigrationHistory();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(migrationHistoryCreateSQLCommand);
        } catch (SQLException e) {

            throw new ORMException("Error in creating migration_history table.");
        }
    }


    // Предоставляет SQL скрипт для создания таблицы с историей миграций
    private String createMigrationHistory() {
        return """
                create table if not exists migration_history (
                id bigserial primary key,
                filename varchar(255) not null,
                actual boolean not null,
                UNIQUE (filename, actual)
                )
                """;
    }

    // Метод для чтения файла из ресурсов
    private static String readFileFromResources(String fileName) {
        try (InputStream inputStream = DbMigrator.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            throw new ORMException(String.format("Error initializing database: incorrect file \"%s\" ) ", fileName));
        }
    }
}

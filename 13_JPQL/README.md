## Язык запросов JPQL // ДЗ 13

### Требования 

- [x] Создать три класса модели: Телефон, адрес и клиент.
- [x] Реализовать CRUD операции для этих классов модели
- [x] Чтение и сохранение сущностей, связанных между собой должно быть каскадным

### Заметки
Удаление связанных сущностей происходит несколькими запросами. 
Например если у клиента три телефона, и мы удаляем клиента - будет выполнено три запроса. 
Я пробовал пофиксить это через JPQL, но там возникала непонятная ошибка отношений между объектами. 

```java
Exception in thread "main" jakarta.persistence.PersistenceException: Converting org.hibernate.exception.SQLGrammarException to JPA PersistenceException : JDBC exception executing SQL [delete from public.address where c1_0.id=?]
	at org.hibernate.internal.ExceptionConverterImpl.convert(ExceptionConverterImpl.java:165)
	at org.hibernate.internal.ExceptionConverterImpl.convert(ExceptionConverterImpl.java:175)
	at org.hibernate.query.sqm.internal.QuerySqmImpl.executeUpdate(QuerySqmImpl.java:709)
	at ru.otus.hw.repository.ClientRepositoryImpl.delete(ClientRepositoryImpl.java:57)
	at ru.otus.hw.Main.main(Main.java:47)
Caused by: org.hibernate.exception.SQLGrammarException: JDBC exception executing SQL [delete from public.address where c1_0.id=?]
	at org.hibernate.exception.internal.SQLStateConversionDelegate.convert(SQLStateConversionDelegate.java:89)
	at org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:56)
	at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:109)
	at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:95)
	at org.hibernate.sql.exec.internal.StandardJdbcMutationExecutor.execute(StandardJdbcMutationExecutor.java:97)
	at org.hibernate.query.sqm.internal.SimpleDeleteQueryPlan.executeUpdate(SimpleDeleteQueryPlan.java:179)
	at org.hibernate.query.sqm.internal.QuerySqmImpl.doExecuteUpdate(QuerySqmImpl.java:728)
	at org.hibernate.query.sqm.internal.QuerySqmImpl.executeUpdate(QuerySqmImpl.java:698)
	... 2 more
Caused by: org.postgresql.util.PSQLException: ERROR: missing FROM-clause entry for table "c1_0"
  Позиция: 34
	at org.postgresql.core.v3.QueryExecutorImpl.receiveErrorResponse(QueryExecutorImpl.java:2676)
	at org.postgresql.core.v3.QueryExecutorImpl.processResults(QueryExecutorImpl.java:2366)
	at org.postgresql.core.v3.QueryExecutorImpl.execute(QueryExecutorImpl.java:356)
	at org.postgresql.jdbc.PgStatement.executeInternal(PgStatement.java:496)
	at org.postgresql.jdbc.PgStatement.execute(PgStatement.java:413)
	at org.postgresql.jdbc.PgPreparedStatement.executeWithFlags(PgPreparedStatement.java:190)
	at org.postgresql.jdbc.PgPreparedStatement.executeUpdate(PgPreparedStatement.java:152)
	at org.hibernate.sql.exec.internal.StandardJdbcMutationExecutor.execute(StandardJdbcMutationExecutor.java:84)
```
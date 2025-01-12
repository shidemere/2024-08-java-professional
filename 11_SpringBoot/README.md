## Spring Boot. Часть 1 // ДЗ 

### Требования
- [x] Создайте сервис, который хранит продукты (id, title, price)
- [x] Сервис должен быть разделен на стандартный набор слоев: контроллеры, сервисы, репозитории
- [x] Хранение продуктов орагнизуйте через List в отдельном компоненте
- [x] Сервис должен давать возможность: запросить все продукты, запросить продукт по id, создавть новый продукт

### Заметки

Почему то `@PathVariable` не работал как надо. Проблема была в самом Gradle, его надо было дополнительно конфигурировать настройкой

```groovy
tasks.withType(JavaCompile).configureEach {
    options.compilerArgs << '-parameters'
}
```

Это связано (как я понимаю) с тем, что Spring не может получить данные параметра через рефликсию. 
А эта настройка добавляет возможность сохранять информацию о типа в байткоде.
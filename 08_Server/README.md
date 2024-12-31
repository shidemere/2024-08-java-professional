## Подробно про протокол HTTP // ДЗ

### Требования

-[x] Обработка запросов происходит в параллельных потоках
-[x] Запросы должны быть распарсены в объект типа HttpRequest с полями: http-метод, uri, заголовки, параметры запроса
-[x] При парсинге не должно содаваться лишних объектов (в т.ч. нельзя постоянно пересоздавать byte[] buffer если будете его использовать) (один из самых важных пунктов задания)
-[x] Настройки сервера (порт, размера пула потоков) должны лежать в отдельном .properties файле
-[x] Должна быть возможность принимать запросы любого размера (в разумных пределах, пусть будет до 5 мб)
-[ ] При отправке запроса GET /shutdown на сервер он должен остановить свою работу
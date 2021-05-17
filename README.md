# CurrencyWatcher

Приложение предназначено для получения и отображения информации о курсе валют.

### Основные функции:
- Получение курса валют по отношению к российскому рублю за последний месяц
- Выбор валюты из выпадающего списка для отображения
- Режим отслеживания курса валют, оповещение о превышении установленного порога (push-уведомление)

### Используемые технологии:
- Для работы с сетью: Retrofit (SimpleXmlConverterFactory) + RxJava 
- Для работы с уведомлениями: WorkManager + Notification

### Работа с API:
Данные предоставляет Центральный Банк Российской Федерации (https://cbr.ru/)

Для получения информации о курсе валют на выбранную дату используется HTTP GET-запрос вида без заголовков:
http://www.cbr.ru/scripts/XML_daily.asp?date_req=dd/mm/yyyy

Сервер передает ответ в формате XML.

Полная документация на API
https://cbr.ru/development/SXML/

### Настройка уведомлений:
Частота уведомлений настраивается константами

- REPEAT_TIME_UNIT_FOR_WORKER = TimeUnit.MINUTES

- REPEAT_INTERVAL_WORKER = 15L
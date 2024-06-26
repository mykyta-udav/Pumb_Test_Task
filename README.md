﻿# Pumb_Test_Task
# Система управління даними про тварин (Animal Data Management API)

## Огляд

Система управління даними про тварин розроблена для забезпечення надійної бекенд-системи для зберігання, отримання та управління даними про різні типи тварин. Система підтримує завантаження даних через файли у форматах CSV та XML та дозволяє виконувати запити з даними за допомогою численних фільтрів та можливостей сортування.

### Основні функції:
1. **Завантаження файлів**: Підтримує завантаження даних про тварин через файли у форматах CSV та XML.
2. **Отримання даних**: Надає кінцеві точки для отримання даних про тварин, з можливістю фільтрації за типом, категорією, статтю та власним сортуванням.
3. **Документація Swagger**: Інтегровано Swagger для документації API та інтерактивного тестування.

### Технологічний стек:
- **Spring Boot**: Фреймворк для створення Java-додатків.
- **Spring Data JPA**: Спрощення доступу до даних з використанням Java Persistence API.
- **H2 Database**: База даних у пам'яті для розробки та тестування.
- **Springdoc OpenAPI**: Для автоматично генерованої документації API та інтерфейсу Swagger UI.

## Вимоги до системи

- **Java JDK**: Версія 11 або новіша.
- **Maven**: Управління залежностями та збирання проєкту.
- **IDE**: Будь-яке середовище розробки, сумісне з Java, наприклад IntelliJ IDEA, Eclipse або VS Code.
- **Postman** або будь-який інструмент для тестування API (необов'язково для взаємодії з API).
- **MySQL:** база даних  

## Налаштування проєкту

### Крок 1: Клонування репозиторію
```
git clone https://github.com/yourusername/animal-data-management.git
cd animal-data-management
```
### Крок 2: Збирання проєкту
```bash
mvn clean install
```

### Крок 3: Запуск додатку

### Крок 4: Доступ до додатку
```
Swagger UI: Перейдіть за адресою http://localhost:8080/swagger-ui.html, щоб переглянути документацію API та взаємодіяти з API.
```

### Тестування додатку
```
За допомогою Swagger UI:
Відкрийте Swagger UI та спробуйте різні кінцеві точки, передавши параметри та спостерігаючи за відповідями.
За допомогою Postman:
Налаштуйте Postman або будь-який інструмент для тестування API, щоб відправляти запити на кінцеві точки та валідувати відповіді.
Для тестування FileUploadController краще використовувати Postman
```
### Приклад використання Postman для тестування додатку:
![img.png](img.png)

### Вирішення проблем
```
Проблеми з підключенням до бази даних: Переконайтесь, що база даних H2 правильно налаштована в application.properties.
Проблеми з залежностями: Виконайте mvn dependency:tree, щоб діагностувати конфлікти або відсутні залежності.
```

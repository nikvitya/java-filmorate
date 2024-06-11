# Filmorate - сервис оценки фильмов и получения рекомендаций.
[![Java](https://img.shields.io/badge/-Java%2011-F29111?style=for-the-badge&logo=java&logoColor=e38873)](https://www.oracle.com/java/)
[![Spring](https://img.shields.io/badge/-Spring%202.7.1-6AAD3D?style=for-the-badge&logo=spring&logoColor=90fd87)](https://spring.io/projects/spring-framework)
[![H2](https://img.shields.io/badge/-H2-0f1aa3?style=for-the-badge&logo=db&logoColor=FFFFFF)](https://www.postgresql.org/)
[![JDBCtemplate](https://img.shields.io/badge/-JDBC_template-000000?style=for-the-badge&logo=db&logoColor=FFFFFF)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/-Maven-7D2675?style=for-the-badge&logo=apache&logoColor=e38873)](https://maven.apache.org/)
[![JUnit](https://img.shields.io/badge/JUnit%205-6CA315?style=for-the-badge&logo=JUnit&logoColor=white)](https://junit.org/junit5/docs/current/user-guide/)
[![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)](https://www.postman.com/)

### Описание:
Приложение, похожее на кинопоиск. В нём пользователиле ставят оценки фильмам и добавляют других пользоватей в друзья.

### Архитектура:
Монолитное приложение, данные хранятся в БД H2. Обращение к БД через JDBC template.

### Функциональность:

| Эндпоинт                                 | Описание                                                                                                  |
|------------------------------------------|-----------------------------------------------------------------------------------------------------------|
| POST /users                              | добавление пользователя                                                                                   |
| GET /users                               | получение списка пользователей                                                                            |
| GET /users/{id}                          | получение пользователя по id                                                                              |
| PUT /users                               | изменение информации о пользователе                                                                       |
| DELETE /users/{id}                       | удаление пользователя по id                                                                               |
| DELETE /users                            | удаление всех пользователей                                                                               |
| GET /users/{id}/friends                  | получение списка друзей пользователя                                                                      |
| GET /users/{id}/friends/common/{otherId} | получение общих друзей двух пользователей                                                                 |
| PUT /users/{id}/friends/{friendId}       | добавление пользователя в друзья                                                                          |
| DELETE /users/{id}/friends/{friendId}    | удаление пользователя из друзей                                                                           |
| GET /films                               | получение списка фильмов                                                                                  |
| GET /films/{id}                          | получение фильма по id                                                                                    |
| GET /films/popular?count={count}         | получение списка популярных фильмов длиной count. <br/>Параметр count не обязательный, по умолчанию равен 10 |
| POST /films                              | добавление фильма                                                                                         |
| PUT /films                               | изменение информации о фильме                                                                             |
| PUT /films/{id}/like/{userId}            | добавление лайка фильму                                                                                   |
| DELETE /films/{id} }                     | удаление фильма по id                                                                                     |
| DELETE /films }                          | удаление всех фильмов                                                                                     |
| DELETE /films/{id}/like/{userId} | удаление лайка у фильма                                                                                   |
| GET /genres | получение списка жанров фильмов                                                                           |
| GET /genres/{id} | получение жанра по id                                                                                     |
| GET /mpa | получение списка рейтингов MPA фильмов                                                                    |
| GET /mpa/{id} | получение рейтинга MPA по id                                                                              |


### Схема базы данных:  
![java-filmorate2](https://github.com/nikvitya/java-filmorate/assets/119168580/ef43c9ae-f3bd-4482-8751-8ff3eeb2ce22)

<https://dbdiagram.io/d/646b6874dca9fb07c487cc84>
### Тестирование:
Написано 23 юнит-теста для основной функциональности приложения с использованием аннотации @SpringBootTest

### Как запустить и использовать:
Откройте командную строку cmd и выполните следующие команды:
git clone https://github.com/nikvitya/java-filmorate.git

В командной строке переходите в корень проекта. Далее
  ```
mvn clean package
  ```
  ```
java -jar target\filmorate-0.0.1-SNAPSHOT.jar
  ```

Приложение готово к использованию! Сервис доступен по адресу http://localhost:8080

Со сценариями работы приложения ознакомьтесь, посмотрев и запустив [коллекцию Postman-тестов]

Дальнейшая разработка приложения велась в [групповом проекте] (https://github.com/AlinaProvotorova/java-filmorate) 
ветка [add-reviews] (https://github.com/AlinaProvotorova/java-filmorate/tree/add-reviews)



### Примеры запросов:  
**Для фильмов:**   
- Получить список всех фильмов, упорядоченных по дате выхода:
```SQL
SELECT * 
FROM Film
ORDER BY releaseDate;
```
**Для пользователей:**  
- Получение пользователя по идентификатору id:  
```SQL
SELECT *
FROM users
WHERE user_id = ?
```
- Получение всех пользователей:  
```SQL
SELECT *
FROM users
```

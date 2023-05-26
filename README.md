# java-filmorate
Схема базы данных:  
![java-filmorate](https://github.com/nikvitya/java-filmorate/assets/119168580/13c0b4cb-d899-4393-914c-bf0d72e38aef)



<https://dbdiagram.io/d/646b6874dca9fb07c487cc84>
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

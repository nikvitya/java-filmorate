# java-filmorate
Схема базы данных:  
![java-filmorate2](https://github.com/nikvitya/java-filmorate/assets/119168580/ef43c9ae-f3bd-4482-8751-8ff3eeb2ce22)



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

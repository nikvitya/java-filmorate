# java-filmorate
Схема базы данных:  
![java-filmorate2](https://github.com/nikvitya/java-filmorate/assets/119168580/bfefe599-f9a1-4ca7-8a43-06f8e562147a)




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

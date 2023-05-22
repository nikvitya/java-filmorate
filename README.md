# java-filmorate
Схема базы данных:
<img src="E:/Видео/Java ЯП/java-filmorate2.png"/>
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
### Hexlet tests and linter status:
[![Actions Status](https://github.com/Grad566/java-project-99/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/Grad566/java-project-99/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/624328207d690cb40d69/maintainability)](https://codeclimate.com/github/Grad566/java-project-99/maintainability)
[![My test](https://github.com/Grad566/java-project-99/actions/workflows/myTest.yml/badge.svg?event=push)](https://github.com/Grad566/java-project-99/actions/workflows/myTest.yml)


## О проекте
Cистема управления задачами, подобная http://www.redmine.org/. Она позволяет ставить задачи, назначать исполнителей и менять их статусы. Для работы с системой требуется регистрация и аутентификация.

Развернутое приложение: https://java-project-99-rmkr.onrender.com

Используйте hexlet@example.com и qwerty для авторизации.


## Локальный запуск
Требования:
- jdk 21
- gradle 8.7

```
make dev
```

После чего приложение будет доступно по http://localhost:8080/

В качестве бд будет H2. (на проде PostgreSQL)


## Документация
Документация swagger по url:
1) http://localhost:8080/swagger-ui/index.html
2) http://localhost:8080/v3/api-docs

## Дополнительные команды:
```
// запуск checkStyle
make lint 

// запуск тестов
make test 
```

## Использование
К приложению подключен фронт. Но можно использовать и как api отправляя запросы.

Примеры:

Для аутентификации используйте (пользователь уже есть в БД):
```
curl -X POST http://localhost:8080/api/login \
-H "Content-Type: application/json" \
-d '{
"username": "hexlet@example.com",
"password": "qwerty"
}'
```
После чего можно будет создавать новых пользователей и логиниться под новыми данными.

Пример:
```
curl -X POST http://localhost:8080/api/users \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW5AZXhhbXBsZS5jb20iLCJleHAiOjE3MjMxMTQ1NDksImlhdCI6MTcyMzExMDk0OX0.a6ItX_zm7w3cLn-IzFwizIXbo7YqpCTeCndnoklJf13BbbiVBKwvE9Ri5XO29s6dLWUBDBbuGE2yHWtgbwas-cPSI5eiHW5KTvwPI0e4RU6A5D9ki50R2hBnU6m1KYzTFsyp-iAIjh4NPQRFR2p-I9lKaJhCD1pMKqkldamaMEuWZa5M3xUyxDjTQWBmPE1YoTiVS77IjI79raBX1t26409aPqBSiOVOCWTlejqE14Gv_H_BWIjup637603c_jf5qb9mI0iN8lD2zyCco3xTm6tr01g28zriVXT8aQVc_ilUe3KXLu86r2SmmZkjHosoZXYIjxZ3BiJhXMw1lhWd7g" \
-d '{
    "email": "wantToWork@gmail.com",
    "password": "123zxc"
}'

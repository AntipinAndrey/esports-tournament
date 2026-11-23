# Система управления киберспортивными турнирами (Lab 3)

## Описание проекта

REST-сервис для управления киберспортивными турнирами, командами, игроками,
организаторами и матчами. Проект разработан на Java с использованием Spring
Boot, Spring Data JPA и PostgreSQL. Реализован паттерн MVC.

## Обработка исключений

В Лабораторной работе №3 добавлена централизованная обработка ошибок через
`@RestControllerAdvice`. Сервисы выбрасывают собственные исключения, а
`ApiExceptionHandler` преобразует их в ответы с HTTP-статусом, описанием,
подсказкой по исправлению и адресом запроса.

| Исключение | Ситуация | HTTP-статус |
|---|---|---:|
| `ResourceNotFoundException` | Организатор, команда, игрок, турнир или матч не найден | 404 |
| `DuplicateResourceException` | Объект с таким естественным ключом уже существует | 409 |
| `BusinessRuleException` | Нарушено правило матча или другой операции предметной области | 409 |
| `InvalidRequestException` | Противоречивые данные запроса или неверный формат | 400 |
| Ошибка валидации DTO или JSON | Пустое обязательное поле, неверная дата или JSON | 400 |
| Ошибка ограничения базы данных | Нарушена связь или уникальность записи | 409 |

Пример ответа API:

```json
{
  "timestamp": "2026-11-18T12:30:00Z",
  "status": 404,
  "error": "Ресурс не найден",
  "message": "Team not found: Unknown",
  "hint": "Check teamName or first send GET /api/teams.",
  "path": "/api/teams/Unknown"
}
```

## Пакеты проекта

- `model` — JPA-сущности предметной области;
- `dto` — DTO-объекты для запросов и ответов API с валидацией;
- `repository` — интерфейсы Spring Data JPA;
- `service` — бизнес-логика: интерфейсы и реализации `ServiceImpl`;
- `exception` — собственные исключения предметной области;
- `controller` — REST-контроллеры и централизованный обработчик ошибок;
- `src/test` — тесты Spring Boot, Mockito и MockMvc.

## REST API

### Организаторы (`/api/organizers`)

- `POST /api/organizers` — создать организатора;
- `PUT /api/organizers/{organizerName}` — обновить организатора;
- `GET /api/organizers` — получить всех организаторов;
- `GET /api/organizers/{organizerName}` — получить организатора;
- `GET /api/organizers?country={country}` — фильтр по стране;
- `DELETE /api/organizers/{organizerName}` — удалить организатора.

### Команды (`/api/teams`)

- `POST /api/teams` — создать команду;
- `PUT /api/teams/{teamName}` — обновить команду;
- `GET /api/teams` — получить все команды;
- `GET /api/teams/{teamName}` — получить команду;
- `GET /api/teams?country={country}` — фильтр по стране;
- `DELETE /api/teams/{teamName}` — удалить команду.

### Игроки (`/api/players`)

- `POST /api/players` — создать игрока;
- `PUT /api/players/{nickname}` — обновить игрока;
- `GET /api/players` — получить всех игроков;
- `GET /api/players/{nickname}` — получить игрока;
- `GET /api/players?teamName={teamName}` — фильтр по команде;
- `GET /api/players?firstName={firstName}` — фильтр по имени;
- `DELETE /api/players/{nickname}` — удалить игрока.

### Турниры (`/api/tournaments`)

- `POST /api/tournaments` — создать турнир;
- `PUT /api/tournaments/{tournamentName}` — обновить турнир;
- `GET /api/tournaments` — получить все турниры;
- `GET /api/tournaments/{tournamentName}` — получить турнир;
- `GET /api/tournaments?status={status}` — фильтр по статусу;
- `GET /api/tournaments?discipline={discipline}` — фильтр по дисциплине;
- `DELETE /api/tournaments/{tournamentName}` — удалить турнир.

### Матчи (`/api/matches`)

- `POST /api/matches` — создать матч;
- `PUT /api/matches/{tournamentName}/{firstTeamName}/{secondTeamName}` — обновить матч;
- `GET /api/matches` — получить матчи;
- `GET /api/matches/{tournamentName}/{firstTeamName}/{secondTeamName}` — получить матч;
- `GET /api/matches?tournamentName={tournamentName}` — фильтр по турниру;
- `GET /api/matches?teamName={teamName}` — фильтр по команде;
- `GET /api/matches?winnerName={winnerName}` — фильтр по победителю;
- `DELETE /api/matches/{tournamentName}/{firstTeamName}/{secondTeamName}` — удалить матч.

## Технологии

- Java 17;
- Spring Boot 3.4.4;
- Spring MVC;
- Spring Data JPA;
- PostgreSQL;
- Lombok;
- H2 для тестов;
- Mockito, `@SpringBootTest` и MockMvc;
- Maven.

Тест `ApiExceptionHandlerTest` проверяет отдельную обработку ошибок 404, 409,
ошибок валидации и некорректного JSON.

Схема таблиц создаётся Hibernate на основе JPA-аннотаций при запуске приложения.
Тесты используют изолированную H2-базу и не подключаются к пользовательской
базе PostgreSQL.

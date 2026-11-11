# Система управления киберспортивными турнирами (Lab 2)

## Описание проекта

REST-сервис для управления киберспортивными турнирами, командами, игроками,
организаторами и матчами. Проект разработан на Java с использованием Spring
Boot, Spring Data JPA и PostgreSQL. Реализован паттерн MVC.

## Пакеты проекта

- `model` — JPA-сущности предметной области;
- `dto` — DTO-объекты для запросов и ответов API с валидацией;
- `repository` — интерфейсы Spring Data JPA;
- `service` — бизнес-логика: интерфейсы и реализации `ServiceImpl`;
- `controller` — REST-контроллеры;
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

Схема таблиц создаётся Hibernate на основе JPA-аннотаций при запуске приложения.
Тесты используют изолированную H2-базу и не подключаются к пользовательской
базе PostgreSQL.

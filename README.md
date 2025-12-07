Project Backend Service
Overview

Backend-сервис предоставляет базовый функционал аутентификации, загрузки пользовательских файлов и административный интерфейс для просмотра логов. Проект может запускаться как через Docker Compose, так и в Kubernetes.

API Endpoints
1. Аутентификация
Метод	Путь	Описание
POST	/auth/register	Регистрация нового пользователя
POST	/auth/login	Выполнение логина
2. Профиль пользователя
Метод	Путь	Описание
POST	/minio/upload/{id}	Загрузка фото профиля, хранится в MinIO
3. Админские эндпоинты (требуется роль ADMIN)
Метод	Путь	Описание
GET	/admin/logs	Просмотр логов приложения

(Авторизация может быть реализована простой ролью в базе или любым используемым механизмом проекта.)

Swagger Documentation

После запуска сервис предоставляет интерактивную документацию:

http://localhost:8080/swagger-ui/index.html

Running Locally
1. Docker Compose

Запуск приложения:

docker compose up -d


Сервис поднимется на:

http://localhost:8080


Swagger:

http://localhost:8080/swagger-ui/index.html

Minio UI

http://localhost:9001

Running in Kubernetes

Применение манифестов:

kubectl apply -f manifests/ -n devops


Проверка статуса:

kubectl get pods -n devops


Доступ к приложению осуществляется через NodePort


Technologies

Java / Spring Boot

Spring Security

Postgres

Liquibase

MinIO

Swagger / OpenAPI

Docker

Kubernetes

# OAuth 2.0 GitHub client — учебный пример

Небольшое Spring Boot Java-приложение демонстрирует OAuth 2.0 Authorization Code flow через GitHub. Оно использует встроенную Thymeleaf-страницу, Spring Security OAuth2 Client и HTTP-сессию; отдельный frontend и база данных не нужны.

## Требования

- JDK 17+
- Gradle 8.x
- GitHub account и OAuth App

## Создание GitHub OAuth App

В GitHub откройте **Settings → Developer settings → OAuth Apps → New OAuth App** и задайте:

- Application name: например `OAuth2 Learning Demo`
- Homepage URL: `http://localhost:8080`
- Authorization callback URL: `http://localhost:8080/login/oauth2/code/github`

Сохраните OAuth App, скопируйте Client ID и создайте Client Secret. Не публикуйте secret и не коммитьте его в репозиторий.

## Запуск

Задайте секреты через переменные окружения, затем запустите приложение из корня:

```bash
export GITHUB_CLIENT_ID='ваш-client-id'
export GITHUB_CLIENT_SECRET='ваш-client-secret'
gradle bootRun
```

PowerShell:

```powershell
$env:GITHUB_CLIENT_ID = "ваш-client-id"
$env:GITHUB_CLIENT_SECRET = "ваш-client-secret"
gradle bootRun
```

Откройте http://localhost:8080 и нажмите **«Продолжить через GitHub»**. После подтверждения доступа приложение покажет login, имя и аватар профиля. Выход завершает локальную Spring-сессию.

## Что происходит в OAuth2 flow

1. Браузер открывает `/oauth2/authorization/github`.
2. Spring Security создаёт authorization request и перенаправляет браузер на GitHub; используются `state` и callback.
3. GitHub возвращает authorization code на `/login/oauth2/code/github`.
4. Spring обменивает code на access token сервер-сервер.
5. Spring с токеном запрашивает `https://api.github.com/user` и создаёт `OAuth2AuthenticationToken`/HTTP session.

В примере запрошен только scope `read:user`. OAuth 2.0 делегирует доступ к API; сама по себе OAuth 2.0 не является протоколом идентификации пользователя (для этого обычно применяют OpenID Connect).

## Важные настройки безопасности

- Client secret задавайте через окружение; `.env.example` — только шаблон.
- Не добавляйте реальные ключи в YAML, исходники или Git.
- Демо использует localhost и стандартную сессию; production требует HTTPS, корректной cookie-политики, управления секретами и защиты от CSRF согласно сценарию.
- Access token не выводится пользователю и не нужен в браузерном JavaScript.

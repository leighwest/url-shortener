# URL Shortener
This Java 25 Spring Boot application exposes a RESTful endpoint which converts a long URL into a short, random 6 character code.

A redirect endpoint accepts the shortened URI and returns the long form equivalent.

## Requirements 
- Java 25
- Maven

##  Running
### Windows
  ```
.\mvnw.cmd spring-boot:run
  ```
### Mac/Linux
```
./mvnw spring-boot:run
```
  Or run UrlShortenerApplication directly from IntelliJ.
  API
  
## API

### Shorten a URL
```
POST /api/shorten
Content-Type: application/json

{"url": "https://www.originenergy.com.au/electricity-gas/plans.html"}
```

Response:

```json
{"shortUrl": "http://localhost:8080/a1B2c3"}
```

### Redirect

```
GET /a1B2c3
```

Returns a 302 redirect to the original URL.

### Errors

400 Bad Request — blank, malformed, or non-http(s) URL:

```json
{"error": "Invalid URL", "details": "URL must use http or https scheme"}
```

404 Not Found — unknown short code:

```json
{"error": "Short code not found", "details": "a1B2c3"}
```

## Design Notes

- Short codes are randomly generated 6-character alphanumeric strings using `SecureRandom`. Random generation was chosen over sequential IDs to satisfy the non-sequential constraint.
- `ShortCodeGenerator` is an interface so the generation strategy can be swapped out easily — e.g. hash-based or vanity codes.
- `ShortCode` is a value object that validates the code format, keeping the domain model explicit rather than passing raw strings around.
- URLs are stored in a `ConcurrentHashMap` to handle concurrent requests safely without needing a database.
- Validation and error handling sit in the service layer, keeping the controller thin.

## Assumptions
- Each POST creates a new short code even if the URL has been shortened before — keeps the code simpler and avoids a lookup on every request.
- `app.base-url` in `application.properties` is set to `http://localhost:8080` — update this for other environments.
- Data is lost on restart as there is no persistence.

## Given More Time
- Persistent storage with Postgres and Flyway migrations.
- Rate limiting on the shorten endpoint.
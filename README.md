# Spring Tasks

## Practice 1 — Greeting REST endpoint
`GET /greeting` returns a greeting string. `GreetingController` depends on `GreetingService`
via constructor injection.

## Practice 2 — Maven deps, dev/test profiles, typed configuration
- `spring-boot-configuration-processor` + `spring-boot-starter-validation` added (versions from the Spring Boot BOM).
- `application.yml`, `application-dev.yml`, `application-test.yml` hold profile-specific settings under `app.greeting.*` and `server.port`.
- `GreetingProperties` (record, `@ConfigurationProperties(prefix = "app.greeting")`) binds and validates those settings.
- `GET /greeting-info` returns the bound `GreetingProperties` as JSON, proving the values were read.

Run with a profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## Practice 3 — Constructor injection + conditional bean
`GreetingService` now depends on `Optional<GreetingProvider>`, injected through its constructor
(no `@Autowired` fields anywhere in the service layer).

`GreetingConfig` declares the `greetingProvider` bean conditionally:

```java
@Bean
@ConditionalOnProperty(prefix = "app.greeting", name = "enabled", havingValue = "true")
public GreetingProvider greetingProvider(GreetingProperties properties) {
    return properties::message;
}
```

### How to toggle it
Set `app.greeting.enabled` in `application-dev.yml` / `application-test.yml`, or override at runtime
without editing files:

```bash
# bean ON (default) — /greeting returns the configured message
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# bean OFF — /greeting falls back to "Greeting is currently disabled"
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.arguments=--app.greeting.enabled=false
```

Or with the packaged jar:
```bash
./mvnw clean package
java -jar target/practice1-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev --app.greeting.enabled=false
```

Verify with:
```bash
curl http://localhost:8081/greeting
```

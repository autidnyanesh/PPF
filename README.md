# PPF module of GBM

PPF is a **separate Spring Boot JAR**. It does not own login.

1. User logs in to **GBM** (`https://github.com/autidnyanesh/GBM`) on port **8081**.
2. GBM Home is shown.
3. User opens **PPF**. The browser sends GBM's JWT to this app on port **8082**.
4. PPF verifies the token with the same `GBM_TOKEN_SECRET` and serves `/api/ppf/**`.

There is no PPF login endpoint on purpose.

## Run locally (H2)

Start GBM first with profile `h2`, then:

```bash
export GBM_TOKEN_SECRET=gbm-local-dev-secret-please-change-32bytes!
./mvnw spring-boot:run -Dspring-boot.run.profiles=h2
```

Use the same secret in both apps. Open http://localhost:8081, log in, then click **PPF**.

## API (all require `Authorization: Bearer <gbm-jwt>`)

| Method | Path |
|---|---|
| GET | `/api/ppf/home` |
| GET | `/api/ppf/accounts` |
| GET | `/api/ppf/accounts/{id}` |
| POST | `/api/ppf/accounts` |
| POST | `/api/ppf/accounts/{id}/contributions` |
| POST | `/api/ppf/accounts/{id}/withdrawals` |
| POST | `/api/ppf/accounts/{id}/close` |

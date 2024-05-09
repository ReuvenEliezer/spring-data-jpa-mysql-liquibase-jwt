# Documentation
# Swagger
- http://localhost:8080/swagger-ui/index.html 

# Security - AuthController
- register (TODO - only System Administrator can register a new user)
    ```bash
    curl --location 'http://localhost:8080/api/v1/auth/register' \
      --header 'Content-Type: application/json' \
      --data-raw '{"email":"email@gmail.com","password": "password"}'
    ```
- login
    ```bash
    curl --location 'http://localhost:8080/api/v1/auth/login' \
    --header 'Content-Type: application/json' \
    --data-raw '{"email":"email@gmail.com","password": "password"}'
    ```
- refresh-token
    ```bash
    curl --location 'http://localhost:8080/api/v1/auth/refresh-token' \
    --header 'Content-Type: application/json' \
    --data '{"refreshToken": "<given token from login API>" }'
  ```
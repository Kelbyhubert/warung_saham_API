# User Api Spec

## Login
Method : POST \
Endpoint: /api/v1/auth/signin

Request Body:

```json
{
  "username": "admin1",
  "password": "secret"
}
```

Response Body (Success)

```json
{
  "data": {
    "token": "TOKEN",
    "expiredDate": 2321132919
  }
}
```

Response Body (Failed)

```json
{
  "data": null,
  "error": {
    "code": "401",
    "message": "username or password invalid"
  }
}
```

## Register
Method : POST \
Endpoint : /api/v1/user/add-user

Request Body:

```json
{
  "username": "admin1",
  "password": "secret"
}
```

Response Body (Success)

```json
{
  "data": {
    "token": "TOKEN",
    "expiredDate": 2321132919
  }
}
```

Response Body (Failed)

```json
{
  "data": null,
  "error": {
    "code": "401",
    "message": "username or password invalid"
  }
}
```

## Update User

## Get User

## Logout User
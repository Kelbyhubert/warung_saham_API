# User Api Spec

## Login
Endpoint: POST/api/v1/users

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
    "userData": {
      "userId": "userId",
      "username": "admin1"
    },
    "expiredDate": 2321132919
  }
}
```

Response Body (Failed)

```json
{
  "data": null,
  "error": {
    "code": "http error code",
    "message": "message"
  }
}
```

## Register

## Update User

## Get User

## Logout User
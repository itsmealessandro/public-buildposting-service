# Client explanation

The client is a process that provides an Form about the information that the end
user wants to be sent to the Camunda API responsible for handling the request.
Many client should be able to do a request simultaneously.

## How the Client works

1) The client process should be an application capable sending a
POST request about the service.
2) wait for the response from the Camunda process.
3) Then send another POST with confirm or cancel.

### How to run

```bash
locust -f locustfile.py --host http://localhost:8091
```

### Flow example

This is an example of request sent by the locust client

``` JSON
{
  "username": "mariorossi_a3b4c5d6",
  "cities": ["Rome", "Milan"],
  "max_prices": {
    "Rome": 350,
    "Milan": 280
  },
  "poster_format": "A4"
}
```

The expected response is:

```JSON
{
  "selectedZones": ["Rome_Center", "Milan_North"],
  "totalPrice": 630.50,
  "requestId": "a1b2c3d4e5f6g7h8i9j0"
}
```

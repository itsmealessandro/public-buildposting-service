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

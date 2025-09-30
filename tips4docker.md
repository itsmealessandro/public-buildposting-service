# tips for docker

Remember that when you are using docker some stuff will change.
In particular many things related to the network.

## network changes

When you want to access from outside to the containers you can use

```URL
 localhost:(port)/path 
```

But when you are **INSIDE** a container and you want to interact with a container
of the same network, you have to use another notation.

```URL
(container-name):(port)/path
```

This is an example:

Will not work if used by an application that is containerized:

```URL
http://localhost:8888/postingservice
```

This has to be used instead:

```URL
http://posting-service:8888/postingservice
```

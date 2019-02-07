# Simple ToDo Application
[![CircleCI](https://circleci.com/gh/perforb/simple-todo/tree/master.svg?style=shield&circle-token=972d4b658137dd49daddae29e77b9255f08f1ccc)](https://circleci.com/gh/perforb/simple-todo/tree/master)
[![CircleCI](https://circleci.com/gh/perforb/simple-todo/tree/develop.svg?style=shield&circle-token=972d4b658137dd49daddae29e77b9255f08f1ccc)](https://circleci.com/gh/perforb/simple-todo/tree/develop)

This is very simple ToDo application written in Java 8+ using Spring Boot v2.

## Motivation

I wanted my own product which I could freely develop.
Also, I'd like to make use of my knowledge gained from this product for my business.

## Technology Stack

Under construction...

## Run Application

```
$ ./gradlew build && ./gradlew :simple-todo-api:bootRun
```

## Run Docker

```
$ docker-compose -p simple-todo up -d
```

## Want to do

* [x] CI
    - [x] CircleCI
* [x] Testing
    - [x] JUnit 5
* [x] Multiple Projects
    - [x] Gradle
* [ ] Clean Architecture
* [ ] Messaging
    - [ ] Kafka
* [ ] Authentication/Authorization
    - [x] JWT
    - [ ] Authorization Server
    - [ ] Resource Server
* [ ] Frontend
    - [ ] Vue.js
* [ ] Machine Learning

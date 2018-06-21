# fileprocessor
This project aims at building a graphical dashboard visualizer. The features are
1. Reading data from a `csv` file
2. Provide tabular data from the stored file
3. Provide mapped data for graphs - bar, histogram and scatter

# Technology Used
1. Java 8
2. Spring Boot

# How to run?

## Pre-requisites
1. Please make sure java 8 is installed
2. Please make sure maven is installed
3. Please make sure git is installed
4. Please make sure docker is installed

## Steps to run without Docker

1. `git clone` the repo and `cd` into it
2. Run the below
```sh
mvn spring-boot:run
```
3. The serivice is now available in `http://localhost:8080`
4. To check you can run this in the browser `http://localhost:8080/deranz/getTime`, you should get the current date and time

## Steps to run with Docker

1. `git clone` the repo and `cd` into it
2. Run the below
```sh
docker build -t="fileprocessor" .
docker run -i -t -p 8080:8080 fileprocessor
```
3. The serivice is now available in `http://localhost:8080`
4. To check you can run this in the browser `http://localhost:8080/deranz/getTime`, you should get the current date and time

License
----

MIT

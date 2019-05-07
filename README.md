# Spring/Angular/Bootstrap REST API demo

## Requirements

A running PostgreSQL server >= 11.2

## Configure

To setup the database needed by the application and its user:

```$ psql postgres < pgsql/create.sql```

## Run the application 

Use `./gradlew bootRun` to build and run the application.

Point your browser to http://localhost:8080/en/index.html

### Demo users

- admin/admin
- user/user

## Cleanup

To remove the database and its user:

```$ psql postgres < pgsql/clean.sql```

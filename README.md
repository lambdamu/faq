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

## About

CRUD demo using Spring Boot and Angular as front-end.

### Functionalities

* Browse FAQs
* Search FAQs (keyword search on question/answer/tags)
* Login
* Logout
* Create a new FAQ (admin)
* Update a FAQ (admin)
* Delete a FAQ (admin)

### UI proofing

* Responsive design with Boostrap 4
* Form validation
* Watch for unsaved changes
* Request for confirmation on deletion
* Alert service for fatal errors (e.g. network errors)

### Security

* Spring route security
* CSRF support implementation
* Angular route guard

### Internationalisation

#### Back-end

* French / English
* Support for locale change request

Note that there is an interpolation [bug](https://github.com/spring-projects/spring-boot/issues/3071) when using Spring's resource bundle with Hibernate's validator.

#### Front-end

* French / English
* Uses Angular AOT build-in i18n support.


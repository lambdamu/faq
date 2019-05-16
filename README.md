# Spring/Angular/Bootstrap REST API demo

## À l'attention de Netheos

### Challenge technique back-end

* Les user stories 1, 2 et 3 sont supportées
* Utiliser un SGBD type MySQL (ou MariaDB) ou PostgreSQL: oui PostgresSQL 
* Être compilable sous la forme d'un WAR ou d'un fat jar (java 8+): Spring Boot fat jar 
* Être assemblé via Gradle: oui
* Tests: via l'interface Angular, ou l'export Postman (`faq-api/api.postman_collection.json`). 

**IMPORTANT**
Dans Postman, vous devez: 
* créer un environnement avec la variable X-XSRF-TOKEN et l'utiliser pour la collection.
* utiliser les requêtes login user/admin pour tester les permissions. Un script post-query informe la variable X-XSRF-TOKEN.

### Challenge technique front-end

L'interface diffère. 

L'éditeur de FAQ est disponible 
* soit à partir du sous-menu Admin
* soit en cliquant sur le lien d'édition disponible pour chaque FAQ listée - si l'utilisateur est admin

Il n'y a qu'une seule interface pour la recherche et le listing et elle est accessible par tous les utilisateurs (user stories 3, 4, 5).
Après une recherche, si l'utilisateur veut accéder au listing, il peut soit clicker sur 'Home/Accueil' soit effacer sa recherche. 

* User story 1 (login): oui mais sans le mot de passe oublié
* User story 2 (editeur): oui
* User story 3 (listing): oui
* User story 4 (recherche admin): oui, mais la recherche porte sur tous les champs (réponse inclue)
* User story 5 (recherche anonyme): oui
* Assemblé via Gulp ou Webpack: non
* AngularJS ou Angular 2: non, Angular 7
* Testing: un tout petit test peut être vérifié via:

```
$ cd faq-ui
$ ng test
```

La suite de ce README est en anglais, tel que je l'avais écrit à l'origine.

## Requirements

A running PostgreSQL server >= 11.2 with postgres user access (or any account with create and grant privileges).

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
* Tag prediction/type ahead (FAQ editor)

### UI proofing

* Responsive design with Boostrap 4
* Form validation
* Watch for unsaved changes
* Request for confirmation on deletion
* Alert service for fatal errors (e.g. network errors)

### Security

* Spring route security
* JWT not implemented for this demo but CSRF is enabled
* Angular route guard
* PostgreSQL user is granted all privileges for the demo (Hibernate DB creation), they should be restricted in a production environment. 

### Internationalisation support

The demo contains sample data in English only and does not support translations. 
However both back-end and front-end are setup to support internationalisation if needed.

#### Back-end

* Resource bundle in French / English
* Support for locale change request

Note that there is an interpolation [bug](https://github.com/spring-projects/spring-boot/issues/3071) when using Spring's resource bundle with Hibernate's validator.

#### Front-end

* Interface available in French / English
* Uses Angular AOT built-in i18n support.


### Sample tests

#### Java

* `faq.FaqRepositoryTests`
* `faq.TagRepositoryTests`

#### Angular

* `src/paging/paging.component.spec.ts`



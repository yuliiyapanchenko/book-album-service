# book-album-service

Spring boot application, which provides REST API for searching books and albums at google books and itunes. 
There are 3 main endpoints:
* /search - GET /search Search for books albums by specified text query
* /statistic - GET Metrics for response times of the upstream services
* /health - GET Endpoint to check service health (via spring-boot-starter-actuator)

To start application:
1. mvn spring-boot:run
2. navigate to http://localhost:8080/ in browser

There is swagger UI available to document the API and test it executing requests to the REST controllers.

To set the environment type add VM option -Dspring.profiles.active=profileType. Configurable profiles: prod, dev

To deploy in Google Cloud App Engine (installed  Google Cloud SDK is required):
1. create google cloud project "book-album-service"
2. execute mvn appengine:deploy

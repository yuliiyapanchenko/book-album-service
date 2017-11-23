# book-album-service

To start application:
1. mvn spring-boot:run
2. navigate to http://localhost:8080/ in browser

To set the environment type add VM option -Dspring.profiles.active=profileType. Configurable profiles: prod, dev

To deploy in Google Cloud App Engine (installed  Google Cloud SDK is required):
1. create google cloud project "book-album-service"
2. execute mvn appengine:deploy
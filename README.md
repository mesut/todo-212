todo-212 is a simple web application that written in java 8,spring boot,spring web,spring data,spring security,thymeleaf, embedded h2. All unit tests are written in mockito.

todo-212 app is connected to heroku for automatic deployment.

Simply you can run this app with "mvn spring-boot:run".

If you want to use docker to run this application.You can simply run "mvn clean package docker:build" command for creating docker image which name is todo212. After docker image is created, just run "docker run -p 8080:8080 todo212 -t -d". Now you can access the app from localhost:8080.


[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy)

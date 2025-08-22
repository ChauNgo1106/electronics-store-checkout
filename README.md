# Altech Electronics Store Backend

## Prerequisite:
+ Install Docker Desktop on your machine
+ Install Maven with JDK 17
+ Java JDK 17

## 🚀 Start the App
Run the script in Git Bash (Be careful! It will wipe out all running docker containers and remove docker's images on 
your local)

```bash
.\runDocker.sh
```

## [OPTIONAL] Run all testing cases
The script in Dockerfile is good enough to run all testing cases.
In case of you may want to run test cases manually.
```bash
mvn test

# OR, alternatively, you can run the following command to use Maven Wrapper
./mvnw test
```
## [OPTIONAL] Don't want to use Docker
```bash
./mvnw clean package
./mvnw spring-boot:run
```

## Endpoints
You can easily find a full details about APIs via this link after running successfully:
http://localhost:8080/swagger-ui.html
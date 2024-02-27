FROM openjdk:17
 
WORKDIR /newsApp
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
 
COPY src ./src
EXPOSE 3001
 
CMD ["./mvnw", "spring-boot:run"]
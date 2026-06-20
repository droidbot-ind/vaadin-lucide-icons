FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

RUN curl -fsSL https://deb.nodesource.com/setup_20.x | bash - && apt-get install -y nodejs

COPY pom.xml ./
COPY v-lucide-icons/pom.xml ./v-lucide-icons/pom.xml
COPY v-lucide-icons/src ./v-lucide-icons/src
COPY v-lucide-icons-demo/pom.xml ./v-lucide-icons-demo/pom.xml
COPY v-lucide-icons-demo/src ./v-lucide-icons-demo/src

RUN mvn -pl v-lucide-icons-demo -Pproduction package -DskipTests -q

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/v-lucide-icons-demo/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

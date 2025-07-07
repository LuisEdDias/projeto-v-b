# Etapa 1: Build da aplicação
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package

# Etapa 2: Imagem leve para rodar a aplicação
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o JAR gerado para o container
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
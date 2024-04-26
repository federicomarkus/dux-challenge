# Imagen de java 17
FROM openjdk:17-jdk-slim

# Instalar Maven
RUN apt-get update && apt-get install -y maven

# Crear un directorio en docker
WORKDIR /app

# Copiar la aplicación en docker
COPY . .

# Construir el proyecto con Maven
RUN mvn package

# Puerto
EXPOSE 8080

# Comando Inicial
CMD ["java", "-jar", "/app/target/dux-0.0.1-SNAPSHOT.jar"]

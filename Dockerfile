# Multi-stage build para Georef Service

# Stage 1: Build con Commons dependency
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Primero compilar la dependencia commons
COPY ../commons/pom.xml /commons/pom.xml
COPY ../commons/src /commons/src
WORKDIR /commons
RUN mvn clean install -DskipTests

# Volver al directorio de georef
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Descargar dependencias
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar la aplicación
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Crear usuario no-root
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Copiar el JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Cambiar permisos
RUN chown -R appuser:appgroup /app

USER appuser

# Exponer puerto
EXPOSE ${GEOREF_PORT:-8087}

# Variables de entorno
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:${GEOREF_PORT:-8087}/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
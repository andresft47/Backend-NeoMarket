# NeoMarket - Backend

Este es el backend del proyecto **NeoMarket**, construido con **Java** y **Spring Boot**. Se encarga de proveer la API REST, manejar la lógica de negocio y conectarse a la base de datos.

## 🚀 Tecnologías y Dependencias

El proyecto utiliza Maven para gestionar las dependencias. Las principales tecnologías son:
- **Java 21+**
- **Spring Boot (v4.0.6)**
- **Spring Data JPA**: Para el manejo y persistencia de datos (ORM).
- **Spring Web**: Para la creación de la API REST.
- **H2 Database**: Base de datos en memoria utilizada actualmente para desarrollo y pruebas rápidas.
- **SpringDoc OpenAPI (Swagger)**: Para documentar y probar la API visualmente.

*(Nota: Spring Security fue removido temporalmente para facilitar las pruebas de los endpoints durante el desarrollo temprano).*

## ⚙️ Configuración Actual

Toda la configuración principal se encuentra en `src/main/resources/application.properties`. Actualmente está configurada para:
- **Puerto del Servidor:** `8080` (por defecto de Spring Boot).
- **Base de Datos:** H2 Database configurada en memoria (`jdbc:h2:mem:testdb`). 
- Cada vez que se reinicia el servidor, la base de datos de prueba se reinicia.

## 🛠️ Cómo Ejecutar el Proyecto Localmente

No necesitas instalar Maven manualmente en tu sistema, el proyecto incluye un **Maven Wrapper** (`mvnw` / `mvnw.cmd`) que descargará todo automáticamente la primera vez.

1. **Abre tu terminal** y asegúrate de estar en la carpeta `backend`.
2. **Ejecuta el servidor:**
   - En Windows (PowerShell/CMD): `.\mvnw.cmd spring-boot:run`
   - En Git Bash / Linux / Mac: `./mvnw spring-boot:run`
3. **Espera a que compile** y descargue las dependencias (solo tarda la primera vez).
4. Sabrás que está listo cuando veas un mensaje similar a `Started BackendApplication`.

## 🌐 Endpoints Disponibles (Pruebas)

Una vez que el servidor esté corriendo en el puerto 8080, puedes acceder a:
- **Endpoint de prueba (JSON):** [http://localhost:8080/api/test](http://localhost:8080/api/test)
- **Documentación de Swagger (Interfaz Gráfica):** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

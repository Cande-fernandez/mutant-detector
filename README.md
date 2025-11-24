![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)
![Gradle](https://img.shields.io/badge/Gradle-8.x-blue)
![Tests](https://img.shields.io/badge/Tests-35%20passing-brightgreen)
![Coverage](https://img.shields.io/badge/Coverage-90%25-brightgreen)

# 🧬 Mutant Detector API

Detecta si un ADN pertenece a un **mutante** o a un **humano** a través del análisis de secuencias de ADN.
Construido con **Spring Boot**, **H2 Database**, y **Swagger/OpenAPI**.

---

## 🚀 Características

* ✅ Algoritmo optimizado con **detección temprana** y uso eficiente de memoria.
* ✅ Persistencia de ADN con **hash SHA-256** para evitar duplicados.
* ✅ Endpoints REST:

  * `POST /mutant` → Verifica ADN mutante
  * `GET /stats` → Retorna estadísticas de mutantes/humanos
* ✅ Validación de entradas con anotaciones **Jakarta Validation**.
* ✅ Documentación interactiva con **Swagger UI**.
* ✅ Testeo completo con más de 35 tests (unitarios e integración).
* ✅ Dockerizado y listo para despliegue en **Render**.

---

## 📦 Tecnologías

* Java 17
* Spring Boot 3
* Gradle
* H2 Database (in-memory)
* Lombok
* Swagger/OpenAPI
* JUnit + Mockito

---

## 🏗️ Arquitectura

```
src/main/java/com/example/mutant_detector/
├── controller/    -> Endpoints REST
├── dto/           -> Data Transfer Objects
├── service/       -> Lógica de negocio
├── repository/    -> Acceso a base de datos
├── entity/        -> Entidades JPA
├── config/        -> Configuración Swagger / Spring
```

---

## ⚡ Uso

### 🔹 Con Gradle (local)

1. Build del proyecto:

```bash
./gradlew clean build
```

2. Ejecutar la aplicación:

```bash
./gradlew bootRuns
```

3. Acceder a Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

---

### 🔹 Con Docker

1. Construir la imagen:

```bash
docker build -t mutant-detector .
```

2. Ejecutar contenedor:

```bash
docker run -p 8080:8080 mutant-detector
```

3. Verificar Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Endpoints

### POST `/mutant`

* **Descripción:** Verifica si el ADN es mutante.
* **Request Body:** `DnaRequest`

```json
{
  "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```

* **Responses:**

  * `200 OK` → Mutante
  * `403 FORBIDDEN` → Humano
  * `400 BAD REQUEST` → ADN inválido

### GET `/stats`

* **Descripción:** Obtiene estadísticas de ADN analizado.
* **Response:** `StatsResponse`

```json
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
```

---

## ✅ Testing

* Ejecutar tests:

```bash
./gradlew test
```

* Cobertura:

  * MutantDetector > 85%
  * MutantService > 90%
  * Tests totales: 35+

---

## 📄 Validaciones

* ADN debe ser NxN
* Solo caracteres A, T, C, G
* Secuencias detectadas horizontal, vertical, diagonal, y diagonal inversa
* Deduplicación usando hash SHA-256

---

## 🌐 Despliegue

* Proyecto desplegado en **Render**:
  `https://tu-app-mutant-detector.onrender.com/`

* Contenedor Docker listo para producción.

---

## 🧑‍💻 Autor

* Estudiante: **[Tu Nombre]**
* Email: **[****[tuemail@dominio.com](mailto:tuemail@dominio.com)****]**
* Curso/Proyecto: **Desarrollo Avanzado Spring Boot**

---

## 🔗 Recursos

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Swagger/OpenAPI](https://springdoc.org/)
* [Docker](https://www.docker.com/)
* [Render](https://render.com/)

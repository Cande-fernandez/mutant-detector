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
Mutantes/
│
├── 📂 src/main/java/org/example/
│   │
│   ├── 📂 config/                    ← Configuraciones
│   │   └── SwaggerConfig.java        (OpenAPI/Swagger)
│   │
│   ├── 📂 controller/                ← Capa de presentación
│   │   └── MutantController.java     (Endpoints REST)
│   │
│   ├── 📂 dto/                       ← Objetos de transferencia
│   │   ├── DnaRequest.java           (Input API)
│   │   ├── StatsResponse.java        (Output API)
│   │   └── ErrorResponse.java        (Errores)
│   │
│   ├── 📂 entity/                    ← Entidades JPA
│   │   └── DnaRecord.java            (Tabla dna_records)
│   │
│   ├── 📂 exception/                 ← Manejo de errores
│   │   ├── GlobalExceptionHandler.java
│   │   └── DnaHashCalculationException.java
│   │
│   ├── 📂 repository/                ← Acceso a datos
│   │   └── DnaRecordRepository.java  (Interface JPA)
│   │
│   ├── 📂 service/                   ← Lógica de negocio
│   │   ├── MutantDetector.java       (Algoritmo core)
│   │   ├── MutantService.java        (Orquestación)
│   │   └── StatsService.java         (Estadísticas)
│   │
│   ├── 📂 validation/                ← Validaciones custom
│   │   ├── ValidDnaSequence.java     (Anotación)
│   │   └── ValidDnaSequenceValidator.java (Lógica)
│   │
│   └── MutantDetectorApplication.java ← Main class
│
├── 📂 src/main/resources/
│   └── application.properties        ← Configuración app
│
├── 📂 src/test/java/org/example/    ← Tests
│   ├── 📂 controller/
│   │   └── MutantControllerTest.java
│   └── 📂 service/
│       ├── MutantDetectorTest.java
│       ├── MutantServiceTest.java
│       └── StatsServiceTest.java
│
├── 📂 build/                        ← Archivos compilados
├── 📂 gradle/                       ← Wrapper de Gradle
│
├── build.gradle                      ← Dependencias
├── settings.gradle                   ← Config Gradle
├── gradlew / gradlew.bat             ← Scripts Gradle
├── CLAUDE.md                         ← Guía técnica
└── README.md                         ← Este archivo
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

* 🚀 **API en Vivo (Render):**
  [https://mutant-detector-candela.onrender.com/](https://mutant-detector-candela.onrender.com/)
  *(Nota: El primer request puede tardar unos 50 segundos en "despertar" al servidor)*

* 🐳 **Docker:** Imagen optimizada con Eclipse Temurin 17.

---

## 🧑‍💻 Autor

* Estudiante: **[Candela Fernandez]**
* Email: **[****[candeefernand10@gmail.com](mailto:tuemail@dominio.com)****]**
* Curso/Proyecto: **Desarrollo de Software**

---

## 🔗 Recursos

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Swagger/OpenAPI](https://springdoc.org/)
* [Docker](https://www.docker.com/)
* [Render](https://render.com/)

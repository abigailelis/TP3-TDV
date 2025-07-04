# TP3-TDV

### Este proyecto contiene una aplicación bancaria de gestión de tarifas y cuentas. 

#### Está dividido en dos partes: 
1. **Backend** hecho en Spring Boot 
2. **Frontend** en Next.js. 
3. Ambos viven en carpetas separadas bajo un mismo repositorio.

---

## 📦 Estructura del repositorio

    TP3-TDV/ 

        ├── backend/ # API REST con Spring Boot y Docker 

        ├── frontend/ # Interfaz de usuario creada con V0 y Node.js

---

## 🚀 Requisitos previos

- [Docker](https://www.docker.com/)
- [Node.js](https://nodejs.org/) (v18 o superior recomendado)
- `npm` o `pnpm` (según lo que uses para manejar dependencias)

---

## ⚙️ Cómo levantar el proyecto

### 1️⃣ Cloná el repositorio

```bash
    git clone https://github.com/abigailelis/TP3-TDV.git
    cd TP3-TDV
```
### 2️⃣ Backend (Spring Boot con Docker)

🔧 Paso a paso

```bash
    cd backend
    docker build -t sistema-bancario-backend .
    docker run -p 8080:8080 sistema-bancario-backend
```
👉 Esto levanta la API REST en http://localhost:8080.

## 🔍 Accedé a la documentación interactiva en el siguiente link:

[Documentación Swagger](http://localhost:8080/swagger-ui/index.html)

### 3️⃣ Frontend (Next.js)
🔧 Paso a paso
```bash
    cd ../frontend
    npm install         # o pnpm install
    npm run dev         # o npm start si así está configurado
```
👉 Esto levanta el frontend en http://localhost:3000.

---

## 🧪 Endpoints disponibles
### Algunos recursos expuestos por la API:

> GET /api/tarifas

> GET /api/tipo-tarifas

> GET /api/cuentas

> POST, PUT, DELETE disponibles según entidad

**Todos documentados en Swagger.**

---
## 🧪 Visualización de pruebas
### 📬 Postman
En la carpeta **/backend/postman/** encontrarás una colección .json. 
#### Para usarla:

1. Abrir Postman
2. Importar la colección (Archivo > Importar)
3. Ejecutar los endpoints con el backend corriendo en ***localhost:8080***

### 🧭 Selenium IDE
En /selenium-tests/src/test se encuentran los scripts de prueba automatizados exportados desde Selenium IDE, organizados en un proyecto Maven.

La estructura relevante del proyecto incluye:

- TestSuite.xml: archivo de suite para ejecutar múltiples pruebas.
- Main.java: clase utilitaria para lanzar pruebas desde código.
- Clases de prueba individuales en /tudai/tests/

#### ▶️ Cómo ejecutar los tests
Se pueden correr los tests de Selenium de tres maneras distintas:

1. Desde TestSuite.xml
   Usando Maven con Surefire o un plugin compatible:

```bash
  cd selenium-tests
  mvn test 
```
Esto ejecutará todas las pruebas incluidas en la suite definida por el archivo TestSuite.xml.

2. Desde el Main
   La clase Main ubicada en tudai.Main permite ejecutar las pruebas directamente desde un main():

```bash
  cd selenium-tests
  mvn compile exec:java -Dexec.mainClass="tudai.Main"
```
Ideal para debugging o ejecución rápida sin depender del XML.

3. Desde una clase de test específica
   Se puede correr un test puntual desde Maven:

```bash
  mvn -Dtest=NombreDelTest test
```
**Por ejemplo, para ejecutar solo CrearCuentaTest:**

```bash
  mvn -Dtest=tudai.tests.CrearCuentaTest test
```
--- 

## 🧱 Tecnologías utilizadas

### 🔙 Backend
- Java 21
- Spring Boot 3+
- Spring Data JPA
- Hibernate Validator
- Swagger/OpenAPI 3 (para documentación automática)
- Docker (para contenedorización)

### 🌐 Frontend
- Node.js 18+
- React 18 (Next.js 15)
- TailwindCSS + Radix UI
- React Hook Form + Zod para validación
- V0 (UI generado por Vercel)

### 🧪 Herramientas de testing
- **Postman**: para pruebas manuales de endpoints REST
- **Selenium IDE**: para pruebas automáticas de flujo en la interfaz

--- 

### 🧠 Autores
- Elis Abigail
- Brost Simón
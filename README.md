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
En **/frontend/tests-selenium/** podés encontrar los scripts de prueba exportados desde Selenium IDE.

#### Para ejecutarlos:

1. Instalar la extensión Selenium IDE para tu navegador
2. Abrir el archivo .side en la extensión 
3. Ejecutar las pruebas sobre tu frontend local (localhost:3000)

---

## 🧱 Tecnologías utilizadas

### 🔙 Backend
- Java 17
- Spring Boot 3+
- Spring Data JPA
- Hibernate Validator
- Swagger/OpenAPI 3 (para documentación automática)
- Docker (para contenedorización)

### 🌐 Frontend
- Node.js 18+
- React 19 (Next.js 15)
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
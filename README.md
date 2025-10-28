# 🧾 README — API REST de Creación de Usuarios

## 🧠 Descripción del proyecto

Este proyecto es una API REST desarrollada con **Spring Boot**, que permite registrar usuarios y sus teléfonos asociados.  
Al momento de la creación, se genera un **token (JWT)** que se almacena en base de datos y se devuelve en la respuesta.

El sistema utiliza:

- **Spring Boot**
- **Spring Data JPA (Hibernate)**
- **Base de datos en memoria H2**
- **Lombok**
- **ModelMapper**
- **Validaciones**
- **Generación de token JWT**

---

## ⚙️ Requisitos previos

Antes de ejecutar el proyecto asegúrate de tener instalado:

- ☕ **Java 17** o superior  
- 🧱 **Maven 3.8+**  
- 📬 **Postman** (para pruebas)

---

## 🚀 Ejecución del proyecto

### 1️⃣ Clonar el repositorio
```bash
git clone https://github.com/<tu-usuario>/user-api-demo.git
cd user-api-demo
```

### 2️⃣ Compilar y ejecutar
```bash
mvn clean install
mvn spring-boot:run
```
### 3️⃣ Acceder a la consola H2 (opcional):
Una vez iniciada la aplicación, entra a:
👉 http://localhost:8080/h2-console

- JDBC URL: jdbc:h2:mem:demo
- User Name: demo
- Password: (dejar vacío)

---

## 🧩 Endpoints principales

### 🔹 Crear un nuevo usuario
- POST /api/users
- Content-Type: application/json

📥 Ejemplo de request:
```json
{
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "password": "Hunter28$",
    "phones": [
        {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
        }
    ]
}
```

📤 Ejemplo de response:
```json
{
    "id": "7acea6f1-a72a-460a-8c9e-fa874c50809c",
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "created": "2025-10-27T22:41:38.5799128",
    "modified": "2025-10-27T22:41:38.5799128",
    "lastLogin": "2025-10-27T22:41:38.5799128",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiIsImlhdCI6MTc2MTYyMjg5OCwiZXhwIjoxNzYxNjI2NDk4fQ.Oor3g2CAZGCBuE4MlSsShppcojDkbRY49GzpaBnPtAPiepkQ7fliDigUBf7r-0AapveM4muHvD4GfisiPEOtcw",
    "phones": [
        {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
        }
    ],
    "active": true
}
```

---

## ❌ Ejemplo de validación de error
Si el correo ya existe o el formato es inválido, el API devuelve:

```json
{
  "mensaje": "El correo no cuenta con el formato adecuado"
}
```

Código HTTP: 500 Internal Server Error
(o 400 Bad Request, según la configuración)

---

## 🔑 Validaciones aplicadas
- Correo: formato válido ```^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$```
- Contraseña: formato válido ```^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$```

→ Mínimo 8 caracteres, una mayúscula, una minúscula, un número y un símbolo especial.

---

## 🧠 Diagrama de Arquitectura

- El cliente (Postman o Frontend) envía solicitudes HTTP al **UserController**.
- El **UserController** delega la lógica de negocio al **UserService**.
- El **UserService** interactúa con el **UserDao** para acceder a la base de datos **H2**.
- Durante el proceso, se utilizan **ModelMapper** para transformar Dtos a Entidades, **validaciones regex** y una **utilidad JWT** para generar el token que se devuelve al usuario.

![architecture-diagram.png](diagrams/architecture-diagram.png)

---

## 🧠 Diagrama de Secuencia

### 🧩 Qué representa:

- El flujo completo del endpoint **createUser**.
- **Validaciones** previas a la creación:
- **Regex** para email y contraseña.
- **Existencia del email** en la base de datos.
- **Persistencia** del usuario en la base de datos.

![sequence-diagram.png](diagrams/sequence-diagram.png)

---

## 🧰 Estructura del proyecto

```swift
src/
 └── main/
     ├── java/com/example/demo/
     │   ├── config/            → Clases de configuración
     │   ├── controller/        → Controladores REST
     │   ├── dao/               → Clases DAO
     │   ├── model/entity/      → Entidades JPA
     │   ├── model/dto/         → Clases DTO
     │   ├── model/response/    → Clases de respuesta
     │   ├── service/           → Lógica de negocio
     │   ├── util/              → Utilidades (JWT, validaciones)
     └── resources/
         ├── application.properties
         ├── schema.sql (opcional)
```
---

## 🧪 Pruebas con Postman
Se incluye una colección de Postman con el endpoint configurado para pruebas locales.

Importar el archivo:

📁 ```postman/Demo Users.postman_collection.json```

Y ejecutar el request "Create User".

---

## 🧩 Script de Base de Datos
Se incluye el script de creación de la base de datos

Se encuentra en el archivo:

📁 ```src/main/resources/schema.sql```

---
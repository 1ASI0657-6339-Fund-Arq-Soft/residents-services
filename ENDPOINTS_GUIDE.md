# 🏥 Guía de Endpoints - Microservicio Residents (SeniorHub)

## 📋 Índice
1. [Información General](#información-general)
2. [Configuración Inicial](#configuración-inicial)
3. [Orden Recomendado de Uso](#orden-recomendado-de-uso)
4. [Endpoints de Residentes](#endpoints-de-residentes)
5. [Endpoints de Historiales Médicos](#endpoints-de-historiales-médicos)
6. [Endpoints de Medicaciones](#endpoints-de-medicaciones)
7. [Endpoints de Registros de Salud Mental](#endpoints-de-registros-de-salud-mental)
8. [Endpoints de Documentación](#endpoints-de-documentación)
9. [Ejemplos de Flujos Completos](#ejemplos-de-flujos-completos)

---

## 📚 Información General

### 🌐 Base URL
```
http://localhost:8080
```

### 🗄️ Base de Datos
- **Host**: localhost:3306
- **Schema**: residents_db
- **Usuario**: root
- **Contraseña**: (vacía)

### 🔧 Headers Requeridos
```http
Content-Type: application/json; charset=utf-8
Accept: application/json
```

---

## ⚙️ Configuración Inicial

### 1. Iniciar el Microservicio
```bash
cd "C:\Users\andre\Documents\Fundamentos - backend a microservicios\residents-service"
.\mvnw.cmd spring-boot:run
```

### 2. Verificar que está funcionando
```bash
curl http://localhost:8080/swagger-ui.html
```

---

## 📊 Orden Recomendado de Uso

### 🎯 Flujo Principal Recomendado:
1. **Crear Residente** (POST /residents)
2. **Verificar Residente** (GET /residents/{id})
3. **Agregar Medicaciones** (POST /residents/{id}/medications)
4. **Agregar Historiales Médicos** (POST /residents/{id}/medical-histories)
5. **Agregar Registros de Salud Mental** (POST /residents/{id}/mental-health-records)
6. **Ver Detalles Completos** (GET /residents/{id}/details)
7. **Actualizar si es necesario** (PUT /residents/{id})
8. **Gestionar registros específicos** (GET, DELETE según necesidad)

---

## 🏠 Endpoints de Residentes

### 1. 📝 Crear Residente
```http
POST /api/v1/residents
```

**Request Body:**
```json
{
    "dni": "12345678",
    "firstName": "Juan",
    "lastName": "Perez",
    "city": "Lima",
    "state": "Lima",
    "country": "Peru",
    "street": "Av. Lima 123",
    "zipCode": "15001",
    "birthDate": "1950-05-15T00:00:00.000Z",
    "gender": "M",
    "receiptId": 1
}
```

**Response (201 Created):**
```json
{
    "id": 1,
    "dni": "12345678",
    "firstName": "Juan",
    "lastName": "Perez",
    "city": "Lima",
    "state": "Lima",
    "country": "Peru",
    "street": "Av. Lima 123",
    "zipCode": "15001",
    "birthDate": "1950-05-15T00:00:00.000+00:00",
    "gender": "M",
    "receiptId": 1
}
```

### 2. 📋 Obtener Todos los Residentes
```http
GET /api/v1/residents
```

**Response (200 OK):**
```json
[
    {
        "id": 1,
        "dni": "12345678",
        "firstName": "Juan",
        "lastName": "Perez",
        "city": "Lima",
        "state": "Lima",
        "country": "Peru",
        "street": "Av. Lima 123",
        "zipCode": "15001",
        "birthDate": "1950-05-15",
        "gender": "M",
        "receiptId": 1
    }
]
```

### 3. 🔍 Obtener Residente por ID
```http
GET /api/v1/residents/{id}
```

**Ejemplo:**
```http
GET /api/v1/residents/1
```

**Response (200 OK):** [Mismo formato que crear residente]

### 4. ✏️ Actualizar Residente
```http
PUT /api/v1/residents/{id}
```

**Ejemplo:**
```http
PUT /api/v1/residents/1
```

**Request Body:**
```json
{
    "id": 1,
    "dni": "12345678",
    "firstName": "Juan Carlos",
    "lastName": "Perez",
    "city": "Lima",
    "state": "Lima",
    "country": "Peru",
    "street": "Av. Lima 456",
    "zipCode": "15001",
    "birthDate": "1950-05-15",
    "gender": "M",
    "receiptId": 1
}
```

### 5. 🗑️ Eliminar Residente
```http
DELETE /api/v1/residents/{id}
```

**Ejemplo:**
```http
DELETE /api/v1/residents/1
```

**Response (204 No Content):** [Sin contenido]

### 6. 🔎 Buscar Residente por DNI
```http
GET /api/v1/residents/searchByDni?dni={dni}
```

**Ejemplo:**
```http
GET /api/v1/residents/searchByDni?dni=12345678
```

### 7. 📊 Obtener Detalles Completos del Residente
```http
GET /api/v1/residents/{id}/details
```

**Ejemplo:**
```http
GET /api/v1/residents/1/details
```

**Response (200 OK):**
```json
{
    "id": 1,
    "fullName": "Juan Carlos Perez",
    "dni": "12345678",
    "medications": [
        {
            "id": 1,
            "name": "Aspirina",
            "frequency": "1 vez al dia"
        }
    ],
    "medicalHistories": [
        {
            "id": 1,
            "recordDate": "2025-10-05",
            "diagnosis": "Hipertension",
            "treatment": "Medicacion antihipertensiva"
        }
    ],
    "mentalHealthRecords": [
        {
            "id": 1,
            "recordDate": "2025-10-05",
            "diagnosis": "Ansiedad leve",
            "treatment": "Terapia cognitiva"
        }
    ]
}
```

---

## 🏥 Endpoints de Historiales Médicos

### 1. 📝 Crear Historial Médico
```http
POST /api/v1/residents/{residentId}/medical-histories
```

**Ejemplo:**
```http
POST /api/v1/residents/1/medical-histories
```

**Request Body:**
```json
{
    "date": "2025-10-05T00:00:00.000Z",
    "description": "Consulta general",
    "diagnosis": "Hipertension",
    "treatment": "Medicacion antihipertensiva"
}
```

**Response (201 Created):**
```json
[
    {
        "createdAt": "2025-10-05T15:41:44.646+00:00",
        "updatedAt": "2025-10-05T15:41:44.646+00:00",
        "id": 1,
        "recordDate": "2025-10-05",
        "diagnosis": "Hipertension",
        "treatment": "Medicacion antihipertensiva"
    }
]
```

### 2. 📋 Obtener Todos los Historiales Médicos
```http
GET /api/v1/residents/{residentId}/medical-histories
```

**Ejemplo:**
```http
GET /api/v1/residents/1/medical-histories
```

### 3. 🗑️ Eliminar Historial Médico
```http
DELETE /api/v1/residents/{residentId}/medical-histories/{historyId}
```

**Ejemplo:**
```http
DELETE /api/v1/residents/1/medical-histories/1
```

**Response (204 No Content):** [Sin contenido]

---

## 💊 Endpoints de Medicaciones

### 1. 📝 Crear Medicación
```http
POST /api/v1/residents/{residentId}/medications
```

**Ejemplo:**
```http
POST /api/v1/residents/1/medications
```

**Request Body:**
```json
{
    "name": "Aspirina",
    "frequency": "1 vez al dia"
}
```

**Response (201 Created):**
```json
{
    "createdAt": null,
    "updatedAt": null,
    "id": null,
    "name": "Aspirina",
    "frequency": "1 vez al dia"
}
```

### 2. 📋 Obtener Todas las Medicaciones
```http
GET /api/v1/residents/{residentId}/medications
```

**Ejemplo:**
```http
GET /api/v1/residents/1/medications
```

**Response (200 OK):**
```json
[
    {
        "createdAt": "2025-10-05T15:43:07.000+00:00",
        "updatedAt": "2025-10-05T15:43:07.000+00:00",
        "id": 1,
        "name": "Aspirina",
        "frequency": "1 vez al dia"
    }
]
```

### 3. 🗑️ Eliminar Medicación
```http
DELETE /api/v1/residents/{residentId}/medications/{medicationId}
```

**Ejemplo:**
```http
DELETE /api/v1/residents/1/medications/1
```

**Response (204 No Content):** [Sin contenido]

---

## 🧠 Endpoints de Registros de Salud Mental

### 1. 📝 Crear Registro de Salud Mental
```http
POST /api/v1/residents/{residentId}/mental-health-records
```

**Ejemplo:**
```http
POST /api/v1/residents/1/mental-health-records
```

**Request Body:**
```json
{
    "diagnosis": "Ansiedad leve",
    "treatment": "Terapia cognitiva"
}
```

**Response (201 Created):**
```json
[
    {
        "createdAt": "2025-10-05T15:43:30.985+00:00",
        "updatedAt": "2025-10-05T15:43:30.985+00:00",
        "id": 1,
        "recordDate": "2025-10-05",
        "diagnosis": "Ansiedad leve",
        "treatment": "Terapia cognitiva"
    }
]
```

### 2. 📋 Obtener Todos los Registros de Salud Mental
```http
GET /api/v1/residents/{residentId}/mental-health-records
```

**Ejemplo:**
```http
GET /api/v1/residents/1/mental-health-records
```

### 3. 🗑️ Eliminar Registro de Salud Mental
```http
DELETE /api/v1/residents/{residentId}/mental-health-records/{recordId}
```

**Ejemplo:**
```http
DELETE /api/v1/residents/1/mental-health-records/1
```

**Response (204 No Content):** [Sin contenido]

---

## 📚 Endpoints de Documentación

### 1. 🔍 Swagger UI
```http
GET /swagger-ui.html
```

**Descripción:** Interfaz interactiva para probar todos los endpoints

### 2. 📄 Documentación OpenAPI
```http
GET /v3/api-docs
```

**Descripción:** Especificación OpenAPI en formato JSON

---

## 🚀 Ejemplos de Flujos Completos

### 📝 Flujo 1: Registro Completo de un Nuevo Residente

#### Paso 1: Crear Residente
```bash
curl -X POST "http://localhost:8080/api/v1/residents" \
  -H "Content-Type: application/json; charset=utf-8" \
  -H "Accept: application/json" \
  -d '{
    "dni": "12345678",
    "firstName": "Juan",
    "lastName": "Perez",
    "city": "Lima",
    "state": "Lima",
    "country": "Peru",
    "street": "Av. Lima 123",
    "zipCode": "15001",
    "birthDate": "1950-05-15T00:00:00.000Z",
    "gender": "M",
    "receiptId": 1
  }'
```

#### Paso 2: Agregar Medicación
```bash
curl -X POST "http://localhost:8080/api/v1/residents/1/medications" \
  -H "Content-Type: application/json; charset=utf-8" \
  -H "Accept: application/json" \
  -d '{
    "name": "Aspirina",
    "frequency": "1 vez al dia"
  }'
```

#### Paso 3: Agregar Historial Médico
```bash
curl -X POST "http://localhost:8080/api/v1/residents/1/medical-histories" \
  -H "Content-Type: application/json; charset=utf-8" \
  -H "Accept: application/json" \
  -d '{
    "date": "2025-10-05T00:00:00.000Z",
    "description": "Consulta general",
    "diagnosis": "Hipertension",
    "treatment": "Medicacion antihipertensiva"
  }'
```

#### Paso 4: Agregar Registro de Salud Mental
```bash
curl -X POST "http://localhost:8080/api/v1/residents/1/mental-health-records" \
  -H "Content-Type: application/json; charset=utf-8" \
  -H "Accept: application/json" \
  -d '{
    "diagnosis": "Ansiedad leve",
    "treatment": "Terapia cognitiva"
  }'
```

#### Paso 5: Ver Detalles Completos
```bash
curl -X GET "http://localhost:8080/api/v1/residents/1/details" \
  -H "Accept: application/json"
```

### 🔍 Flujo 2: Consulta y Gestión de Datos Existentes

#### Paso 1: Listar Todos los Residentes
```bash
curl -X GET "http://localhost:8080/api/v1/residents" \
  -H "Accept: application/json"
```

#### Paso 2: Buscar por DNI
```bash
curl -X GET "http://localhost:8080/api/v1/residents/searchByDni?dni=12345678" \
  -H "Accept: application/json"
```

#### Paso 3: Ver Medicaciones Específicas
```bash
curl -X GET "http://localhost:8080/api/v1/residents/1/medications" \
  -H "Accept: application/json"
```

#### Paso 4: Actualizar Información del Residente
```bash
curl -X PUT "http://localhost:8080/api/v1/residents/1" \
  -H "Content-Type: application/json; charset=utf-8" \
  -H "Accept: application/json" \
  -d '{
    "id": 1,
    "dni": "12345678",
    "firstName": "Juan Carlos",
    "lastName": "Perez",
    "city": "Lima",
    "state": "Lima",
    "country": "Peru",
    "street": "Av. Lima 456",
    "zipCode": "15001",
    "birthDate": "1950-05-15",
    "gender": "M",
    "receiptId": 1
  }'
```

### 🗑️ Flujo 3: Eliminación de Datos

#### Paso 1: Eliminar Medicación Específica
```bash
curl -X DELETE "http://localhost:8080/api/v1/residents/1/medications/1" \
  -H "Accept: application/json"
```

#### Paso 2: Eliminar Historial Médico
```bash
curl -X DELETE "http://localhost:8080/api/v1/residents/1/medical-histories/1" \
  -H "Accept: application/json"
```

#### Paso 3: Eliminar Registro de Salud Mental
```bash
curl -X DELETE "http://localhost:8080/api/v1/residents/1/mental-health-records/1" \
  -H "Accept: application/json"
```

#### Paso 4: Eliminar Residente Completo
```bash
curl -X DELETE "http://localhost:8080/api/v1/residents/1" \
  -H "Accept: application/json"
```

---

## 🏷️ Códigos de Respuesta HTTP

| Código | Descripción | Cuándo se usa |
|--------|-------------|---------------|
| 200 | OK | GET exitosos |
| 201 | Created | POST exitosos |
| 204 | No Content | DELETE exitosos |
| 400 | Bad Request | Datos inválidos |
| 404 | Not Found | Recurso no existe |
| 500 | Internal Server Error | Error del servidor |

---

## ⚠️ Notas Importantes

### 🔤 Encoding
- Usar `charset=utf-8` en Content-Type para caracteres especiales
- Evitar acentos en nombres para pruebas iniciales

### 🗄️ Base de Datos
- El microservicio crea automáticamente las tablas
- Los IDs se autogeneran (autoincrement)
- Las fechas se almacenan en formato ISO 8601

### 🔗 Relaciones
- Un residente puede tener múltiples medicaciones
- Un residente puede tener múltiples historiales médicos
- Un residente puede tener múltiples registros de salud mental
- Al eliminar un residente se eliminan todos sus registros relacionados

### 🛡️ CORS
- Configurado para permitir todas las origins (`*`)
- Métodos permitidos: GET, POST, PUT, DELETE
- Headers permitidos para desarrollo

---

## 🎯 Resumen de Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **RESIDENTES** |
| GET | `/api/v1/residents` | Listar todos |
| GET | `/api/v1/residents/{id}` | Obtener por ID |
| POST | `/api/v1/residents` | Crear nuevo |
| PUT | `/api/v1/residents/{id}` | Actualizar |
| DELETE | `/api/v1/residents/{id}` | Eliminar |
| GET | `/api/v1/residents/searchByDni?dni=X` | Buscar por DNI |
| GET | `/api/v1/residents/{id}/details` | Detalles completos |
| **HISTORIALES MÉDICOS** |
| GET | `/api/v1/residents/{id}/medical-histories` | Listar |
| POST | `/api/v1/residents/{id}/medical-histories` | Crear |
| DELETE | `/api/v1/residents/{id}/medical-histories/{histId}` | Eliminar |
| **MEDICACIONES** |
| GET | `/api/v1/residents/{id}/medications` | Listar |
| POST | `/api/v1/residents/{id}/medications` | Crear |
| DELETE | `/api/v1/residents/{id}/medications/{medId}` | Eliminar |
| **SALUD MENTAL** |
| GET | `/api/v1/residents/{id}/mental-health-records` | Listar |
| POST | `/api/v1/residents/{id}/mental-health-records` | Crear |
| DELETE | `/api/v1/residents/{id}/mental-health-records/{recId}` | Eliminar |
| **DOCUMENTACIÓN** |
| GET | `/swagger-ui.html` | Swagger UI |
| GET | `/v3/api-docs` | OpenAPI Docs |

---

**Total: 18 Endpoints Funcionales** ✅

---

*Documento generado para el microservicio SeniorHub Residents*  
*Fecha: Octubre 2025*  
*Versión: 1.0*
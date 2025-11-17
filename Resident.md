# 🏥 Guía de Pruebas API - Microservicio Residents (SeniorHub)

## 📋 Información General

### 🌐 Configuración del Servicio
- **Puerto**: 8081
- **Base URL**: `http://localhost:8081`
- **Swagger UI**: `http://localhost:8081/swagger-ui.html`
- **OpenAPI Docs**: `http://localhost:8081/v3/api-docs`
- **Base de Datos**: `seniorhub_residents`

### 📚 Tags de Endpoints
- **Residents**: Gestión de residentes principales
- **Medical Histories**: Historiales médicos de residentes 
- **Medications**: Gestión de medicamentos
- **Mental Health Records**: Registros de salud mental

---

## 🚀 Orden Recomendado de Pruebas

### 1️⃣ Configuración Inicial
Antes de probar los endpoints, asegúrate de que:
- El microservicio esté ejecutándose en puerto 8081
- MySQL esté funcionando con la base de datos `seniorhub_residents`
- Swagger UI esté accesible

---

## 🏠 Endpoints de Residentes

### **POST** `/api/v1/residents` - Crear Residente
**Orden**: 🥇 **PRIMERO** - Necesario para todas las demás operaciones

**Cuerpo de la Petición**:
```json
{
  "dni": "12345678",
  "firstName": "María",
  "lastName": "González",
  "city": "Lima",
  "state": "Lima",
  "country": "Perú",
  "street": "Av. Principal 123",
  "zipCode": "15001",
  "birthDate": "1950-05-15T00:00:00.000Z",
  "gender": "Female",
  "receiptId": 1
}
```

**Resultado Esperado**: Status 201, retorna el residente creado con ID asignado
**Notas**: Guarda el `id` retornado para usarlo en siguientes pruebas

---

### **GET** `/api/v1/residents` - Obtener Todos los Residentes
**Orden**: 🥈 **SEGUNDO** - Verificar que el residente se creó

**Parámetros**: Ninguno
**Resultado Esperado**: Status 200, lista con el residente creado

---

### **GET** `/api/v1/residents/{residentId}` - Obtener Residente por ID
**Orden**: 🥉 **TERCERO** - Probar búsqueda específica

**Parámetros**: 
- `residentId`: ID del residente creado anteriormente

**Resultado Esperado**: Status 200, datos del residente específico

---

### **GET** `/api/v1/residents/searchByDni` - Buscar por DNI
**Orden**: 4️⃣ **CUARTO** - Probar búsqueda alternativa

**Parámetros Query**: 
- `dni`: "12345678" (el DNI usado en la creación)

**Resultado Esperado**: Status 200, datos del residente

---

### **GET** `/api/v1/residents/{residentId}/details` - Obtener Detalles Completos
**Orden**: 5️⃣ **QUINTO** - Información completa con relaciones

**Parámetros**: 
- `residentId`: ID del residente

**Resultado Esperado**: Status 200, información detallada incluyendo medicamentos, historial médico y registros de salud mental

---

### **PUT** `/api/v1/residents/{residentId}` - Actualizar Residente
**Orden**: 6️⃣ **SEXTO** - Modificar datos existentes

**Parámetros**: 
- `residentId`: ID del residente

**Cuerpo de la Petición**:
```json
{
  "id": 1,
  "dni": "12345678",
  "fullName": "María González Rodríguez",
  "address": "Av. Principal 456, Lima, Lima, Perú, 15001",
  "birthDate": "1950-05-15T00:00:00.000Z",
  "gender": "Female",
  "receiptId": 1
}
```

**Resultado Esperado**: Status 200, residente actualizado

---

### **DELETE** `/api/v1/residents/{residentId}` - Eliminar Residente
**Orden**: 🔚 **ÚLTIMO** - Solo si quieres limpiar datos

**Parámetros**: 
- `residentId`: ID del residente

**Resultado Esperado**: Status 204, sin contenido

---

## 🏥 Endpoints de Historiales Médicos

### **POST** `/api/v1/residents/{residentId}/medical-histories` - Crear Historial
**Orden**: Después de crear residente

**Parámetros**: 
- `residentId`: ID del residente existente

**Cuerpo de la Petición**:
```json
{
  "condition": "Hipertensión",
  "diagnosis": "Hipertensión arterial esencial",
  "treatment": "Medicación antihipertensiva",
  "recordDate": "2024-01-15T00:00:00.000Z"
}
```

---

### **GET** `/api/v1/residents/{residentId}/medical-histories` - Obtener Historiales

**Parámetros**: 
- `residentId`: ID del residente

**Resultado Esperado**: Status 200, lista de historiales médicos

---

## 💊 Endpoints de Medicamentos

### **POST** `/api/v1/residents/{residentId}/medications` - Crear Medicamento

**Cuerpo de la Petición**:
```json
{
  "name": "Losartán",
  "dosage": "50mg",
  "frequency": "Una vez al día",
  "startDate": "2024-01-15T00:00:00.000Z",
  "endDate": "2024-12-31T00:00:00.000Z"
}
```

---

### **GET** `/api/v1/residents/{residentId}/medications` - Obtener Medicamentos

**Resultado Esperado**: Status 200, lista de medicamentos del residente

---

## 🧠 Endpoints de Salud Mental

### **POST** `/api/v1/residents/{residentId}/mental-health-records` - Crear Registro

**Cuerpo de la Petición**:
```json
{
  "assessment": "Evaluación cognitiva normal",
  "notes": "Paciente muestra buen estado de ánimo",
  "recordDate": "2024-01-15T00:00:00.000Z"
}
```

---

### **GET** `/api/v1/residents/{residentId}/mental-health-records` - Obtener Registros

**Resultado Esperado**: Status 200, lista de registros de salud mental

---

## 🎯 Flujo de Prueba Completo Recomendado

1. **Crear Residente** → Obtener ID
2. **Listar Residentes** → Verificar creación
3. **Buscar por ID** → Confirmar datos
4. **Buscar por DNI** → Probar búsqueda alternativa
5. **Crear Historial Médico** → Agregar información médica
6. **Crear Medicamento** → Agregar medicación
7. **Crear Registro Mental** → Agregar salud mental
8. **Obtener Detalles Completos** → Ver toda la información
9. **Actualizar Residente** → Modificar datos
10. **Eliminar** (opcional) → Limpiar datos

---

## ⚡ Headers Importantes

```http
Content-Type: application/json
Accept: application/json
```

---

## 🏷️ Códigos de Respuesta

- **200**: OK - Operación exitosa
- **201**: Created - Recurso creado exitosamente
- **204**: No Content - Eliminación exitosa
- **400**: Bad Request - Datos inválidos
- **404**: Not Found - Recurso no encontrado

---

## ⚠️ Notas Importantes

1. **Dependencias**: Los endpoints de medical-histories, medications y mental-health-records requieren un residente existente
2. **IDs**: Guarda los IDs generados para usar en endpoints relacionados
3. **Fechas**: Usa formato ISO 8601 (`YYYY-MM-DDTHH:mm:ss.sssZ`)
4. **Validaciones**: Todos los campos requeridos deben proporcionarse
5. **Base de Datos**: Asegúrate de que la base de datos `seniorhub_residents` exista y esté accesible

---

## 🔗 Enlaces Útiles

- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8081/v3/api-docs
- **Health Check**: http://localhost:8081/actuator/health (si está habilitado)
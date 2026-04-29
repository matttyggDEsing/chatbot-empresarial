# 🍽️ Chatbot Empresarial con IA

Asistente virtual inteligente para negocios, construido con **Java Spring Boot**, **LLaMA 3 (Groq)** y **Supabase**. Responde preguntas de clientes en tiempo real y guarda el historial de conversaciones en la nube.

---

## 📸 Preview

> Interfaz web incluida — disponible en `http://localhost:8080` al levantar el servidor.

---

## ✨ Features

- 🤖 Respuestas inteligentes usando **LLaMA 3** vía Groq API
- 💾 Historial de conversaciones guardado en **Supabase (PostgreSQL)**
- 🌐 API REST lista para integrar en cualquier frontend
- 🖥️ Interfaz web incluida con diseño profesional
- ⚡ Respuestas en menos de 2 segundos
- 🔧 Contexto del negocio 100% configurable

---

## 🛠️ Stack

| Tecnología | Uso |
|---|---|
| Java 17 + Spring Boot 3.2 | Backend y API REST |
| Groq API (LLaMA 3.3 70B) | Motor de IA |
| Supabase (PostgreSQL) | Base de datos en la nube |
| OkHttp + Gson | Cliente HTTP y parsing JSON |
| HTML / CSS / JS | Interfaz web del chat |

---

## 📁 Estructura del proyecto

```
src/main/
├── java/com/chatbot/chatbot/
│   ├── controller/
│   │   └── ChatController.java       # Endpoints REST
│   ├── model/
│   │   └── Conversation.java         # Entidad JPA
│   ├── repository/
│   │   └── ConversationRepository.java
│   ├── service/
│   │   ├── ChatService.java          # Integración con Groq API
│   │   └── ConversationService.java  # Lógica de historial
│   └── ChatbotEmpresarialApplication.java
└── resources/
    ├── static/
    │   └── index.html                # Interfaz web del chat
    └── application.properties
```

---

## 🚀 Cómo correrlo

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/chatbot-empresarial.git
cd chatbot-empresarial
```

### 2. Configurar variables en `application.properties`

```properties
server.port=8080

# Groq API
groq.api.key=TU_CLAVE_DE_GROQ

# Supabase (PostgreSQL)
spring.datasource.url=jdbc:postgresql://TU_HOST.pooler.supabase.com:5432/postgres
spring.datasource.username=postgres.TU_PROJECT_REF
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=none
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### 3. Crear la tabla en Supabase

Ejecutar en el **SQL Editor** de Supabase:

```sql
CREATE TABLE conversations (
    id BIGSERIAL PRIMARY KEY,
    session_id VARCHAR(100) NOT NULL,
    user_message TEXT NOT NULL,
    bot_response TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);
```

### 4. Levantar el servidor

```bash
mvn spring-boot:run
```

El servidor queda disponible en `http://localhost:8080`.

---

## 📡 API Endpoints

### `POST /api/chat`

Envía un mensaje y recibe la respuesta del asistente.

**Request:**
```json
{
  "message": "¿Cuál es el horario?",
  "sessionId": "sesion-001"
}
```

**Response:**
```json
{
  "response": "Estamos abiertos de lunes a sábado de 12:00 a 23:00hs. ¿En qué más puedo ayudarte?"
}
```

---

### `GET /api/chat/history/{sessionId}`

Devuelve el historial completo de una sesión.

**Response:**
```json
[
  {
    "id": 1,
    "sessionId": "sesion-001",
    "userMessage": "¿Cuál es el horario?",
    "botResponse": "Estamos abiertos de lunes a sábado...",
    "createdAt": "2026-04-29T08:30:00"
  }
]
```

---

## ⚙️ Personalización del negocio

Para adaptar el chatbot a cualquier negocio, modificar la constante `BUSINESS_CONTEXT` en `ChatController.java`:

```java
private static final String BUSINESS_CONTEXT =
    "Nombre del negocio. " +
    "Horario: ... " +
    "Servicios: ... " +
    "Dirección: ... " +
    "Teléfono: ...";
```

---

## 🔑 Obtener API Keys

| Servicio | Link | Costo |
|---|---|---|
| Groq (LLaMA 3) | [console.groq.com](https://console.groq.com) | Gratis |
| Supabase | [supabase.com](https://supabase.com) | Gratis |

---

## 📬 Contacto

Desarrollado por **[Matias]** · Disponible para proyectos freelance en [Workana](https://workana.com) y [Fiverr](https://fiverr.com).

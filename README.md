# ⚡ Demo PokeAPI + Hazelcast Cache

> Proyecto de ejemplo construido con **Spring Boot**, **OpenFeign** y **Hazelcast** para consultar información de Pokémon desde **PokeAPI** y evitar llamadas repetidas usando caché.

---

## 📌 Resumen

Este repositorio expone un endpoint REST que permite consultar un Pokémon por nombre.

La primera vez que se consulta un nombre, la aplicación realiza una llamada real a **PokeAPI** mediante un cliente **Feign**.  
Las siguientes consultas para el mismo Pokémon se resuelven desde **Hazelcast**, evitando volver a invocar la API externa.

---

## 🧱 Stack tecnológico

- Java 25
- Spring Boot
- Spring Web
- Spring Cache
- Spring Cloud OpenFeign
- Hazelcast
- Lombok
- Maven

---

## 🎯 Objetivo

El objetivo principal del proyecto es demostrar:

- cómo consumir una API externa con **OpenFeign**
- cómo cachear respuestas con **Spring Cache**
- cómo usar **Hazelcast** como proveedor de caché
- cómo reducir llamadas reales a servicios externos
- cómo dejar una base simple para pruebas de rendimiento y optimización

---

## 🏗️ Arquitectura simple

```text
Cliente
   |
   v
PokemonController
   |
   v
PokemonService
   |
   v
@Cacheable
   |
   +--> si existe en caché: retorna desde Hazelcast
   |
   +--> si no existe: llama a PokemonClient --> PokeAPI
```

---

## 📂 Estructura principal

### `PokemonController`

Controlador REST que expone el endpoint:

```text
GET /api/pokemon/{name}
```

Responsabilidades:

- recibir la solicitud HTTP
- registrar logs de entrada y salida
- delegar la lógica al servicio

---

### `PokemonService`

Interfaz que define el contrato de consulta de Pokémon.

Método principal:

```java
PokemonResponse getPokemon(String pokemonName);
```

---

### `PokemonServiceImpl`

Implementación del servicio.

Responsabilidades:

- aplicar caché con `@Cacheable`
- llamar al cliente Feign si el dato no está cacheado
- registrar logs del flujo
- retornar la respuesta al controlador

Anotación usada:

```java
@Cacheable(cacheNames = "cacheServicePokeApi")
```

Esto significa que Spring intentará resolver la respuesta desde la caché antes de ejecutar el método.

---

### `PokemonClient`

Cliente Feign encargado de consumir la API externa de Pokémon.

Su responsabilidad es encapsular la llamada HTTP hacia **PokeAPI**.

---

## 🧠 ¿Cómo funciona la caché?

Cuando se consulta por primera vez un Pokémon, por ejemplo:

```text
GET /api/pokemon/pikachu
```

ocurre lo siguiente:

1. entra la request al controlador
2. el servicio intenta resolver el resultado desde la caché
3. como no existe, se ejecuta el método real
4. se llama a **PokeAPI**
5. la respuesta se guarda en Hazelcast
6. se devuelve la respuesta al cliente

Si luego se vuelve a consultar:

```text
GET /api/pokemon/pikachu
```

Spring devolverá el valor cacheado y no ejecutará nuevamente la llamada real.

---

## 🚀 Endpoint disponible

### Consultar un Pokémon por nombre

```text
GET /api/pokemon/{name}
```

### Ejemplo

```bash
curl --location 'http://localhost:8080/api/pokemon/pikachu'
```

---

## 📝 Logs esperados

Cuando la consulta **no está en caché**, debería verse algo parecido a esto:

```text
INFO [demo,traceId=] Solicitud recibida para consultar el Pokémon con nombre: pikachu
INFO [demo,traceId=] Realizando llamada REAL (sin caché) a PokeApi, para el pokemón = pikachu
INFO [demo,traceId=] Consultando información del Pokémon = 'pikachu'
INFO [demo,traceId=] Respuesta recibida desde PokeAPI. pokemon = '...'
INFO [demo,traceId=] Pokémon consultado correctamente: pikachu
```

Cuando la consulta **ya está en caché**, no debería aparecer el log de llamada real a PokeAPI, porque el método cacheado no se ejecuta de nuevo.

---

## ⚙️ Configuración relevante

### `application.properties`

```properties
spring.application.name=demo
server.port=8080

logging.pattern.level=%5p [${spring.application.name:},traceId=%X{traceId:-}]
```

### Explicación rápida

- `spring.application.name=demo`: nombre de la aplicación
- `server.port=8080`: puerto del servicio
- `logging.pattern.level=...`: personaliza el log para incluir nombre de la aplicación y `traceId` si existe en `MDC`

---

## 📦 Dependencias principales

El proyecto utiliza estas dependencias destacadas:

- `spring-boot-starter-web`
- `spring-cloud-starter-openfeign`
- `spring-boot-starter-cache`
- `hazelcast`
- `hazelcast-spring`
- `lombok`

---

## ✅ Beneficios de esta implementación

- reduce llamadas innecesarias a PokeAPI
- mejora el tiempo de respuesta en consultas repetidas
- disminuye consumo de red
- simplifica la integración con APIs externas
- demuestra de forma clara el uso de caché con Hazelcast

---

## ⚠️ Consideraciones

- la caché funciona por clave, en este caso el nombre del Pokémon
- si cambia el dato en la API externa, el valor cacheado podría quedar desactualizado hasta ser invalidado
- para entornos reales conviene definir tiempos de expiración, tamaño máximo y estrategia de invalidación
- el `traceId` aparece en el patrón de log, pero en este proyecto solo se imprimirá si existe en `MDC`

---

## ▶️ Ejecución del proyecto

### Levantar la aplicación

```bash
mvn spring-boot:run
```

### Probar el endpoint

```bash
curl --location 'http://localhost:8080/api/pokemon/pikachu'
```

---

## 📚 Conclusión

Este proyecto es una demo simple y útil para entender cómo combinar:

- **Spring Boot**
- **OpenFeign**
- **Spring Cache**
- **Hazelcast**

para consumir una API externa de forma más eficiente, cacheando respuestas y evitando llamadas repetidas innecesarias a **PokeAPI**.
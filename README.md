# Weather API

## Overview

This project is a simple backend REST API built with Java 21 and Spring Boot.  
It retrieves weather information for a given ZIP code using public, open-source services.

---

## What the API Does

- Receives a ZIP code as input
- Retrieves latitude and longitude using a public geocoding service
- Retrieves current weather data for that location
- Returns:
    - Current temperature
    - Minimum temperature
    - Maximum temperature
- Caches results for 15 minutes
- Indicates whether the response came from cache

---

## Tech Stack

- Java 21
- Spring Boot 3.5.10
- Spring MVC
- WebClient (used only as an HTTP client)
- Spring Cache
- Caffeine
- Maven

---

## Architecture

The application follows a standard layered structure:

Controller → Service → External API Clients

- Controllers handle HTTP requests and responses
- Services contain the application logic
- Clients are responsible for calling external APIs

---

## Design Decisions

- Errors caused by invalid ZIP codes are handled separately from external service failures, returning different HTTP status codes to clearly distinguish user input issues from infrastructure problems.
- Logging is intentionally kept at debug level to support troubleshooting without increasing log volume in production environments.
- Unit tests focus on service and controller layers to validate business logic and HTTP contracts without testing framework internals.

---

## External Services Used

### Geocoding
- Service: Nominatim (OpenStreetMap)
- Purpose: Convert ZIP codes into latitude and longitude
- Authentication: Not required

### Weather
- Service: Open-Meteo
- Purpose: Retrieve weather data based on coordinates
- Authentication: Not required

---

## Caching

- Cache is applied per ZIP code
- Cache duration: 15 minutes
- In-memory cache using Caffeine
- The API response includes a flag indicating cache usage

---

## Endpoint

GET /weather/{zipCode}

### Example Request

```bash
curl -X GET http://localhost:8080/weather/90210
```

### Example Response

```bash
{
"zipCode": "90210",
"currentTemperature": 22.4,
"minTemperature": 18.1,
"maxTemperature": 26.7,
"fromCache": false
}
```

---

## Configuration

Configuration is defined in application.yml:

```yaml
spring:
  application:
    name: weather-api

clients:
  geocoding:
    base-url: https://nominatim.openstreetmap.org
  weather:
    base-url: https://api.open-meteo.com
```

---

## Running the Application

Requirements:
- Java 21
- Maven

Run the application with:

```bash
mvn spring-boot:run
```

The API will be available on port 8080.

---

## Future Improvements

To evolve the project, the API could be extended to support country-aware ZIP code searches,
additional forecast details, and improved observability metrics.

---

## Notes

- The implementation intentionally avoids unnecessary complexity
- Error handling is minimal and focused on the main use case
- The project is meant to be easy to read and understand

---

Developed by @GuilhermeBolonha
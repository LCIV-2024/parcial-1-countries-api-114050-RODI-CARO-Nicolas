[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/_RotgeOK)
# Parcial 1 - Paises

## Contexto

### ¿Qué es la API de países?

La API de países proporciona información detallada sobre diferentes países y lenguajes hablados en el mundo. Permite
consultar datos como nombres de países, códigos, continentes y lenguajes, facilitando el acceso a una amplia variedad de
datos geográficos y culturales.
Estructura de la API

La API está estructurada para exponer datos de países y lenguajes a través de endpoints RESTful.

## Consigna

### Precondiciones y Aclaraciones

    Para este parcial utilizaremos la API de países que se encuentra en https://restcountries.com/v3.1/all

### Requerimientos Funcionales

El parcial consta de 5 endpoints que hay que desarrollar. Son 4 endpoints de tipo GET y un POST.

### 1. Crear un endpoint que exponga todos los países. <span style="color:red">(5 puntos)</span>

Request

```http
curl --location 'http://localhost:8080/api/countries'
```

**Response**

```json

[
  {
    "code": "ARG",
    "name": "Argentina"
  },
  {
    "code": "BRA",
    "name": "Brazil"
  },
  {
    "code": "USA",
    "name": "United States"
  },
  {
    ...
  }
]
```

### 2. El endpoint anterior debe permitir filtrar la lista tanto por nombre como por codigo. <span style="color:red">(5 puntos)</span>

Request

```http
curl --location 'http://localhost:8080/api/countries?name=argentina'
```

**Response**

```json

[
  {
    "code": "ARG",
    "name": "Argentina"
  }
]
```

**Request**

```http

curl --location 'http://localhost:8080/api/countries?code=bra'
```

**Response**

```json

[
  {
    "code": "BRA",
    "name": "Brazil"
  }
]
```

### 3. Crear un endpoint que exponga todos paises que se encuentren dentro de un continente pasandoselo por parametro. <span style="color:red">(5 puntos)</span>

Los continenes posibles son:

1. Africa
2. Americas
3. Asia
4. Europe
5. Oceania
6. Antarctic

**Request**

```http

curl --location 'http://localhost:8080/api/countries/{continent}/continent

```

### 4. Crear un endpoint que exponga todos paises que hablen un idioma pasandoselo por parametro. <span style="color:red">(10 puntos)</span>

Los idiomas posibles son:

1. English
2. Spanish
3. French
4. German
5. Portuguese
6. Chinese
7. Arabic
8. Russian
9. Hindi
10. Swahili

**Request**

```http

curl --location 'http://localhost:8080/api/countries/{language}/language

```

**Response**

```json

[
  {
    "code": "KIR",
    "name": "Kiribati"
  },
  {
    "code": "ASM",
    "name": "American Samoa"
  },
  {
    "code": "NZL",
    "name": "New Zealand"
  }
]
```

### 6. Crear un endpoint que traiga el nombre del pais con mas fronteras <span style="color:red">(5 puntos)</span>

**Request**

```http

curl --location 'http://localhost:8080/api/countries/most-borders

```

**Response**

```json
{
  "code": "CHN",
  "name": "China"
}
```

### 7. Duplicar el endpoint creado en el primer punto, pero que sea de tipo post y reciba un body de tipo <span style="color:red">(20 puntos):

**Request POST**

```http
curl --location 'http://localhost:8080/api/countries' \
--header 'Content-Type: application/json' \
--data-raw '{
    "amountOfCountryToSave": 10
}'
```

```json
 {
  "amountOfCountryToSave": 10
}
```

**Response**

```json

[
  {
    "code": "KIR",
    "name": "Kiribati"
  },
  {
    "code": "ASM",
    "name": "American Samoa"
  },
  {
    "code": "NZL",
    "name": "New Zealand"
  }
]
```

#### En resumen el endpoint deberia:

a. Obtener todos los países desde la API: El endpoint debe realizar una solicitud a una API externa que devuelva
información sobre todos los países disponibles.

b. Seleccionar y procesar la cantidad indicada: De la lista de países obtenida, se debe seleccionar la cantidad
especificada como parámetro (que no debe ser mayor a 10). Una vez seleccionados, se deben ordenar estos países de manera
aleatoria.

c. Guardar en la base de datos: Luego, se debe guardar en la base de datos los países seleccionados, incluyendo los
campos de nombre, código, población (population) y área (Ademas de un id autoincremental).

d. Devolver los países insertados: Finalmente, se debe devolver el nombre y el código de los países que fueron
insertados en la base de datos.

### 8. Crear los Test para que la aplicación tenga un 50% de cobertura. <span style="color:red">(40 puntos)</span>

### 9. Entregar el proyecto con el archivo Dockerfile que permita ejecutar la aplicación en un contenedor.<span style="color:red">(5 puntos)</span>

### 10. Entregar el proyecto con un archivo docker-compose para poder ejecutar el servicio creado. <span style="color:red">(5 puntos)</span>



# Proyecto Challenge Mutante __Version 1.0.0.RELEASE__


Este microservicio se encarga de validar si una persona es un Mutante a partir de su secuencia de ADN.


## Pre-requisitos

1. Java JDK 8
3. IDE con soporte a Java y Spring Boot 2.5.4.RELEASE
4. GIT

## Instalación

Para obtener una copia de este proyecto ejecute el siguiente comando.

```bash
git clone  https://github.com/dannny84/MutantChallenge.git
```

## Este proyecto incluye
1. Swagger
2. Log4j
3. Junit - Coverage
4. Lombok
5. Bucket4j
6. H2 (Base de Datos en Memoria)
7. Despliegue en Heroku


___
## Uso

Lo primero que debes conocer es el uso del archivo __application.yml__, el cual es usado para las configuraciones mas relevantes del proyecto.

> Ubicación del archivo: \src\main\resources\application.yml


Adicionalmente se configura un archivo yml para ambiente de desarrollo:

> Ubicación del archivo: \src\main\resources\application-dev.yml


En estos archivos se configuran propiedades especifias para cada ambiente, para ejecutar el ambiente correspondiente ejecute:

```bash
mvn clean install
java -jar -Dspring.profiles.active=dev mutant-api-0.0.1-SNAPSHOT.jar 
```

Nota: También puede configurarlo como variable del sistema:

```bash
export spring_profiles_active=dev
```

## Sección Server

En esta sección podras ver la configuración del servidor, donde se le indica el puerto de salida __```port: 8080```__, al igual que el path inicial __```servlet-path: /challenge/v1.0/```__, al iniciar la aplicacion podra acceder a ella desde su navegador ingresando a la url __http://localhost:8084/challenge/v1.0/__ 

```yaml
server:
	port: 8084
	servlet:
		context-path: /challenge/v1.0/
```

## Sección Base de Datos

Aqui podemos encontrar la informacion relacionada con la base de datos que usa el API para almacenar las secuencias de ADN validadas.

```yaml
spring:
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
  datasource:
    url: jdbc:h2:mem:challengedb
    driverClassName: org.h2.Driver
    username: sa
    password: sa
```

***- Accediendo a la base de datos:*** Cuando desplegamos la aplicacion, se creará la tabla __CH_HUMAN__ y para realizar consultas debemos ingresar al link (http://localhost:8084/challenge/v1.0/h2-console). Esta base de datos estará cargada en memoria.


## Sección Implementación

Esta sección contiene sub-secciones con las cuales podra interactuar y modificar a la necesidad del desarrollo.

### 1. Swagger

En este apartado podrá configurar propiedades del swagger.

```yaml
  swagger:
    version: "v1.0"
    title: "Challenge MercadoLibre"
    base-package: "com.mercadolibre.challenge.controller"    
  ...
```

__- base-package__: El campo base-package indica el paquete principal del proyecto a analizar; por defecto este es nombrado __com.mercadolibre.challenge.controller__ y es alli donde se encuentran ubicados las clases controller.


Para acceder a swagger de manera local deberá ingresar al link (http://localhost:8084/challenge/v1.0/swagger-ui.html).

Para acceder a swagger en el ambiente desplegado, ingresar a la url creada en el servidor __Heroku__ donde está hosteado el proyecto (https://mutant-api-challenge.herokuapp.com/challenge/v1.0/swagger-ui.html)



### 2. Rate Limiting

En este apartado podrá indicar el numero máximo de peticiones (security.tokens.max) que soportará el API, y la duracion de las mismas en segundos (security.tokens.duration).

```yaml
security:
  tokens:
    max: 100
    duration: 1
```


### 3. Logger
La aplicación incluye un manejo de log por medio de Log4j de la libreria apache, se incluye el archivo de configuración en la carpeta resource, 
este log esta configurado para la consola y para exportarse a un archivo .


```properties
	...

	log4j.appender.default.file=org.apache.log4j.DailyRollingFileAppender
	log4j.appender.default.file.append=true
	log4j.appender.default.file.file=./logs/mutant-api.log
	log4j.appender.default.file.threshold=INFO
	log4j.appender.default.file.layout=org.apache.log4j.PatternLayout
	log4j.appender.default.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %p - %m%n
	log4j.appender.default.file.DatePattern='.'yyyy-MM-dd

  ...
```

***- Ubicación del archivo:*** En la propiedad log4j.appender.default.file.file se especifica la ruta donde será guardado el archivo. Genera un nuevo archivo cada día.


---
## Despliegue

Para desplegar la aplicación siga los siguientes comandos.

```bash
mvn clean install
java -jar mutant-api-0.0.1-SNAPSHOT.jar
```

---
## Concurrencia

Este proyecto puede ser desplegado en una infraestructura que permita la escalabilidad del mismo.

---
## Autor

* ***Danny Londoño***


# Dux Challenge

#### Hecho por Federico Markus (www.linkedin.com/in/federicomarkus)

## Documentación: https://docs.google.com/document/d/1PzDo1N9rhitZy8NZi_I4gGBXAlHxpugLmuT9Ncq8gTo/edit

## Consideraciones generales

### Swagger

- http://localhost:8080/swagger-ui/index.html#/

### Auth

- Se creo la entidad en la base de datos Usuario para realizar la autenticación.
- Para facilitar no se encodeo la contraseña.

### Liquibase

- Se uso Liquibase para ejecutar automaticamente scripts de SQL al iniciar la aplicación y asi levantar las tablas y los inserts de las mismas.

### Lombok

- Se utilizo la libreria de Lombok para facilitar los getters, setters o builders de las entidades.
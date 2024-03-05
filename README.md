# PRACTICA SISTEMAS ORIENTADOS A SERVICIOS

# Titulo:
Diseño e implementación de una API REST y un prototipo funcional de un servicio sencillo.

# Objetivo:
1. Abordar el diseño RESTful de un servicio sencillo, aplicando las mejores prácticas y las recomendaciones explicadas en las clases de la asignatura.

2. Saber implementar ese servicio usando el entorno Eclipse + Tomcat y la implementación de referencia de la API JAX-RS (Jersey). Posteriormente verificar su correcto funcionamiento con un cliente REST (Postman) y desarolle su propio cliente en Java

## Características
-  El servicio simulará una aplicación de una comunidad online para la gestión y recomendación de vinos.
- Los usuarios de esta comunidad podrán interactuar entre ellos de diversas formas.
- Asumimos que la AUTENTICACIÓN y SEGURIDAD de la herramienta está realizada (no debemos implementarla)

## Herramientas utilizadas

Entornos de desarollo:
- VSCode: -
- Eclipse + Apache Tomcat: Entorno de desarrollo integrado para desarrollar y desplegar aplicaciones web Java utilizando el servidor web Tomcat.

APIs:
- Java JAX-RS: Especificación de Java que facilita la creación de servicios web RESTful utilizando el lenguaje de programación Java

Librerías
- más: 
- java.sql.Connection: Interfaz en Java que representa una conexión de base de datos, permitiendo establecer comunicación y realizar operaciones de consulta y actualización en una base de datos utilizando JDBC (Java Database Connectivity).

Clientes:
- Postman: Herramienta que permite probar, documentar y colaborar en servicios web, APIs y servicios RESTful mediante solicitudes HTTP, proporcionando un entorno amigable para la interacción con APIs.

Frameworks:
- SWAGGER: Facilita la documentación, diseño y prueba de APIs RESTful, proporcionando una interfaz para describir la estructura y funcionalidad de los servicios web de manera precisa y legible.

Base de Datos:
- MySQL: Sistema de gestión de bases de datos relacional de código abierto ampliamente utilizado que permite almacenar, organizar y manipular datos de manera eficiente a través de consultas SQL.

Control de versiones:
- GitHub: Plataforma de desarrollo colaborativo que permite alojar y revisar código, controlar versiones, gestionar proyectos y colaborar en software utilizando el sistema de control de versiones Git.

## Especificaciones
El servicio debe permitir:
- Puntuar vinos
- Seguir a otros usuarios
- Realizar búsquedas sobre vinos concretos
- Descubrir reocmendaciones basadas en sus gustos personales

Los vinos están representados al menos por:
- El nombre de la botella
- Bodega a la que pertenece
- Añada de la bodega
- Denominación
- Origeen (país o región)
- Tipo de vino (tinto, blanco, rosado, etc)
- Tipos de uva (formado por varios tipos de uva en porcentaje)

## Operaciones
Se debe implementar a través de la API:

(Propiedades de un user) Un user puede:

- Añadir un usuario nuevo. Sus datos básicos necesarios son:
    - Nombre de usuario
    - Fecha de nacimiento (+18)
        Si user < 18 --> No podrá registrarse y deberá devolver mensaje de error
    - Correo electrónico
- Ver sus datos básicos
- Cambiar sus datos básicos del perfil
- Borrar el perfil
- Obtener una lista de todos los usuarios en la aplicacion.
    Debe permitir ser filtrada por:
    - patrón de nombre*

(Propiedades de un vino)

- Añadir un vino a su lista + agregarle una puntuacion (0 - 10)
- Eliminar vinos de su lista personal
- Modificar la puntuacion de un vino de su lista
- Obtener una lista con todos los vinos de un usuario.
    Debe permitir ser filtrada por:
    - fecha de adición
    - limitar la cantidad de información obtenida por cantidad**
    - caracteristicas de los vinos***

(Propiedades de un seguidor)

- Añadir seguidores entre los usuarios existentes
- Eliminar a un seguidor que tuviera
- Obtener una lista de todos los seguidores de un user
    Debe permitir ser filtrada por:
    - patrón de nombre
    - limitar la cantidad de información obtenida
- Obtener una lista con todos los vinos de un seguidor.
    Debe permitir ser filtrada por:
    - fecha de adición
    - limitar la cantidad de información obtenida por cantidad**
    - características de los vinos***

*patron de nombre
    - Buscar todos los users que contengan "Mar" --> "Mario", "Maria", etc

**informacion obtenida por cantidad
    - los X primeros
    - los eltos entre X e Y

***caracteristicas de los vinos
    - tipo de vino
    - uva
    - origen
    - año
    - etc
    o la combinación de varios de estos filtros

## Pasos a seguir
### Diseño del servicio RESTful
1. Identificar todos los recursos en un modelo de recursos para el servicio, incluyendo:
    - recursos de información
    - colecciones
    - recursos compuestos
    - contenedores/fábricas
    - controladores
    1.1. Diseñe los tipos de documentos JSON necesarios para el servicio
    1.2. Diseñe los esquemas JSON
        No necesario proporcionar los esquemas
        Sí los ejemplos de datos de todos los tipos de docs definidos

2. Diseñe los ids URI (nedpoint) de todos los recursos en el modelo (segun las convenciones de clase)

3. DIseñe para cada recurso, el subconjunto del interfaz uniforme de HTTP que ofrece. Incluya para cada verbo empleado por un recurso una breve descripcion de su uso.

4. Resuma el diseño del servicio usando SWAGGER para cada recurso, método y representación. Dar el fichero YAML para su comprobación + el enlace público a la API.
    * Incorporar en memoria las capturas de pantalla de cada operación
Alternativamente, utilizar la tabla para cada recurso definido y método soportado para dicho recurso.


### Implementacion de la API REST del servicio
Se debe utilizar la implementación de referencia del estándar para REST JAX-RX vista en clase o cualquiera de sus versiones (The Java API for RESTful Web Services, JSR3113, JSR3394 o JSR3705).

El prototipo deberá contar con algún mecanismo de persistencia de datos, recomendando el uso de un database SQL que facilitaría la construcción de consultas.
    * Indicar en la memoria el método de persistencia de datos seleccionado + diagrama entidad-relacion utilizado

### Implementación de un cliente Java
El cliente debe probar el servicio y llamar a todas las operaciones del servicio.

No es necesario realizar un cliente con interfaz gráfico o web (Vamos, no es necesario un frontend)

## Entrega
1. Memoria
    - Resumen del diseño del servicio. Incluir los * de los pasos de arriba
    - Screenshots de la ejecución de las operaciones desde un cliente REST  (Postman / http Rest Client).
        Debe verse tanto los detalles de la llamada a la operación como los detalles de esa llamada.
        Definición de colecciones de pruebas y su automatización
    - Screenshots de la ejecución del cliente de prueba para las operaciones anteriores

2. Datos utilizados para la prueba del servicio:
    - Si se ha elegido una db --> Incluir .txt con el código SQL con las instrucciones INSERT necesarias para la correcta ejecución de su:
        - cliente
        - usuario
        - clave
        empleados en la bbdd
        y detalle del nombre y versión del sistema de gestión de base de datos utilizado
    - Si se han utilizado dicheros, incluir los ficheros en los que se almacenan los datos.

3. WAR con el codigo fuente generado por Eclipse con el código del servicio
    Se añaden seleccionando la opción de incluir archivos con el código fuente

4. Proyecto con el código del cliente (ZIP o RAR)

### Criterios de evaluacion
- Diseño: 60%
    - Definición URIs + recursos: 30%
    - Verbos, código s http, parámetros: 20%
    - Estructuras de datos JSON + paginable + navegable: 15%
    - Diseño en swagger + colecciones de pruebas automatizadas: 10%
    - Memoria: 25%

- Implementación servicio y cliente: 40%
    - Servicio: 75%
    - Cliente: 25%

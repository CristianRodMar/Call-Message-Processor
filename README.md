Enlace documentación

url/swagger-ui/index.html

# Proyecto de Procesamiento de Mensajes

Este es un proyecto de procesamiento de mensajes que se encarga de analizar y clasificar mensajes en formato JSON. El objetivo principal es validar y almacenar mensajes válidos en las categorías correspondientes y sacar las métricas y KPIs correspondientes.

## Motivación

En este proyecto, he tomado la decisión de no utilizar una base de datos para almacenar los mensajes. La razón principal es que los datos ya se almacenan en un punto externo o se procesan adicionalmente en otros sistemas. Almacenar los mismos datos en una base de datos sería duplicar información innecesariamente.

Además, he aprovechado esta oportunidad para experimentar con tecnologías nuevas como Swagger y Lombok para mejorar la documentación y simplificar el código.

## Tecnologías Utilizadas

- Spring Boot: Framework de desarrollo de aplicaciones Java.
- Swagger: Herramienta para documentar y exponer APIs de manera sencilla.
- Lombok: Biblioteca que simplifica la creación de clases Java.
- Jackson: Librería para el manejo de JSON en Java.

## Enlace Documentación

Si despliegas localmente la aplicación, la documentación debe estar en http://localhost:8080/swagger-ui.html

![Ejemplo de la documentación](https://i.imgur.com/g8GgRa2.png)



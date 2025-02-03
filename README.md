# Desarrollo de marcos web para servicios REST y gestión de archivos estáticos

En este taller, exploraremos el funcionamiento de un servidor web capaz de manejar múltiples solicitudes de forma secuencial (no concurrente). El servidor leerá archivos desde el disco local y responderá con cualquier archivo solicitado, incluyendo:

Páginas HTML
Archivos JavaScript
Hojas de estilo CSS
Imágenes
Este ejercicio permitirá comprender cómo un servidor procesa peticiones y sirve contenido estático de manera eficiente.

## Descripción de la aplicación 📖

La aplicación web diseñado como una plataforma visualmente atractiva y funcional, ideal para explorar y gestionar diversos archivos. Su objetivo es proporcionar una interfaz intuitiva y moderna que permita a los usuarios interactuar con elementos como JavaScript, CSS, HTML e imágenes de manera rápida y sencilla. La aplicación combina un diseño elegante con animaciones suaves y una experiencia de usuario optimizada.

## Diagrama de Arquitectura

* Usuario (User):

Es quien realiza solicitudes HTTP a través de un navegador web.
* Navegador (Browser):

Actúa como intermediario entre el usuario y el servidor HTTP.
Realiza solicitudes HTTP al servidor en busca de recursos como archivos HTML, JavaScript, CSS o imágenes.
* Servidor HTTP (HttpServer):

Es el servidor que recibe y procesa las solicitudes HTTP enviadas por el navegador.
Se encuentra dentro de un "grupo genérico", lo que indica que puede formar parte de una infraestructura más amplia.

El navegador envía varias solicitudes HTTP al servidor en el puerto 35000 para diferentes rutas:

* /script.js: Solicitud para obtener un archivo de JavaScript.
* /index.html: Solicitud para cargar el archivo principal de la página web.
* /estilos.css: Solicitud para cargar el archivo de estilos CSS.
* /Imagen/Chill.jpg: Solicitud para obtener una imagen ubicada en una ruta específica.

El servidor procesa estas solicitudes y responde con los recursos correspondientes desde su sistema de archivos.

![image](https://github.com/user-attachments/assets/01c7ee8a-a10d-44e3-875e-97118c608545)

## Diagrama de Clase

Este diagrama describe la estructura básica y las responsabilidades principales de la clase HttpServer, que está diseñada para manejar solicitudes HTTP y servir archivos estáticos.

![image](https://github.com/user-attachments/assets/caace3d5-a3de-44fa-9875-e34adebdba24)


## Comenzando 🚀

Las siguientes instrucciones le permitirán obtener una copia del proyecto en funcionamiento en su máquina local para fines de desarrollo y prueba.

### Tecnologías usadas ⚙️

* [Maven](https://maven.apache.org/) : Gestor de dependencias y automatización de construcción para Java.
* [JavaScript](https://nodejs.org/) : Lenguaje de programación para interactividad en la web.
* [Java](https://www.java.com/es/) : Lenguaje de programación robusto para backend y aplicaciones empresariales.

```
* Versión Maven: 3.9.9
* Versión Java: 21
```

### Instalación 📦

Realice los siguientes pasos para clonar el proyecto en su máquina local.

```
git clone https://github.com/Pau993/Taller02.git
cd Taller02
git checkout Taller02
mvn clean compile
```

### Ejecutando la aplicación ⚙️

Para ejecutar la aplicación, ejecute el siguiente comando:

```
mvn exec:java '-Dexec.mainClass=edu.eci.arep.App'
```

El anterior comando limpiará las contrucciones previas, compilará y empaquetará el código en un jar y luego ejecutará la aplicación.

Diríjase a su navegador de preferencia y vaya a la siguiente dirección: http://localhost:35000/ para ver la aplicación en funcionamiento.

## Ejecutando las pruebas ⚙️

Para ejecutar las pruebas, ejecute el siguiente comando:

Las pruebas realizadas en este proyecto se enfocan en la validación y verificación de requisitos relacionados con el proceso de gestión de solicitudes, asegurando su correcto funcionamiento y cumplimiento de especificaciones.

```
mvn test
```
![image](https://github.com/user-attachments/assets/34dddd62-fddf-4d65-8fba-e7313f300c9b)

## Descripción de las pruebas

* testHandleApiRequestSaludo 🛠️

Verifica que la solicitud a la ruta /api/saludo responde con HTTP 200 OK y contiene el mensaje JSON esperado.
* testHandleApiRequestFecha 📅

Valida que la solicitud a /api/fecha devuelve HTTP 200 OK y contiene una clave "fecha" en la respuesta.
* testHandleApiRequestNotFound ❌

Comprueba que una ruta inexistente, como /api/desconocido, devuelve HTTP 404 Not Found.
* testHandleApiPostRequest 📤

Evalúa que una solicitud POST a /api/enviar con un cuerpo JSON sea procesada correctamente y responda con HTTP 200 OK y el mensaje

## Características principales: ⚙️

1. Interfaz moderna y responsiva:

* Un diseño minimalista con un esquema de colores que incluye degradados de tonos morados, creando una experiencia visual sofisticada.
* Totalmente adaptable a diferentes dispositivos gracias a su diseño responsivo.
  
2. Gestión de archivos: ⚙️

* Incluye botones interactivos que permiten abrir y visualizar archivos clave como:
* Archivos JavaScript (script.js).
* Hojas de estilo CSS (estilos.css).
* Documentos HTML (index.html).
* Imágenes (Chill.jpg).

## Muestra de la aplicación 🧩

![image](https://github.com/user-attachments/assets/85381b19-1d0d-492a-8a35-380d17db9219)


## Autores ✒️

* **Paula Natalia Paez Vega* - *Initial work* - [Paulinguis993](https://github.com/Paulinguis993)

## Licencia 📄

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Agradecimientos 🎁

Agradecimientos al profeso Daniel Benavides por brindarme sus conocimientos.

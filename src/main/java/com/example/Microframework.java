package com.example;
import static com.example.HttpServer.*;

public class Microframework {
    
    public static void main(String[] args) {
       
        // Crear un servidor HTTP
        try {
            HttpServer server = new HttpServer(); // Crear un servidor HTTP
            server.port(35000);
            staticFiles.location("/static"); // Establecer la ubicación de los archivos estáticos
            server.get("/hello", (req, res) -> "Hello World"); // Crear una ruta
        } catch (Exception e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }

    }
}

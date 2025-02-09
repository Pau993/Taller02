package com.example;
import static com.example.Utils.HttpServer.*;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.Anotation.GetMapping;
import com.example.Anotation.RestController;

public class Microframework {
    
    public static void main(String[] args) {
       
        // Crear un servidor HTTP
        try {
            port(35000);
            staticFiles.location("/static"); // Establecer la ubicación de los archivos estáticos
            get("/hello", (req, res) -> "Hello World"); // Crear una ruta
            get("/pi", (req, res) -> { return String.valueOf(Math.PI);}); // Crear una ruta con parámetros
            run(); // Iniciar el servidor
        } catch (Exception e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }

    }

}

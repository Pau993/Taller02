package com.example;
import static com.example.HttpServer.*;

public class Microframework {
    
    public static void main(String[] args) {
       
        try {
            HttpServer server = new HttpServer();
            server.port(35000);
            staticFiles.location("/static");
            server.get("/hello", (req, res) -> "Hello World");
        } catch (Exception e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }

    }
}

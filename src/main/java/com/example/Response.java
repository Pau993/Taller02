package com.example;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private int statusCode;
    private String statusMessage;
    private String body;
    private Map<String, String> headers = new HashMap<>();

    // Constructor por defecto
    public Response() {
        this.statusCode = 200; // Código de estado por defecto
        this.statusMessage = "OK"; // Mensaje de estado por defecto
    }

    // Método para obtener el código de estado
    public int getStatusCode() {
        return statusCode;
    }

    // Método para establecer el código de estado
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    // Método para obtener el mensaje de estado
    public String getStatusMessage() {
        return statusMessage;
    }

    // Método para establecer el mensaje de estado
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    // Método para obtener el cuerpo de la respuesta
    public String getBody() {
        return body;
    }

    // Método para establecer el cuerpo de la respuesta
    public void setBody(String body) {
        this.body = body;
    }

    // Método para agregar encabezados a la respuesta
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    // Método para obtener los encabezados de la respuesta
    public Map<String, String> getHeaders() {
        return headers;
    }

    // Método para enviar la respuesta al cliente
    public void send(PrintWriter out) {
        out.println("HTTP/1.1 " + statusCode + " " + statusMessage); // Línea de estado
        headers.forEach((key, value) -> out.println(key + ": " + value)); // Encabezados
        out.println();
        if (body != null) {
            out.println(body);
        }
        out.flush(); // Vaciar el búfer de salida, es decir limpiar todos los bits que puedan estar en caché
    }
}
package com.example;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 *   @author Paula Paez
 */

public class HttpServer {
    private static boolean primeraPeticion = true;
    private static int PORT = 35000;
    private static final String BASE_DIRECTORY = "src/main/resources/Files";
    public static final Utils staticFiles = new Utils();
    static ArrayList<Path> paths = new ArrayList<>();
    static Map<String, Route> routes = new HashMap<>();

    public HttpServer() throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciado en el puerto " + PORT);

        //Escucha simultaneamente por puerto
        while (true) {
            try (Socket clientSocket = serverSocket.accept()) {
                handleRequest(clientSocket);
            }
            //Si es la primera peticion, se cambia el valor de la variable
            primeraPeticion = false;
        }
    }

    /**
     * Este método maneja la solicitud HTTP recibida y envía una respuesta al cliente.
     * @param clientSocket
     */
    static void handleRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());
             OutputStream dataOut = clientSocket.getOutputStream();
        ) {
            //Valor de cada invocación de BufferedReader
            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) return;

            System.out.println("Solicitud recibida: " + requestLine);
            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0]; // GET, POST, PUT, DELETE, etc. Metodos que se están invocando
            String path = requestParts[1]; // Ruta del recurso solicitado
            System.out.println(path);

            

            if (method.equals("GET") && !primeraPeticion) {
                serveStaticFile(path, out, dataOut, bos);
                } else if (primeraPeticion){
                    serveStaticFile(path, out, dataOut, bos);
                } else {
                sendResponse(out, 405, "Method Not Allowed", "Método no permitido.");
                }
        } catch (IOException e) {
            System.err.println("Error al manejar la solicitud: " + e.getMessage());
        }
    }

    /**
     * // Sirve un archivo estático al cliente.
     * @param path
     * @param out
     * @param dataOut
     * @throws IOException
     */
    private static void serveStaticFile(String path, PrintWriter out, OutputStream dataOut, BufferedOutputStream bos) throws IOException {


        String almacenar = URI.create(path).getQuery();
        if(almacenar != null){
            path = "/" + almacenar.split("=")[1];
        }
        System.out.println(path);
        String filePath = BASE_DIRECTORY + (path.equals("/") ? "/index.html" : path);
        //System.out.println(URI.create(path).getQuery().split("=")[1]);
        File file = new File(filePath);
        System.out.println(filePath);

        if (file.exists() && !file.isDirectory()) {
            String contentType = Files.probeContentType(file.toPath());
            System.out.println(contentType);
            
                byte[] fileData = Files.readAllBytes(file.toPath());
    
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: " + contentType);
                out.println("Content-Length: " + fileData.length);
                out.println();
                out.flush();
    
                dataOut.write(fileData, 0, fileData.length);
                dataOut.flush();
            

        } else {
            sendResponse(out, 404, "Not Found", "Archivo no encontrado.");
        }
    }

    /**
     * // Maneja una solicitud a la API.
     * @param path
     * @param out
     */
    static void handleApiRequest(String path, PrintWriter out) {
        Arrays.toString( getParams(path));
        if (path.equals("/api/saludo")) {
            sendResponse(out, 200, "OK", "{\"mensaje\": \"¡Hola desde el servidor!\"}");
        } else if (path.equals("/api/fecha")) {
            sendResponse(out, 200, "OK", "{\"fecha\": \"" + new Date() + "\"}");
        } else {
            sendResponse(out, 404, "Not Found", "{\"error\": \"Recurso no encontrado\"}");
        }
    }

    /**
     * // Obtiene los parámetros de una solicitud.
     * @param path
     * @return
     */
    static String[] getParams(String path) {
        String[] parts = path.split("\\?");
        if (parts.length == 1) return new String[0];
        return parts[1].split("&");
    }

    /**
     * // Maneja una solicitud POST a la API.
     * @param path
     * @param in
     * @param out
     * @throws IOException
     */
    static void handleApiPostRequest(String path, BufferedReader in, PrintWriter out) throws IOException {
        if (path.startsWith("/api/enviar")) {
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                body.append(line);
            }

            sendResponse(out, 200, "OK", "{\"mensaje\": \"Datos recibidos: " + body + "\"}");
        } else {
            sendResponse(out, 404, "Not Found", "{\"error\": \"Recurso no encontrado\"}");
        }
    }


    /**
     * // Envía una respuesta HTTP al cliente.
     * @param out
     * @param statusCode
     * @param statusMessage
     * @param body
     */
    private static void sendResponse(PrintWriter out, int statusCode, String statusMessage, String body) {
        out.printf("HTTP/1.1 %d %s\r\n", statusCode, statusMessage);
        out.println("Content-Type: application/json");
        out.println("Content-Length: " + body.length());
        out.println();
        out.println(body);
    }

    public static void port(int port) {
        PORT = port;
    }

    public static void get(String path, Route route){
        paths.add(Paths.get(path));
    }

}



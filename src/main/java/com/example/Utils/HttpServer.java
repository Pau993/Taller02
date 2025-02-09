package com.example.Utils;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;


/**
 *   @author Paula Paez 
 */

public class HttpServer {
    private static boolean primeraPeticion = true; //Revisa si la primera petición es verdadera
    private static int PORT = 35000; // Puerto donde se inicia el programa 
    private static final String BASE_DIRECTORY = "src/main/resources/Files"; 
    public static final Utils staticFiles = new Utils();

    // String que almacena la ruta de los archivos Controlador
    // Route es una interfaz funcional que se utiliza para manejar las rutas Respuesta
    static Map<String, Route> routes = new HashMap<>(); // Rutas registradas en el servidor

    /**
     * Constructor de la clase HttpServer
     * @throws Exception
     */
    public HttpServer(){
        
    }

    public static void run() throws IOException {
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
     * @param clientSocket es el socket del cliente que realiza la solicitud
     */
    static void handleRequest(Socket clientSocket) {
        // Abre un BufferedReader para leer la entrada del cliente
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // Abre un PrintWriter para enviar exto al cliente
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            // Abre un Buffere BufferedOutputStream para enviar datos binarios al cliente
             BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());
             // Abre un OutputStream para enviar datos al cliente
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
     * @param path es la ruta del recurso solicitado
     * @param out es el flujo de salida para enviar la respuesta HTTP
     * @param dataOut es el flujo de salida para enviar los datos del archivo
     * @throws IOException es una excepción que se lanza si ocurre un error de entrada/salida
     */
    private static void serveStaticFile(String path, PrintWriter out, OutputStream dataOut, BufferedOutputStream bos) throws IOException {
        System.out.println(routes.keySet().toString());
        //Se obtiene el valor de la variable almacenar
        String almacenar = URI.create(path).getQuery();
        if(almacenar != null){
            path = "/" + almacenar.split("=")[1];
        }
        System.out.println(path);
        String filePath = BASE_DIRECTORY + (path.equals("/") ? "/index.html" : path);
        //System.out.println(URI.create(path).getQuery().split("=")[1]);
        File file = new File(filePath);
        System.out.println(filePath);

        Response response = new Response();
        Request request = new Request(almacenar);
        System.out.println(request.toString());
        if (routes.containsKey(path)) {
            Route responseString = routes.get(path);
            String responseValue = "";
            try {
                 responseValue = responseString.handle(request, response).toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/plain");
                out.println();
                out.println(responseValue);
                out.flush();
    
        }else if (file.exists() && !file.isDirectory() && !routes.containsKey(path)) {
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
    public static void handleApiRequest(String path, PrintWriter out) {
        Arrays.toString( getParams(path));
        if (path.equals("/api/saludo")) {
            sendResponse(out, 200, "OK", "{\"mensaje\": \"¡Hola desde el servidor!\"}");
        } else if (path.equals("/api/fecha")) {
            sendResponse(out, 200, "OK", "{\"fecha\": \"" + new Date() + "\"}");
        } else if (path.equals("/api/hello")) {
            sendResponse(out, 200, "OK", "{\"mensaje\\\": \\\"¡Hola desde el servidor!\\\"}");
        } else if (path.equals("/index.html")) {
            sendResponse(out, 200, "OK", "{\"mensaje\\\": \\\"¡Hola desde el servidor!\\\"}");
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
    public static void handleApiPostRequest(String path, BufferedReader in, PrintWriter out) throws IOException {
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
     * Envía una respuesta HTTP al cliente.
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

    /**
     * Establece el puerto en el que escuchará el servidor.
     * @param port
     */
    public static void port(int port) {
        PORT = port;
    }

    /**
     * Registra una ruta en el servidor.
     * @param path
     * @param route
     */
    public static void get(String path, Route route){
        routes.put(path, route);
    }

}



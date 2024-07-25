package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(7007);
            System.out.println("Servidor esperando conexiones...");

            while (true) {
                // Esperar que se conecten
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado desde: " + socket.getInetAddress());

                // Procesar el flujo de entrada 
                try (DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()))) {
                   
                    String nombreArchivo = "respaldo_laclinica1.dat";

                    // Crear un flujo de salida para guardar el archivo
                    try (FileOutputStream fos = new FileOutputStream(nombreArchivo)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                        fos.flush();
                        System.out.println("Respaldo recibido correctamente y guardado como " + nombreArchivo);
                    } catch (IOException e) {
                        System.out.println("Error al guardar el archivo de respaldo: " + e.getMessage());
                    }
                } catch (IOException e) {
                    System.out.println("Error al leer los datos del cliente: " + e.getMessage());
                } finally {
                    socket.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}


package net.code.java.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class JavaConnect2SQL {


    public static void main(String[] args) {

        String connectionUrl =
                "jdbc:sqlserver://192.168.100.118:1433;"
                        + "database=gestion_paquetes;"
                        + "user=an.rosario;" //TU USER
                        + "password=Eict@2024;" //TU CLAVE
                        + "encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=30;";
        

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {

            String selectSql = "SELECT * FROM lugar";
            ResultSet resultSet = statement.executeQuery(selectSql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id_lugar");
                String nombre = resultSet.getString("nombre_lugar");
                System.out.println("ID: " + id + ", Nombre: " + nombre);
            }

        } 
        
        catch (Exception e) {
                System.out.println("error");
                e.printStackTrace();
        }
        
    }

}
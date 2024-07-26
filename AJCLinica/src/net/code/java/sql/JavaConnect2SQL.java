package net.code.java.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;

import org.omg.CORBA.PUBLIC_MEMBER;

import jdk.internal.dynalink.beans.StaticClass;

import java.sql.ResultSet;

public class JavaConnect2SQL {

	private static String connectionUrl =
            "jdbc:sqlserver://192.168.100.118:1433;"
                    + "database=gestion_paquetes;"
                    + "user=an.rosario;" //TU USER
                    + "password=Eict@2024;" //TU CLAVE
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "loginTimeout=30;";
	
    public static void main(String[] args) {
    	
        String selectSql = "SELECT * FROM lugar";
        Connectionsql(selectSql);

    }

	public static void Connectionsql(String selectSql) {
		// TODO Auto-generated method stub
		try (Connection connection = DriverManager.getConnection(connectionUrl);
	             Statement statement = connection.createStatement()) {

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
package csc251.team.project;

import java.sql.*;

public class connectDB {
    public static boolean isDBClassLoaded(){
         try {
             String jdbcDriver = "com.mysql.cj.jdbc.Driver";
             Class.forName(jdbcDriver);
             return true;
         } catch (ClassNotFoundException e) {
             System.out.println("Please install mysql driver ");
             return false;
         }
     }
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "root");
            if (!databaseExists(conn, "carlot")) {
                createDatabase(conn, "carlot");
                System.out.println("Database 'carlot' created.");
            }

            conn = DriverManager.getConnection("jdbc:mysql://localhost/carlot", "root", "root");
            System.out.println("connected to database");
        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
        return conn;
    }

    // PRIVATE METHODS
    private static boolean databaseExists(Connection conn, String dbName) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery("SHOW DATABASES LIKE '" + dbName + "'");
        return resultSet.next();
    }
    private static void createDatabase(Connection conn, String dbName) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE DATABASE " + dbName);
    }

}


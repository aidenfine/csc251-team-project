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
    public static Car addCar(String id, int mileage, int mpg, double cost, double salesPrice) {
        Connection conn = null;
        PreparedStatement statement = null;
        Car car = null;

        try {
            if (doesCarExist(id)) {
                System.out.println("Car with ID " + id + " already exists in the database.");
                return null;
            }

            car = new Car(id, mileage, mpg, cost, salesPrice);
            conn = connectDB.getConnection();

            String sql = "INSERT INTO casetResult (id, mileage, mpg, cost, salesPrice) VALUES (?, ?, ?, ?, ?)";
            statement = conn.prepareStatement(sql);

            statement.setString(1, car.getId());
            statement.setInt(2, car.getMileage());
            statement.setInt(3, car.getMpg());
            statement.setDouble(4, car.getCost());
            statement.setDouble(5, car.getSalesPrice());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Car added to database.");
            } else {
                System.out.println("Failed to add car to database.");
            }
        } catch (SQLException e) {
            System.err.println("Error adding car to database: " + e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return car;
    }
    public static boolean doesCarExist(String id) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet setResult = null;

        try {
            conn = connectDB.getConnection();

            String sql = "SELECT COUNT(*) FROM casetResult WHERE id = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, id);

            setResult = statement.executeQuery();

            if (setResult.next()) {
                int count = setResult.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking if car exists in database: " + e.getMessage());
        } finally {
            try {
                if (setResult != null) {
                    setResult.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static void sellCar(String id, double priceSold) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet setResult = null;

        try {
            conn = connectDB.getConnection();
            String sqlUpdate = "UPDATE casetResult SET priceSold = ?, sold = ?, profit = priceSold - cost WHERE id = ?";
            statement = conn.prepareStatement(sqlUpdate);
            statement.setDouble(1, priceSold);
            statement.setBoolean(2, true);
            statement.setString(3, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Car with ID " + id + " sold for $" + priceSold + ".");
            } else {
                System.out.println("Car with ID " + id + " not found in the database.");
            }
        } catch (SQLException e) {
            System.err.println("Error selling car: " + e.getMessage());
        } finally {
            try {
                if (setResult != null) {
                    setResult.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW DATABASES LIKE '" + dbName + "'");
        return resultSet.next();
    }
    private static void createDatabase(Connection conn, String dbName) throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeUpdate("CREATE DATABASE " + dbName);
    }

}


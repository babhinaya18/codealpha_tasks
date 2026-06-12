package Hotel.Management.System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {
    public Connection connection;
    public Statement statement;

    public DatabaseConnection(){
        try{
            // Ensure proper MySQL driver instantiation for modern Java runtimes
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hotelMS",
                    "root",
                    "your_password"
            );
            statement = connection.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
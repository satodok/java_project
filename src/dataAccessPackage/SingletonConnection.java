package dataAccessPackage;

import exceptionPackage.*;

import javax.swing.*;
import java.sql.*;

public class SingletonConnection {
    private static Connection uniqueConnection;
    private SingletonConnection() {

    }

    public static Connection getInstance(String password) throws ConnectionException {
        try {
            if (uniqueConnection == null) {
                uniqueConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libiavelo",
                        "root",
                            password);
            }
        } catch (SQLException sqlException) {
            String message = "Erreur lors de la connexion à la base de données libiavelo.";
            throw new ConnectionException(message);

        }
        return uniqueConnection;
    }
}

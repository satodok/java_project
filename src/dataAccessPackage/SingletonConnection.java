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
            return uniqueConnection;
        } catch (SQLException sqlException) {
            throw new ConnectionException("Erreur lors de la connexion à la base de données libiavelo.");

        }
    }
}

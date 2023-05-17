package dataAccessPackage;

import exceptionPackage.*;

import javax.swing.*;
import java.sql.*;

public class SingletonConnection {
    private static Connection uniqueConnection;

    private SingletonConnection() {
    }

    public static Connection getInstance() throws ConnectionException {
        try {
            if (uniqueConnection == null) {
                JPasswordField passwordField = new JPasswordField();
                int option = JOptionPane.showOptionDialog(null, passwordField, "Please enter your password",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

                if (option == JOptionPane.OK_OPTION) {
                    char[] passwordChars = passwordField.getPassword();
                    String password = new String(passwordChars);

                    uniqueConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libiavelo",
                            "root",
                            password);
                } else {
                    throw new ConnectionException("La connexion à la base de données a été annulée.");
                }
            }
            return uniqueConnection;
        } catch (SQLException sqlException) {
            throw new ConnectionException("Erreur lors de la connexion à la base de données libiavelo.");
        }
    }
}
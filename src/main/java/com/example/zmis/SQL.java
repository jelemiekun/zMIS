package com.example.zmis;

import java.sql.*;

import static com.example.zmis.Alerts.*;

public class SQL {
    private static String url = "jdbc:mysql://gateway01.ap-southeast-1.prod.aws.tidbcloud.com/zMIS?verifyServerCertificate=false&useSSL=true&requireSSL=true&sslMode=VERIFY_CA&sslCert=src/main/resources/isrgrootx1.pem";
    private static String user = "2ceuVZdRhiHHWcH.root";
    private static String password = "HgAhMHYP4GsyA62G";
    private static Connection connection;
    private static PreparedStatement preparedStatement;

    public static void SQLCreateConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            alertNoConnection();
        }
        System.out.println("Connected to tiDB database.");
    }

    public static boolean SQLLogin(String email, String password) {
        try {
            String query = "SELECT password FROM login_register where email = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String dbPassword = resultSet.getString("password");
                return dbPassword.equals(password);
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean SQLRegister(String email, String password) {
        try {
            String addToStudentQuery = "INSERT INTO student (email, password) values (?, ?);";
            preparedStatement = connection.prepareStatement(addToStudentQuery);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            preparedStatement.executeUpdate();

            String addToLoginQuery = "INSERT INTO login_register (email, password, credentials_id)" +
                    "VALUES (?, ?, (SELECT id FROM student WHERE email = ?))";
            preparedStatement = connection.prepareStatement(addToLoginQuery);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3 , email);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

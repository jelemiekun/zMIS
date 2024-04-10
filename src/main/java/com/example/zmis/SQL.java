package com.example.zmis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public static boolean SQLLogin() {
        return false;
    }

    public static boolean SQLRegister() {
        return false;
    }
}

package com.example.zmis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQL {
    private static String url = "jdbc:mysql://gateway01.ap-southeast-1.prod.aws.tidbcloud.com/zMIS?verifyServerCertificate=false&useSSL=true&requireSSL=true&sslMode=VERIFY_CA&sslCert=src/main/resources/isrgrootx1.pem";
    private static String user = "2ceuVZdRhiHHWcH.root";
    private static String password = "HgAhMHYP4GsyA62G";
    private static PreparedStatement preparedStatement;

    public static void createConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to tiDB database.");
        } catch (SQLException e) {
            System.out.println("No connection");
            // handle events here
        }
    }
}

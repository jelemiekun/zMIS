package com.example.zmis;

import java.sql.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import static com.example.zmis.Alerts.*;

public class SQL {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;
    private static String url = "jdbc:mysql://gateway01.ap-southeast-1.prod.aws.tidbcloud.com/zMIS?verifyServerCertificate=false&useSSL=true&requireSSL=true&sslMode=VERIFY_CA&sslCert=src/main/resources/isrgrootx1.pem";
    private static String user = "2ceuVZdRhiHHWcH.root";
    private static String password = "HgAhMHYP4GsyA62G";
    private static PreparedStatement preparedStatement;

    public static void SQLCreateConnection() {
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000); // 30 seconds
        config.setIdleTimeout(600000); // 10 minutes
        config.setMaxLifetime(1800000); // 30 minutes
        dataSource = new HikariDataSource(config);
    }

    public static boolean SQLLogin(String email, String password) {
        try {
            Connection connection = dataSource.getConnection();

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
            if (!SQLEmailAlreadyExists(email)) {
                Connection connection = dataSource.getConnection();

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
                alertRegisterSuccess();
                return true;
            } else {
                alertRegisterEmailExists();
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static boolean SQLEmailAlreadyExists(String email) {
        try {
            Connection connection = dataSource.getConnection();
            String searchAllEmailQuery = "SELECT email FROM login_register WHERE email = ?";
            preparedStatement = connection.prepareStatement(searchAllEmailQuery);
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            return true;
        }
    }
}

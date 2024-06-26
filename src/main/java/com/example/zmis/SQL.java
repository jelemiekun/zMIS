package com.example.zmis;

import java.sql.*;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static com.example.zmis.Alerts.*;
import static com.example.zmis.loginRegisterController.*;
import static com.example.zmis.mainController.full_name;

public class SQL {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;
    private static final String url = "jdbc:mysql://gateway01.ap-southeast-1.prod.aws.tidbcloud.com/zMIS?verifyServerCertificate=false&useSSL=true&requireSSL=true&sslMode=VERIFY_CA&sslCert=src/main/resources/isrgrootx1.pem";
    private static final String user = "2ceuVZdRhiHHWcH.root";
    private static final String password = "HgAhMHYP4GsyA62G";
    private static PreparedStatement preparedStatement;

    private static void SQLCreateConnection() {
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(600000); // 10 minuts
        config.setIdleTimeout(600000); // 10 minutes
        config.setMaxLifetime(30000); // 30 seconds
        dataSource = new HikariDataSource(config);
    }

    public static void createConnection() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(SQL::SQLCreateConnection);
        executor.shutdown();
    }

    public static boolean SQLLogin(String email, String password) {
        Connection connection = null;
        try {
            if (dataSource != null) {
                connection = dataSource.getConnection();

                String query = "SELECT password FROM login_register where email = ?;";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String dbPassword = resultSet.getString("password");
                    boolean proceed = dbPassword.equals(password);
                    SQLCheckIfAdmin(email);
                    referenceEmail = email;
                    return proceed;
                }
                return false;
            } else {
                alertNoConnection();
                return false;
            }
        } catch (SQLException e) {
            alertNoConnection();
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void SQLCheckIfAdmin(String email) {
        Connection connection = null;
        try {
            if (dataSource != null) {
                connection = dataSource.getConnection();

                String query = "SELECT credentials_id FROM login_register WHERE email = ?;";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int dbCredentialsID = resultSet.getInt("credentials_id");
                    if (dbCredentialsID == 1)
                        isAdmin = true;
                }
            } else {
                alertNoConnection();
            }
        } catch (SQLException e) {
            alertNoConnection();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static boolean SQLRegister(String email, String password) {
        Connection connection = null;
        try {
            if (dataSource != null) {
                if (!SQLEmailAlreadyExists(email)) {
                    connection = dataSource.getConnection();

                    String addToStudentQuery = "INSERT INTO student (email, password) values (?, ?);";
                    preparedStatement = connection.prepareStatement(addToStudentQuery);
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);

                    preparedStatement.executeUpdate();

                    String addToLoginQuery = "INSERT INTO login_register (email, password, credentials_id)" +
                            "VALUES (?, ?, (SELECT id FROM student WHERE email = ?));";
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
            } else {
                alertNoConnection();
            }
            return false;
        } catch (SQLException e) {
            alertNoConnection();
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static boolean SQLEmailAlreadyExists(String email) {
        Connection connection = null;
        try {
            if (dataSource != null) {
                connection = dataSource.getConnection();
                String searchAllEmailQuery = "SELECT email FROM login_register WHERE email = ?;";
                preparedStatement = connection.prepareStatement(searchAllEmailQuery);
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            } else {
                alertNoConnection();
            }
            return true;
        } catch (SQLException e) {
            return true;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static boolean SQLStudentAccountIsApplied() {
        Connection connection = null;
        try {
            if (dataSource != null) {
                connection = dataSource.getConnection();
                String query = "SELECT is_applied FROM student WHERE email = ?;";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1 , referenceEmail);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("is_applied") == 1;
                    } else {
                        alertSQLRandomError();
                        return false;
                    }
                }
            } else {
                alertNoConnection();
                return false;
            }
        } catch (SQLException e) {
            alertNoConnection();
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static Account SQLPopulateAppliedAccount() {
        Account populatedAccount = null;

        Connection connection = null;
        try {
            if (dataSource != null) {
                connection = dataSource.getConnection();
                String query = "SELECT * FROM student WHERE email = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, referenceEmail);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String email = resultSet.getString("email");
                    String lrn = resultSet.getString("LRN");
                    String fullName = resultSet.getString("full_name");
                    String phoneNumber = resultSet.getString("phone_number");
                    String strand = resultSet.getString("strand");
                    String section = resultSet.getString("section");
                    int age = resultSet.getInt("age");
                    String sex = resultSet.getString("sex");
                    LocalDate birthdate = LocalDate.parse(resultSet.getString("birthdate"));
                    String address = resultSet.getString("address");
                    String civilStatus = resultSet.getString("civil_status");
                    String elemSchool = resultSet.getString("elem_school");
                    String elemSchoolSY = resultSet.getString("elem_SY");
                    String juniorHS = resultSet.getString("junior_hs");
                    String juniorHSSY = resultSet.getString("junior_hs_SY");
                    String documentStatus = resultSet.getString("document_status");
                    boolean form137 = resultSet.getInt("form_137") == 1;
                    boolean form138 = resultSet.getInt("form_138") == 1;
                    boolean goodMoral = resultSet.getInt("good_moral") == 1;
                    boolean isApplied = resultSet.getInt("is_applied") == 1;

                    populatedAccount = new Account(email, lrn, fullName, phoneNumber, strand, section, age, sex, birthdate,
                            address, civilStatus, elemSchool, elemSchoolSY, juniorHS, juniorHSSY, documentStatus,
                            form137, form138, goodMoral, isApplied);
                } else {
                    alertSQLRandomError();
                }
            } else {
                alertNoConnection();
            }
        } catch (SQLException e) {
            alertNoConnection();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return populatedAccount;
    }

    public static Account SQLPopulateApplicationSelected() {
        Account populatedAccount = null;

        Connection connection = null;
        try {
            if (dataSource != null) {
                connection = dataSource.getConnection();
                String query = "SELECT * FROM student WHERE full_name = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, full_name);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String email = resultSet.getString("email");
                    String lrn = resultSet.getString("LRN");
                    String fullName = resultSet.getString("full_name");
                    String phoneNumber = resultSet.getString("phone_number");
                    String strand = resultSet.getString("strand");
                    String section = resultSet.getString("section");
                    int age = resultSet.getInt("age");
                    String sex = resultSet.getString("sex");
                    LocalDate birthdate = LocalDate.parse(resultSet.getString("birthdate"));
                    String address = resultSet.getString("address");
                    String civilStatus = resultSet.getString("civil_status");
                    String elemSchool = resultSet.getString("elem_school");
                    String elemSchoolSY = resultSet.getString("elem_SY");
                    String juniorHS = resultSet.getString("junior_hs");
                    String juniorHSSY = resultSet.getString("junior_hs_SY");
                    String documentStatus = resultSet.getString("document_status");
                    boolean form137 = resultSet.getInt("form_137") == 1;
                    boolean form138 = resultSet.getInt("form_138") == 1;
                    boolean goodMoral = resultSet.getInt("good_moral") == 1;
                    boolean isApplied = resultSet.getInt("is_applied") == 1;

                    populatedAccount = new Account(email, lrn, fullName, phoneNumber, strand, section, age, sex, birthdate,
                            address, civilStatus, elemSchool, elemSchoolSY, juniorHS, juniorHSSY, documentStatus,
                            form137, form138, goodMoral, isApplied);
                } else {
                    alertSQLRandomError();
                }
            } else {
                alertNoConnection();
            }
        } catch (SQLException e) {
            alertNoConnection();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return populatedAccount;
    }

    public static void SQLInsertIntoStudent(String lrn, String fullName, String phoneNumber, String strand, int age, String sex,
                                            LocalDate birthdate, String address, String civilStatus, String elemSchool, String elemSY,
                                            String juniorHS, String juniorHSSY, String documentStatus, boolean form137, boolean form138, boolean goodMoral) {
        Connection connection = null;
        try {
            if (dataSource != null) {
                connection = dataSource.getConnection();
                String query = "UPDATE student SET lrn = ?, full_name = ?, phone_number = ?, strand = ?, age = ?, sex = ?, birthdate = ?, address = ?, civil_status = ?, elem_school = ?, elem_SY = ?, junior_hs = ?, junior_hs_SY = ?, document_status = ?, form_137 = ?, form_138 = ?, good_moral = ?, is_applied = 1 WHERE email = ?;";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, lrn);
                preparedStatement.setString(2, fullName);
                preparedStatement.setString(3, phoneNumber);
                preparedStatement.setString(4, strand);
                preparedStatement.setInt(5, age);
                preparedStatement.setString(6, sex);
                preparedStatement.setDate(7, Date.valueOf(birthdate));
                preparedStatement.setString(8, address);
                preparedStatement.setString(9, civilStatus);
                preparedStatement.setString(10, elemSchool);
                preparedStatement.setString(11, elemSY);
                preparedStatement.setString(12, juniorHS);
                preparedStatement.setString(13, juniorHSSY);
                preparedStatement.setString(14, documentStatus);
                preparedStatement.setBoolean(15, form137);
                preparedStatement.setBoolean(16, form138);
                preparedStatement.setBoolean(17, goodMoral);
                preparedStatement.setString(18, referenceEmail);

                preparedStatement.executeUpdate();
            } else {
                alertNoConnection();
            }
        } catch (SQLException e) {
            alertNoConnection();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static ObservableList<Account> SQLPopulateTableViewEnrolled() {
        ObservableList<Account> accountObservableList = FXCollections.observableArrayList();

        Connection connection = null;
        try {
            if (dataSource != null) {
                connection = dataSource.getConnection();
                String query = "SELECT full_name, strand, section FROM student where is_accepted = 1;";
                preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String fullName = resultSet.getString("full_name");
                    String strand = resultSet.getString("strand");
                    String section = resultSet.getString("section");

                    Account account = new Account(fullName, strand, section);
                    accountObservableList.add(account);
                }
            } else {
                alertNoConnection();
            }
        } catch (SQLException e) {
            alertNoConnection();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return accountObservableList;
    }

    public static ObservableList<Account> SQLPopulateTableViewDashboardApplicants() {
        ObservableList<Account> accountObservableList = FXCollections.observableArrayList();

        Connection connection = null;
        try {
            if (dataSource != null) {
                connection = dataSource.getConnection();
                String query = "SELECT full_name, document_status FROM student;";
                preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String fullName = resultSet.getString("full_name");
                    String documentStatus = resultSet.getString("document_status");

                    Account account = new Account(fullName, documentStatus);
                    accountObservableList.add(account);
                }
            } else {
                alertNoConnection();
            }
        } catch (SQLException e) {
            alertNoConnection();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return accountObservableList;
    }

    public static ObservableList<Account> SQLPopulateTableViewDashboardEnrolled() {
        ObservableList<Account> accountObservableList = FXCollections.observableArrayList();

        Connection connection = null;
        try {
            if (dataSource != null) {
                connection = dataSource.getConnection();
                String query = "SELECT full_name, document_status FROM student WHERE is_accepted = 1;";
                preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String fullName = resultSet.getString("full_name");
                    String documentStatus = resultSet.getString("document_status");

                    Account account = new Account(fullName, documentStatus);
                    accountObservableList.add(account);
                }
            } else {
                alertNoConnection();
            }
        } catch (SQLException e) {
            alertNoConnection();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return accountObservableList;
    }

    public static ObservableList<Account> SQLPopulateTableViewDashboardDeclined() {
        ObservableList<Account> accountObservableList = FXCollections.observableArrayList();

        Connection connection = null;
        try {
            if (dataSource != null) {
                connection = dataSource.getConnection();
                String query = "SELECT full_name, document_status FROM student WHERE is_accepted = 0;";
                preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String fullName = resultSet.getString("full_name");
                    String documentStatus = resultSet.getString("document_status");

                    Account account = new Account(fullName, documentStatus);
                    accountObservableList.add(account);
                }
            } else {
                alertNoConnection();
            }
        } catch (SQLException e) {
            alertNoConnection();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return accountObservableList;
    }


    public static boolean SQLStudentAccountUpdateIsAccepted(String email, boolean isAccepted) {
        Connection connection = null;
        try {
            if (dataSource != null) {
                connection = dataSource.getConnection();
                String query = "UPDATE student set is_accepted = ? where email = ?;";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setBoolean(1 , isAccepted);
                preparedStatement.setString(2, email);

                preparedStatement.executeUpdate();
                return true;
            } else {
                alertNoConnection();
                return false;
            }
        } catch (SQLException e) {
            alertNoConnection();
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void SQLUpdateAccount(String strand, String section, boolean form137, boolean form138, boolean goodMoral, String email) {
        Connection connection = null;
        try {
            if (dataSource != null) {
                connection = dataSource.getConnection();

                String query = "UPDATE student SET strand = ?, section = ?, form_137 = ?, form_138 = ?, good_moral = ? WHERE email = ?;";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, strand);
                preparedStatement.setString(2, section);
                preparedStatement.setBoolean(3, form137);
                preparedStatement.setBoolean(4, form138);
                preparedStatement.setBoolean(5, goodMoral);
                preparedStatement.setString(6, email);

                preparedStatement.executeUpdate();
            } else {
                alertNoConnection();
            }
        } catch (SQLException e) {
            alertNoConnection();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static ObservableList<Account> SQLSearchEnrolled(String search) {
        String query = "SELECT full_name, strand, section FROM student WHERE is_accepted = 1 AND LOWER(full_name) LIKE ?";
        ObservableList<Account> searchedQuery = FXCollections.observableArrayList();

        try (Connection connection = dataSource.getConnection()) {
            String likeParam = "%" + search.toLowerCase() + "%";
            PreparedStatement preparedStatement1 = connection.prepareStatement(query);
            preparedStatement1.setString(1, likeParam);

            ResultSet resultSet = preparedStatement1.executeQuery();

            while (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                String strand = resultSet.getString("strand");
                String section = resultSet.getString("section");

                searchedQuery.add(new Account(fullName, strand, section));
            }
        } catch (SQLException e) {
            alertNoConnection();
            throw new RuntimeException(e);
        }
        return searchedQuery;
    }

}

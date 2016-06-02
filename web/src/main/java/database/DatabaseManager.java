package database;

import java.sql.*;

public class DatabaseManager {

    private static DatabaseManager instance;

    private Connection connection;

    public DatabaseManager() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/mokhnachevresume";
        connection = DriverManager.getConnection(url, "mokhnachevresume", "mokhnachevresume");
    }

    public static synchronized DatabaseManager getInstance() throws SQLException, ClassNotFoundException {
        return instance == null ? instance = new DatabaseManager() : instance;
    }

    public static Statement createStatement(){
        Statement statement = null;
        try {
            statement = getInstance().connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public static PreparedStatement prepareStatementWithReturnGeneratedKeys(String sql){
        PreparedStatement statement = null;
        try {
            statement = getInstance().connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public static PreparedStatement prepareStatement(String sql){
        PreparedStatement statement = null;
        try {
            statement = getInstance().connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return statement;
    }

}

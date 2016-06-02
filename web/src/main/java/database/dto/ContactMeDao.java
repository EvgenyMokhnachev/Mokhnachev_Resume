package database.dto;

import database.DatabaseManager;
import entities.ContactMe;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactMeDao {

    private static ContactMeDao instance;

    private ContactMeDao(){

    }

    public static ContactMeDao getInstance(){
        return instance == null ? instance = new ContactMeDao() : instance;
    }

    public void insert(ContactMe contactMe) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = DatabaseManager.prepareStatementWithReturnGeneratedKeys("INSERT INTO contact_me (name, email, phone, message) VALUES (?, ?, ?, ?)");
            stmt.setString(1, contactMe.name);
            stmt.setString(2, contactMe.email);
            stmt.setString(3, contactMe.phone);
            stmt.setString(4, contactMe.message);

            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0) {
                throw new SQLException("Creating ContactMe failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    contactMe.id = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating ContactMe failed, no ID obtained.");
                }
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public List<ContactMe> getAll(){
        List<ContactMe> contactMes = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseManager.prepareStatement("SELECT * FROM contact_me");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                contactMes.add(new ContactMe(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactMes;
    }

}

package entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactMe {

    public Long id;

    public String name;

    public String email;

    public String phone;

    public String message;

    public ContactMe(String name, String email, String phone, String message){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    public ContactMe(ResultSet rs){
        try {
            this.id = rs.getLong(1);
            this.name = rs.getString(2);
            this.phone = rs.getString(3);
            this.email = rs.getString(4);
            this.message = rs.getString(5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

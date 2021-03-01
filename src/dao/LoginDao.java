package dao;

import metier.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao  {
    public static User checkLogin(String email, String password, String role) {

        User user = new User();
        String sql = "SELECT * FROM users WHERE email=? AND password=? AND role=?";
        Connection connection = SingletonConnection.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, role);
            ResultSet rs = ps.executeQuery();

            /*
             * Si la requete a reussi on retourne vrai
             * if(rs.next()) {
             * return true;
             *  }
             */

            if(rs.next()) {

                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return user;
    }
}

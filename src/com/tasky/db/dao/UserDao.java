package com.tasky.db.dao;

import com.tasky.app.models.User;
import com.tasky.util.Encrypt;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by markus on 2017-03-02.
 */
public class UserDao {
    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public boolean auth(User user) {
        String sql = "SELECT * FROM User WHERE username=? && password=?";
        Boolean validAuth = false;

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, user.getUsername());
            stmt.setString(2, Encrypt.encrypt(user.getPassword()));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                validAuth = true;
            }

            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to fetch user" + e);
        }

        return validAuth;
    }
}

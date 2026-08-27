package vn.hcmute.webpr330479.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vn.hcmute.webpr330479.connection.DBConnection;
import vn.hcmute.webpr330479.dao.UserDao;
import vn.hcmute.webpr330479.models.User;

public class UserDaoImpl implements UserDao {

    @Override
    public void insert(User user) {
        String sql = """
                INSERT INTO AppUser (username, user_password, full_name, email, phone)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = new DBConnection().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhone());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Khong the them tai khoan.", exception);
        }
    }

    @Override
    public User getByUsername(String username) {
        String sql = """
                SELECT user_id, username, user_password, full_name, email, phone
                FROM AppUser
                WHERE username = ?
                """;

        try (Connection connection = new DBConnection().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("user_id"),
                            resultSet.getString("username"),
                            resultSet.getString("user_password"),
                            resultSet.getString("full_name"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"));
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Khong the tim tai khoan.", exception);
        }

        return null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return exists("SELECT 1 FROM AppUser WHERE username = ?", username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return exists("SELECT 1 FROM AppUser WHERE email = ?", email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return exists("SELECT 1 FROM AppUser WHERE phone = ?", phone);
    }

    private boolean exists(String sql, String value) {
        try (Connection connection = new DBConnection().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, value);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Khong the kiem tra tai khoan.", exception);
        }
    }
}
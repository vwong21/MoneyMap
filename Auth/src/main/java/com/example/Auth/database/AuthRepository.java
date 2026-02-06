package com.example.Auth.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.example.Auth.api.model.User;
import com.example.Auth.exception.ExistingUserException;

    @Repository
    public class AuthRepository {
        
        private final DataSource datasource;

        public AuthRepository(DataSource datasource) {
            this.datasource = datasource;
        }

        // register method
        public void register(User user) {
            String sql = "INSERT INTO users (email, first_name, last_name, password) VALUES (?, ?, ?, ?)";
            try (Connection conn = datasource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getPassword());

            stmt.executeUpdate();

            System.out.println("Successfully added user to database");
            } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new ExistingUserException("Email already registered", e);
            } else {
                throw new RuntimeException("Failed to register user", e);
            }
        }

        }

        // login method
        public User login(String email) {
            String sql = "SELECT * FROM users WHERE email = ?";
            try (Connection conn = datasource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(UUID.fromString(rs.getString("id")));
                user.setEmail(email);
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPassword(rs.getString("password"));

                return user;

            } else {
                return null;
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to authenticate user", e);
            }
        }

        // getUser method
        public User getUser(UUID id) {
            String sql = "SELECT * FROM users WHERE id = ?";
            try (Connection conn = datasource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setObject(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    User user = new User();
                    user.setId(UUID.fromString(rs.getString("id")));
                    user.setEmail(rs.getString("email"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setPassword(rs.getString("password"));

                    return user;
                }

                return null;

            } catch (Exception e) {
                throw new RuntimeException("Failed to get user", e);
            }
        }

        // patchUser method
        public void updateEmail(UUID userId, String email) {
            String sql = "UPDATE users SET email = ? WHERE id = ?";
            try (Connection conn =  datasource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setObject(1, email);
                    stmt.setObject(2, userId);
                    
                    stmt.executeUpdate();
                    System.out.println("Successfully updated user email in database");
                 } catch (Exception e) {
                throw new RuntimeException("Failed to update user email", e);
            }
        }

        public void updateFirstName(UUID userId, String firstName) {
            String sql = "UPDATE users SET first_name = ? WHERE id = ?";
            try (Connection conn =  datasource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setObject(1, firstName);
                    stmt.setObject(2, userId);
                    
                    stmt.executeUpdate();
                    System.out.println("Successfully updated user first_name in database");
                 } catch (Exception e) {
                throw new RuntimeException("Failed to update user first_name", e);
            }
        }
        public void updateLastName(UUID userId, String lastName) {
            String sql = "UPDATE users SET last_name = ? WHERE id = ?";
            try (Connection conn =  datasource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setObject(1, lastName);
                    stmt.setObject(2, userId);
                    
                    stmt.executeUpdate();
                    System.out.println("Successfully updated user last_name in database");
                 } catch (Exception e) {
                    throw new RuntimeException("Failed to update user last_name", e);
            }
        }

        // deleteUser method
        public UUID deleteUser(UUID userId) {
            String sql = "DELETE FROM USERS WHERE id = ?";
            try (Connection conn = datasource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setObject(1, userId);

                    stmt.executeUpdate();
                    System.out.println("User has been successfully delted");
                    return userId;
                } catch (Exception e) {
                    throw new RuntimeException("Failed to delete user", e);
                }
        }
    }

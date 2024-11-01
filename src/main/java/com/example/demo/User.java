package com.example.demo;


import javax.persistence.*;
import java.util.Base64;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalUsername;
    private String originalPassword;
    private String currentUsername;
    private String currentPassword;

    private Integer success;

    public Integer isSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    // Getters and Setters
    public String getOriginalUsername() {
        return originalUsername;
    }

    public void setOriginalUsername(String originalUsername) {
        this.originalUsername = originalUsername;
    }

    public String getOriginalPassword() {
        return originalPassword;
    }

    public void setOriginalPassword(String originalPassword) {
        originalPassword = Base64.getEncoder().encodeToString(originalPassword.getBytes());
        this.originalPassword = originalPassword;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        currentPassword = Base64.getEncoder().encodeToString(currentPassword.getBytes());
        this.currentPassword = currentPassword;
    }
}
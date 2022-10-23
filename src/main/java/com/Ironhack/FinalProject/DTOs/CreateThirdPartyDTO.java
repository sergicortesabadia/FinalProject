package com.Ironhack.FinalProject.DTOs;

public class CreateThirdPartyDTO {
    private String username;
    private String password;
    private String hashedKey;

    public CreateThirdPartyDTO() {
    }

    public CreateThirdPartyDTO(String username, String password, String hashedKey) {
        setUsername(username);
        setPassword(password);
        setHashedKey(hashedKey);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }
}

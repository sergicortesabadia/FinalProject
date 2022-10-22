package com.Ironhack.FinalProject.DTOs;

import javax.validation.constraints.NotNull;

public class ChangePassWordDTO {
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
    @NotNull
    private String repeatPassword;

    public ChangePassWordDTO() {
    }

    public ChangePassWordDTO(String oldPassword, String newPassword, String repeatPassword) {
        setOldPassword(oldPassword);
        setNewPassword(newPassword);
        setRepeatPassword(repeatPassword);
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}

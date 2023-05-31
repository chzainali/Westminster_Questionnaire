package com.dev.westminsterquestionnaire.auth;

public class RegisterModel {
    String name, email, phone, password, image, scienceProgress, englishProgress, mathProgress;

    public RegisterModel() {
    }

    public RegisterModel(String name, String email, String phone, String password, String image, String scienceProgress, String englishProgress, String mathProgress) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.image = image;
        this.scienceProgress = scienceProgress;
        this.englishProgress = englishProgress;
        this.mathProgress = mathProgress;
    }

    public String getScienceProgress() {
        return scienceProgress;
    }

    public void setScienceProgress(String scienceProgress) {
        this.scienceProgress = scienceProgress;
    }

    public String getEnglishProgress() {
        return englishProgress;
    }

    public void setEnglishProgress(String englishProgress) {
        this.englishProgress = englishProgress;
    }

    public String getMathProgress() {
        return mathProgress;
    }

    public void setMathProgress(String mathProgress) {
        this.mathProgress = mathProgress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

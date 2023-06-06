package com.example.entity;

public class User {
    public int id;
    public String username;
    public String password;
    public String verifyQuestion;
    public String verifyAnswer;

    public User(){

    }

    public User(String username, String password, String verifyQuestion, String verifyAnswer) {
        this.username = username;
        this.password = password;
        this.verifyQuestion = verifyQuestion;
        this.verifyAnswer = verifyAnswer;
    }

    public String getVerifyQuestion() {
        return verifyQuestion;
    }

    public void setVerifyQuestion(String verifyQuestion) {
        this.verifyQuestion = verifyQuestion;
    }

    public String getVerifyAnswer() {
        return verifyAnswer;
    }

    public void setVerifyAnswer(String verifyAnswer) {
        this.verifyAnswer = verifyAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", verifyQuestion='" + verifyQuestion + '\'' +
                ", verifyAnswer='" + verifyAnswer + '\'' +
                '}';
    }
}

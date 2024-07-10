package org.example.backend.Entity;


public class User {
    private Integer id;
    private String username;
    private String password;
    private String mail;
    public User(Integer id, String username, String user_password, String mail) {
        this.id = id;
        this.username = username;
        this.password = user_password;
        this.mail = mail;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
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
    public User(){}
    public void setMail(String mail){ this.mail = mail; }
    public String getMail(){ return mail; }
}

package com.swingapp;

public class User {

    private int id;
    private String fname;
    private String lname;
    private String username;
    private String dob;
    private String password;
    private String description;
    private String imageSource;
    private String authToken;
    private int admin;
    private int numArticles;


    public int getId() { return id; }

    public String getFname() { return fname; }

    public String getLname() { return lname; }

    public String getUsername() { return username; }

    public String getDob() { return dob; }

    public String getPassword() { return password; }

    public String getDescription() { return description; }

    public String getImageSource() { return imageSource; }

    public String getAuthToken() { return authToken; }

    public int getAdmin() { return admin; }

    public int getNumArticles() { return numArticles; }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", username='" + username + '\'' +
                ", dob='" + dob + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", imageSource='" + imageSource + '\'' +
                ", authToken='" + authToken + '\'' +
                ", admin=" + admin +
                ", numArticles=" + numArticles +
                '}';
    }
}

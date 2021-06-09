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
//    public void setId(int id) { this.id = id; }

    public String getFname() { return fname; }
//    public void setFirstName(String fname) { this.fname = fname; }

    public String getLname() { return lname; }
//    public void setLastName(String lname) { this.lname = lname; }

    public String getUsername() { return username; }
//    public void setUsername(String username) { this.username = username; }

    public String getDob() { return dob; }
//    public void setDob(String dob) { this.dob = dob; }

    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }

    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }

    public String getImageSource() { return imageSource; }
//    public void setImageSource(String imageSource) { this.imageSource = imageSource; }

    public String getAuthToken() { return authToken; }
//    public void setAuthToken(String authToken) { this.authToken = authToken; }

    public int getAdmin() { return admin; }
//    public void setAdmin(int admin) { this.admin = admin; }

    public int getNumArticles() { return numArticles; }
//    public void setNumArticles(int numArticles) { this.numArticles = numArticles; }

}

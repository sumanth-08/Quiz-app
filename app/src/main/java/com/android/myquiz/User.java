package com.android.myquiz;

public class User {
    public String name1;
    public String username1;

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String email1;
    public String phone1;
    public String password1;
    private  long coins = 25;

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public User(){

    }
    public  User(String name1, String username1, String email1, String phone1, String password1){
        this.name1 = name1;
        this.username1 = username1;
        this.email1 = email1;
        this.phone1 = phone1;
        this.password1 = password1;

    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.w1953842_cw_oop;

/**
 *
 * @author HP
 */
public class User {
    private final String username;
    private final String password;
    public static boolean firstPurchase;


    public User(String username,String password) {
        this.username = username;
        this.password = password;
        this.firstPurchase = true;
    }

    // getter for username
    public String getUsername() {
        return username;
    }

    // getter for password
    public String getPassword() {
        return password;
    }

    // Method to make the first purchase false
    public static void addPurchase(){
        firstPurchase=false;
    }

}

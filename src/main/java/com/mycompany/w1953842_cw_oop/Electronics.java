/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.w1953842_cw_oop;

/**
 *
 * @author HP
 */
public class Electronics extends Product {
    private final String brand;
    private final String warranty;

    // constructor
    public Electronics(String productID,String productName,double price,int numOfAvailable,boolean clothingItem,String brand,String warranty) {
        super(productID,productName,price,numOfAvailable,clothingItem);
        this.brand = brand;
        this.warranty = warranty;
    }

    // getter for brand
    public String getBrand() {
        return brand;
    }

    // getter for warranty
    public String getWarranty() {
        return warranty;
    }


}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.w1953842_cw_oop;

/**
 *
 * @author HP
 */
public class Clothing extends Product{
    private final String size;
    private final String colour;

    // constructor
    public Clothing(String productID,String productName,double price,int numOfAvailable,boolean clothingItem,String size,String colour){
        super(productID,productName,price,numOfAvailable,clothingItem);
        this.size = size;
        this.colour = colour;
    }

    // getter for size
    public String getSize() {
        return size;
    }

    // getter for colour
    public String getColour() {
        return colour;
    }


}

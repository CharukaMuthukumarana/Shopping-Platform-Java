/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.w1953842_cw_oop;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
/**
 *
 * @author HP
 */
public class ProductTableListener implements ListSelectionListener {
    private final JTable productsTable;
    private final ArrayList<Product> productList;
    private final JLabel id,category,name,additional1,additional2,availability;

    public ProductTableListener(JTable productsTable,ArrayList<Product> productList,JLabel id,JLabel category,
                                JLabel name,JLabel additional1,JLabel additional2,JLabel availability){
        this.productsTable = productsTable;
        this.productList = productList;
        this.id = id;
        this.category = category;
        this.name = name;
        this.additional1 = additional1;
        this.additional2 = additional2;
        this.availability = availability;
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) { // Check if the selection process has finished
            int selectedRow = productsTable.getSelectedRow();
            if (selectedRow != -1) { // Check if any row is selected
                String selectedProductId = productsTable.getValueAt(selectedRow,0).toString();
                Product selectedProduct = null;
                boolean selectedItemIsNull = true;
                for(Product checker : productList){
                    if (checker.getProductID().equals(selectedProductId)) {
                        selectedProduct = checker;
                        selectedItemIsNull = false;
                        break;
                    }
                }
                if(!selectedItemIsNull) {
                    id.setText("Product Id: "+ selectedProduct.getProductID());
                    name.setText("Name: "+ selectedProduct.getProductName());
                    availability.setText("Items Available: "+ selectedProduct.getNumOfAvailable());
                    if (selectedProduct.isClothingItem()) {
                        Clothing selectedClothe = (Clothing)selectedProduct;
                        category.setText("Category: Clothing");
                        additional1.setText("Size: "+ selectedClothe.getSize());
                        additional2.setText("Colour: "+ selectedClothe.getColour());
                    } else {
                        Electronics selectedElectronic = (Electronics)selectedProduct;
                        category.setText("Category: Electronics");
                        additional1.setText("Brand: "+selectedElectronic.getBrand());
                        additional2.setText("Warranty Period: "+selectedElectronic.getWarranty().replace("m"," months"));

                    }
                }
            }

        }
    }
}

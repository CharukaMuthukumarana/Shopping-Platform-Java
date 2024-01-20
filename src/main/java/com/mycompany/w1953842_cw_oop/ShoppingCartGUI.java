/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.w1953842_cw_oop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author HP
 */
public class ShoppingCartGUI extends JFrame {
    JLabel totalPrice,firstPurchaseDiscountPrice,threeItemsDiscountPrice,finalPrice;

    public ShoppingCartGUI(CartTableModel cartTableModel){

        this.setLayout(new BorderLayout());

        JTable cartTable = new JTable(cartTableModel);
        
        // Add the cartTable to a JScrollPane and add it to the frame
        JScrollPane scrollPane = new JScrollPane(cartTable);

        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(new EmptyBorder(40,20,10,20));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(scrollPane,BorderLayout.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<3; i++){
            cartTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        cartTable.setRowHeight(30);
        cartTable.getColumnModel().getColumn(0).setMinWidth(150);

        // set header attributes
        JTableHeader tableHeader = cartTable.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 35));

        //bottom panel - contains four panels for each row
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(5,1,0,10));
        bottomPanel.setBorder(new EmptyBorder(15,20,35,100));

        //panels for 4 rows - each panel has 2 labels
        JPanel totalPanel,firstPurchasePanel,threeItemsPanel,finalTotalPanel;

        totalPanel = new JPanel(new BorderLayout());
        firstPurchasePanel = new JPanel(new BorderLayout());
        threeItemsPanel = new JPanel(new BorderLayout());
        finalTotalPanel = new JPanel(new BorderLayout());
        JPanel btnPanel=new JPanel(new BorderLayout());
        JButton checkout = new JButton("Checkout");
        
// Action Listener to Checkout button make update first purchase
        checkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cartBuy();
            }
        });

        // labels for fill the
        JLabel totalLabel,firstPurchaseLabel, threeItemsLabel,finalTotalLabel;

        // labels for prices description
        totalLabel = new JLabel("Total      ");
        firstPurchaseLabel = new JLabel("First Purchase Discount (10%)      ");
        threeItemsLabel = new JLabel("Three Items in same Category (20%)      ");
        finalTotalLabel = new JLabel("Final Total      ");

        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        firstPurchaseLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        threeItemsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        finalTotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        checkout.setHorizontalAlignment(SwingConstants.LEFT);

        // labels for prices values
        totalPrice = new JLabel("0 LKR");
        firstPurchaseDiscountPrice = new JLabel("-0 LKR");
        threeItemsDiscountPrice = new JLabel("-0 LKR");
        finalPrice = new JLabel("0 LKR");

        Dimension priceLabelsSize = new Dimension(100,20);
        totalPrice.setPreferredSize(priceLabelsSize);
        firstPurchaseDiscountPrice.setPreferredSize(priceLabelsSize);
        threeItemsDiscountPrice.setPreferredSize(priceLabelsSize);
        finalPrice.setPreferredSize(priceLabelsSize);

        checkout.setPreferredSize(new Dimension(100,20));

        totalPanel.add(totalLabel,BorderLayout.CENTER);
        totalPanel.add(totalPrice,BorderLayout.EAST);
        firstPurchasePanel.add(firstPurchaseLabel,BorderLayout.CENTER);
        firstPurchasePanel.add(firstPurchaseDiscountPrice,BorderLayout.EAST);
        threeItemsPanel.add(threeItemsLabel,BorderLayout.CENTER);
        threeItemsPanel.add(threeItemsDiscountPrice,BorderLayout.EAST);
        finalTotalPanel.add(finalTotalLabel,BorderLayout.CENTER);
        finalTotalPanel.add(finalPrice,BorderLayout.EAST);
        btnPanel.add(checkout,BorderLayout.WEST);


        bottomPanel.add(totalPanel);
        bottomPanel.add(firstPurchasePanel);
        bottomPanel.add(threeItemsPanel);
        bottomPanel.add(finalTotalPanel);
        bottomPanel.add(btnPanel);

        add(tablePanel,BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);
    }
// Method to update the first purchase
    private void cartBuy() {
        User.addPurchase();
    }
    
// Update Prices method with discounts    
    public void updatePrices(double totalPrice,ShoppingCart shoppingCart,boolean firstPurchase){
        double discount1 = 0;
        double discount2 = shoppingCart.getThreeSameItemsDiscount();

        this.totalPrice.setText((Math.round(totalPrice* 100.0) / 100.0) +" LKR");
        if(firstPurchase) {
            discount1 = Math.round((totalPrice*0.1) * 100.0) / 100.0;
            this.firstPurchaseDiscountPrice.setText("-" + discount1 + " LKR");
        }

        this.threeItemsDiscountPrice.setText("-"+ discount2 +" LKR");

        totalPrice = totalPrice-(discount1+discount2);
        this.finalPrice.setText((Math.round(totalPrice* 100.0) / 100.0) +" LKR");
    }

}

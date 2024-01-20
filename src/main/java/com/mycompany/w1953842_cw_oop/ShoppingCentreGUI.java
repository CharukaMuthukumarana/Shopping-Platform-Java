/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.w1953842_cw_oop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;
import static com.mycompany.w1953842_cw_oop.User.firstPurchase;

/**
 *
 * @author HP
 */
public class ShoppingCentreGUI extends JFrame {
//,boolean firstPurchase
     public ShoppingCentreGUI(ArrayList<Product> productList,ShoppingCart shoppingCart){
          JPanel selectProductPanel,selectProductAndCartBtn,topPanel,bottomPanel;
          JLabel selectProductCategory;
          String[] categories = {"All","Electronics","Clothing"};
          JComboBox<String> selectProductCategoryComboBox;
          JButton cartBtn,addToCartBtn;

          this.setLayout(new BorderLayout());
          // selectProductPanel
          selectProductPanel = new JPanel();
          selectProductPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,30));

          // components
          selectProductCategory = new JLabel("  Select Product Category");
          selectProductCategoryComboBox = new JComboBox<>(categories);
          selectProductCategoryComboBox.setPreferredSize(new Dimension(120,20));

          // add components
          selectProductPanel.add(selectProductCategory);
          selectProductPanel.add(selectProductCategoryComboBox);

          // selectProductAndCartBtn
          selectProductAndCartBtn = new JPanel();
          selectProductAndCartBtn.setLayout(new BorderLayout());
          // components
          JPanel cartButtonPanel = new JPanel();
          cartButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
          cartBtn = new JButton("Shopping Cart");
          cartBtn.setPreferredSize(new Dimension(120,30));
          cartButtonPanel.add(cartBtn);

          // add components
          selectProductAndCartBtn.add(selectProductPanel,BorderLayout.CENTER);
          selectProductAndCartBtn.add(cartButtonPanel,BorderLayout.EAST);

          // topPanel
          topPanel = new JPanel();
          topPanel.setLayout(new BorderLayout());
          // components
          ProductTableModel tableModel = new ProductTableModel(productList);
          JTable productsTable = new JTable(tableModel);

          productsTable.setRowHeight(30);
          productsTable.getColumnModel().getColumn(4).setMinWidth(150);

          JPanel tablePanel = new JPanel();
          tablePanel.setBorder(new EmptyBorder(40,0,10,0));
          tablePanel.setLayout(new BorderLayout());
          tablePanel.add(new JScrollPane(productsTable),BorderLayout.CENTER);



          // add components
          topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
          topPanel.add(selectProductAndCartBtn,BorderLayout.NORTH);
          topPanel.add(tablePanel,BorderLayout.CENTER);

          // bottomPanel
          bottomPanel = new JPanel();
          bottomPanel.setLayout(new BorderLayout());
          bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
          // components
          JPanel productDetails = new JPanel();
          productDetails.setLayout(new GridLayout(7,1,10,10));
          productDetails.setBorder(new EmptyBorder(0, 30, 30, 10));

          JLabel productDetailsHeader = new JLabel("Selected Product - Details");
          JLabel id,category,name,additional1,additional2,availability;
          id = new JLabel("");
          category = new JLabel("");
          name = new JLabel("");
          additional1 = new JLabel("");
          additional2 = new JLabel("");
          availability = new JLabel("");

          productDetails.add(productDetailsHeader);
          productDetails.add(id);
          productDetails.add(category);
          productDetails.add(name);
          productDetails.add(additional1);
          productDetails.add(additional2);
          productDetails.add(availability);

          JPanel addToCartPanel = new JPanel();
          addToCartPanel.setLayout(new FlowLayout());
          addToCartBtn = new JButton("Add to Shopping Cart");
          addToCartPanel.add(addToCartBtn);

          // add components
          bottomPanel.add(productDetails,BorderLayout.CENTER);
          bottomPanel.add(addToCartPanel,BorderLayout.SOUTH);

          //add to frame
          this.add(topPanel,BorderLayout.CENTER);
          this.add(bottomPanel,BorderLayout.SOUTH);

          // product table selection model
          productsTable.getSelectionModel().addListSelectionListener(new ProductTableListener(productsTable,
                  productList,id,category,name,additional1,additional2,availability));

          // sorting instance to sort table columns
          TableRowSorter<ProductTableModel> sorter = new TableRowSorter<>(tableModel);
          productsTable.setRowSorter(sorter);

          // Action Listener to filtering products on products table
          selectProductCategoryComboBox.addActionListener(new ProductCategoryComboBoxHandler
                  (selectProductCategoryComboBox,productsTable,sorter));

          // cart table model to create cart GUI
          CartTableModel cartTableModel = new CartTableModel(shoppingCart);

          // creating cart GUI  (still not visible)
          ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(cartTableModel);
          shoppingCartGUI.setTitle("Shopping Cart");
          shoppingCartGUI.setSize(700, 450);
          shoppingCartGUI.setVisible(false);

          //Action Listener to add to cart button with it's availability
          addToCartBtn.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                    int selectedRow = productsTable.getSelectedRow();
                    if(selectedRow != -1) {
                         String selectedProductId = productsTable.getValueAt(selectedRow, 0).toString();
                         Map<Product, Integer> quantityOnCart = cartTableModel.getShoppingCart().getQuantityOnCart();

                         // get the selected product and check its availability
                         for (Product checker : productList) {
                              if (checker.getProductID().equals(selectedProductId) && checker.getNumOfAvailable()>0) {
                                   int availableItems;
                                   if(quantityOnCart.containsKey(checker)){
                                        availableItems = checker.getNumOfAvailable() - quantityOnCart.get(checker);
                                   }
                                   else availableItems = checker.getNumOfAvailable();

                                   if (availableItems > 0) {
                                        cartTableModel.getShoppingCart().addToCart(checker);
                                        shoppingCartGUI.updatePrices(cartTableModel.getShoppingCart().calculateTotal(),cartTableModel.getShoppingCart(), firstPurchase);
                                        cartTableModel.fireTableDataChanged();
                                   }

                                   break;
                              }
                         }

                    }
               }
          });

          cartBtn.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                    if (!shoppingCartGUI.isActive()){
                         shoppingCartGUI.setVisible(true);
                    }
               }
          });

          this.addWindowListener(new WindowAdapter() {
               @Override
               public void windowClosing(WindowEvent e) {
                    shoppingCartGUI.dispose();
               }
          });
     }

}

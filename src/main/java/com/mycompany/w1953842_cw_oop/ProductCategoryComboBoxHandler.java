/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.w1953842_cw_oop;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author HP
 */
public class ProductCategoryComboBoxHandler implements ActionListener {
    JComboBox<String> selectProductCategoryComboBox;
    JTable productsTable;
    TableRowSorter<ProductTableModel> sorter;

    public ProductCategoryComboBoxHandler(JComboBox<String> selectProductCategoryComboBox,JTable productsTable,TableRowSorter<ProductTableModel> sorter){
        this.selectProductCategoryComboBox = selectProductCategoryComboBox;
        this.productsTable = productsTable;
        this.sorter = sorter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = selectProductCategoryComboBox.getSelectedIndex();
        if(selectedIndex == 1){
            sorter.setRowFilter(RowFilter.regexFilter("^[E]", 2)); // Hide clothing items
        } else if (selectedIndex == 2) {
            sorter.setRowFilter(RowFilter.regexFilter("^[C]",2));
        }
        else sorter.setRowFilter(null);
    }
}

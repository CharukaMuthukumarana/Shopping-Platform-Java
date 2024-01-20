/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.w1953842_cw_oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author HP
 */
class WestminsterShoppingManagerTest {

    private WestminsterShoppingManager shoppingManager;

    @BeforeEach
    void setUp() {
        shoppingManager = new WestminsterShoppingManager();
    }

    @Test
    void getInput_validInput_returnsEnteredInput() {
        String input = "Test";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        String result = shoppingManager.getInput("Enter a value: ", "testInput", true);

        assertEquals(input.trim(), result);
    }

    @Test
    void getProductId_validProductId_returnsEnteredProductId() {
        String input = "A1234";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        String result = shoppingManager.getProductId(true);

        assertEquals(input, result);
    }

    @Test
    void getCustomInputs_validInput_returnsEnteredInput() {
        String input = "M";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        String[] validSizes = {"XS", "S", "M", "L", "XL"};
        String result = shoppingManager.getCustomInputs(validSizes, "Enter a size: ", "size");

        assertEquals(input, result);
    }

    @Test
    void updateProductList_addElectronicProduct_successfullyAddsProduct() {
        String input = "Test\n50.0\n10\nSamsung\n12m";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        shoppingManager.UpdateProductList(false);

        ArrayList<Product> productList = shoppingManager.productList;
        assertEquals(1, productList.size());
        assertTrue(productList.get(0) instanceof Electronics);
    }

    @Test
    void updateProductList_addClothingProduct_successfullyAddsProduct() {
        String input = "Test\n50.0\n10\nNike\nM\nRed";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        shoppingManager.UpdateProductList(true);

        ArrayList<Product> productList = shoppingManager.productList;
        assertEquals(1, productList.size());
        assertTrue(productList.get(0) instanceof Clothing);
    }

    @Test
    void deleteProduct_validProductIndex_successfullyDeletesProduct() {
        // Adding a product to the list for testing deletion
        shoppingManager.productList.add(new Electronics("E1234", "TestProduct", 50.0, 10, false, "Samsung", "12m"));

        String input = "1\ny";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        shoppingManager.deleteProduct();

        assertEquals(0, shoppingManager.productList.size());
    }

    @Test
    void printProductList_emptyList_printsEmptyMessage() {
        // Clearing the product list for this test
        shoppingManager.productList.clear();

        // Redirecting System.out for testing output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        shoppingManager.printProductList();

        String expectedOutput = "Product list is empty.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void deleteProduct_validProductID_successfulDeletion() {
        // Create a sample product and add it to the product list
        Electronics testElectronics = new Electronics("E1234", "TestProduct", 50.0, 10, false, "Samsung", "12m");
        shoppingManager.productList.add(testElectronics);

        // Mock user input for the deleteProduct method
        String input = "1\ny";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Redirect System.out for testing output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call deleteProduct method
        shoppingManager.deleteProduct();

        // Verify that the product has been deleted
        assertEquals(0, shoppingManager.productList.size());
        assertFalse(shoppingManager.productList.contains(testElectronics));

        // Verify output
        String expectedOutput = "Product with product ID \"E1234\" has been deleted";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    void saveToFile_validData_successfullySavesToFile() {
        // Create sample products and add them to the product list
        Electronics testElectronics = new Electronics("E1234", "TestProduct", 50.0, 10, false, "Samsung", "12m");
        Clothing testClothing = new Clothing("C5678", "ClothingProduct", 30.0, 5, true, "M", "Blue");
        shoppingManager.productList.add(testElectronics);
        shoppingManager.productList.add(testClothing);

        // Redirect System.out for testing output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call saveToFile method
        shoppingManager.saveToFile(shoppingManager.productList, false);

        // Verify output
        String expectedOutput = "Successfully saved to a file \"product_data.txt\"";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

}
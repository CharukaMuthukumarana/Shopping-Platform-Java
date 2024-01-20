/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.w1953842_cw_oop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author HP
 */
public class WestminsterShoppingManager {
    private static final Scanner input = new Scanner(System.in);
    private static final String fileName = "product_data.txt";
    private static final String CANCEL_INPUT = "#";

    public final ArrayList<Product> productList = new ArrayList<>(50);
    public final ArrayList<User> users = new ArrayList<>(50);
    
    
    public String getInput(String prompt,String inputName,boolean stringValue){
        boolean gotStringInput = false;
        String enteredInput;
        do {
            System.out.print(prompt);
            enteredInput = (input.nextLine()).trim();

            // if the method needed to be canceled -> this value can be entered if user needs to stop add product
            // process, therefore this value should be passed without going through any validation.
            if(enteredInput.equals(CANCEL_INPUT) && !inputName.equals("menu option")) return enteredInput;

            else if (enteredInput.isEmpty()){
                System.out.println(inputName + " cannot be empty. Please enter a valid input.");
                continue;
            }
            else {
                if(!stringValue){

                    try {
                        double enteredNum = Double.parseDouble(enteredInput);

                        if (enteredNum == 0) {
                            System.out.println(inputName + " cannot be zero");
                            continue;
                        }
                        else if (enteredNum < 0) {
                            System.out.println(inputName + " cannot be negative");
                            continue;
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Invalid number format for " + inputName);
                        continue;
                    }
                }
            }
            gotStringInput = true;
        } while (!gotStringInput);

        return enteredInput;
    }

// Method to display the main menu and handle user choices
    public void displayMenu() {
        int mainOption;
        
// Main menu options handling

        String menu = """
             \n-------------------------------------------
              *** Westminster Shopping Manager ***
             -------------------------------------------
             1. Add a new product
             2. Delete a product
             3. Print the list of the products
             4. Save in a file
             5. Read from a file
             6. Open a Graphical User Interface (GUI)
             7. Quit program
             \nEnter a menu option :\s""";

        mainOption =  Integer.parseInt(getInput(menu, "menu option", false));

        switch (mainOption) {
            case 1 -> addNewProduct();
            case 2 -> deleteProduct();
            case 3 -> printProductList();
            case 4 -> saveToFile(productList,false);
            case 5 -> readFromFile();
            case 6 -> openGUI();
            case 7 -> {
                System.out.println("Closed");
                System.exit(0);
                return;
            }
            default -> System.out.println("Please enter option from the menu.");
            }
        displayMenu();
    }
    
// Method to add a new product   
    public void addNewProduct() {
        int option;

        do {
            // if product list already contains maximum number of products on it.
            if (productList.size() == 50) {
                System.out.println("Products cannot be added as the maximum limit has been reached.");
                option = 3;
            }
            else {
                String addProductMenu = """
                        \n1. Add a new Electronics product
                        2. Add a new Clothing product
                        3. Return to main menu
                        \nEnter a menu option :\s""";

                option = Integer.parseInt(getInput(addProductMenu,"menu option",false));

                switch (option){
                    case 3 -> {return;}
                    case 1 -> UpdateProductList(false);
                    case 2 -> UpdateProductList(true);
                    default -> System.out.println("Please enter option from the menu.");
                }
            }
        }while (option != 3);
    }
    
// Method to validate and get product ID    
    public String getProductId(boolean toAdd){
        boolean invalidID;
        String ID;

        do {
            invalidID = false;
            ID = getInput("Product ID : ","product ID",true);

            // if the method needed to be canceled (see "getInput" method for more details).
            if(ID.equals(CANCEL_INPUT)) return ID;

            else if(ID.matches("^[A-Z][0-9]{4}$")){
                boolean duplicateID = false;
                String index = "0";
                for(Product checker : productList){
                    if (checker.getProductID().equals(ID)) {
                            duplicateID = true;
                            index = String.valueOf(productList.indexOf(checker));
                            break;
                    }
                }

                if (duplicateID && toAdd) {
                    System.out.println("Item with product ID \""+ID+"\"is already exist in the product list.");
                    invalidID = true;
                }
                else if(!toAdd && !duplicateID){
                    System.out.println("There is no listed product with product ID \""+ID+"\".");
                    invalidID = true;
                }
                else if(!toAdd){return index;}
            }
            else {
                System.out.println("Please enter a string starting with an uppercase letter followed by 4 digits (e.g.,A1234)");
                invalidID = true;
            }
        }while (invalidID);
        return ID;
    }
    
// LIST OF VALID PRODUCT DETAILS    
    String[] validSizes = {"XS","S","M","L","XL","14","14.5","15","15.5","16","16.5","17"};
    String[] validWarrantyPeriods = {"1m","2m","3m","4m","6m","12m","24m","36m","48m"};
    String[] validColours = {"White","Black","Red","Yellow","Blue","Gray","Pink","Green","Purple","Orange"};
    
// Method to get custom inputs from a list of valid options
    public String getCustomInputs(String[] validInputs,String prompt,String inputName){
        boolean invalidInput = true;
        String enteredValue;

        do {
            enteredValue = getInput(prompt,inputName,true);

            // if the method needed to be canceled (see "getInput" method for more details).
            if(enteredValue.equals(CANCEL_INPUT)) return enteredValue;

            for(String checker : validInputs){
                if(checker.equalsIgnoreCase(enteredValue)){
                    invalidInput = false;
                    break;
                }
            }
            if(invalidInput) System.out.println("Please enter a "+ inputName +" from "+ Arrays.toString(validInputs));

        }while (invalidInput);
        return enteredValue;
    }

// Method to update the product list with a new product 
    public void UpdateProductList(boolean isClothingItem){
        String productType = "electronic";
        if(isClothingItem){
            productType = "clothing";
        }
        System.out.println("\nEnter the " +productType+ " item product details");
        String[] info = new String[6];

        for(int i=0; i<6; i++){
            switch (i) {
                case 0 -> info[0] = getProductId(true);
                case 1 -> info[1] = getInput("Product Name : ", "product name", true);
                case 2 -> info[2] = getInput("Price : ", "price", false);
                case 3 -> info[3] = getInput("Number of Available Items : ", "number of available items", false);
                case 4 -> {
                    if (isClothingItem) info[4] = getInput("Brand Name : ", "brand name", true);
                    else info[4] = getCustomInputs(validSizes, "Size : ", "size").toUpperCase();

                }
                case 5 -> {
                    if (isClothingItem) {
                        info[5] = getCustomInputs(validColours, "Colour : ", "colour");
                        productList.add(new Clothing(info[0],info[1],Double.parseDouble(info[2]),Integer.parseInt(info[3]),true,info[4],info[5]));
                    } else {
                        info[5] = getCustomInputs(validWarrantyPeriods, "Warranty Period : ", "warranty period");
                        productList.add(new Electronics(info[0],info[1],Double.parseDouble(info[2]),Integer.parseInt(info[3]),false,info[4],info[5]));
                    }
                }
            }

            // if user decided to leave during the add product process (see "getInput" method for more details).
            if(info[i].equals(CANCEL_INPUT)){
                System.out.println("Adding " +productType+ " product process has been canceled.");
                return;
            }
        }
        System.out.println("Product has been added to the product list.");
    }
    
// Method to delete a product from the list   
    public void deleteProduct() {
        String indexToDelete;
        int option;

        do {
            if (productList.isEmpty()) {
                System.out.println("Product list is empty.");
                option = 3;
            }
            else {
                String deleteProductMenu = """                        
                            \n1. Enter product ID to delete a product
                            2. Return to main menu
                            \nEnter a menu option :\s""";
                option = Integer.parseInt(getInput(deleteProductMenu, "menu option", false));

                switch (option) {
                    case 2 -> {return;}
                    case 1 -> {
                        // getProductID method will return the index of a product with entered product ID.
                        indexToDelete = getProductId(false);

                        if(indexToDelete.equals(CANCEL_INPUT)){
                            System.out.println("Deleting product process has been canceled.");
                            return;
                        }
                        else {
                            Product product = productList.get(Integer.parseInt(indexToDelete));
                            System.out.println(toString(product));

                            String decisionPrompt = """
                                \nDelete the product with product ID \"""" + product.getProductID() + "\"?\n" + """
                                Enter "y" for yes or "n" for no :\s""";

                            String decision = getInput(decisionPrompt,"decision",true).toLowerCase();
                            boolean invalidDecision = false;
                            do {
                                if (decision.equals("y")) {
                                    productList.remove(product);
                                    System.out.println("""
                                            Product with product ID \"""" + product.getProductID() + "\"" + """
                                            has been deleted"
                                            Number of products left in the system :\s""" + productList.size());
                                } else if (!decision.equals("n")) {
                                    System.out.println("Please enter \"y\" or \"n\"");
                                    invalidDecision = true;
                                }
                            }while (invalidDecision);
                        }
                    }
                    default -> System.out.println("Please enter option from the menu.");
                }
            }
        }while (option != 3);
    }
    
// Method to print the product list 
    public void printProductList() {
        if (productList.isEmpty()) {
            System.out.println("Product list is empty.");
            return;
        }
        // **********************************************************
        productList.sort(Comparator.comparing(Product::getProductID));

        // Print the sorted productList
        for (Product product : productList) {
            System.out.println(toString(product));
        }
    }
    
// Method to create a string representation of a product    
    private String toString(Product product) {
        String additionalDetails;
        String productType = "Electronics";
        if (product.isClothingItem()) {
            Clothing toDelete = (Clothing) product;
            productType = "Clothing";
            additionalDetails = """
                \nSize                      :\s""" + toDelete.getSize() + """
                \nColour                    :\s""" + toDelete.getColour();

        } else {
            Electronics toDelete = (Electronics) product;
            additionalDetails = """
                 \nBrand Name                :\s""" + toDelete.getBrand() + """
                \nWarranty Period           :\s""" + toDelete.getWarranty().replace("m"," months");
        }
        String productDetails = """
            ----------------------------------------
            Product Details of Product Id :\s"""+product.getProductID()+ """
            \n----------------------------------------
            Product Type              :\s"""+productType+ """
            \nProduct Name              :\s""" + product.getProductName() + """
            \nPrice                     :\s""" + product.getPrice() + """
            \nNumber of Available Items :\s""" + product.getNumOfAvailable();

        return productDetails+additionalDetails;
    }
    
// Method to save product data to a file    
    public void saveToFile(ArrayList<Product> productList,boolean append) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,append));
            String productType; // "C" for clothing and "E" for electronics.
            for (Product product : productList) {
                if(product.isClothingItem()){
                    Clothing cProduct = (Clothing) product;
                    productType = "C";// Clothing
                    writer.write(productType+"#"+cProduct.getProductID()+"#"+cProduct.getProductName()+"#"+cProduct.getPrice()+"#"+
                            cProduct.getNumOfAvailable()+"#"+cProduct.getSize()+"#"+cProduct.getColour());
                }
                else {
                    Electronics eProduct = (Electronics) product;
                    productType = "E"; // Electronics
                    writer.write(productType+"#"+eProduct.getProductID()+"#"+eProduct.getProductName()+"#"+eProduct.getPrice()+"#"+
                            eProduct.getNumOfAvailable()+"#"+eProduct.getBrand()+"#"+eProduct.getWarranty());

                }
                writer.write("\n"); //move to the next line.
            }
            writer.close();
            System.out.println("Successfully saved to a file \"product_data.txt\"");
        } catch (Exception e) { //if file can not create for some reason.
            System.out.println("Error : "+e);
        }
    }
    
// Method to read product data from a file    
    public void readFromFile() {
        try {
            File file = new File("product_data.txt"); //new file obj.
            Scanner reader = new Scanner(file); //new scanner obj.


            if(!reader.hasNextLine()) {
                System.out.println("File \"" + fileName + "\" is empty.");
                reader.close();
                return;
            }

            while (reader.hasNextLine()) { //if there is no more lines to read, loop will end.
                String line = reader.nextLine(); //assign whole line in to a String variable.
                String[] attributes = line.split("#"); //create array using split items of line variable.

                if(attributes[0].equals("E")){
                    productList.add(new Electronics(attributes[1],attributes[2],Double.parseDouble(attributes[3]),
                            Integer.parseInt(attributes[4]),false,attributes[5],attributes[6]));
                }
                else {
                    productList.add(new Clothing(attributes[1],attributes[2],Double.parseDouble(attributes[3]),
                            Integer.parseInt(attributes[4]),true,attributes[5],attributes[6]));
                }
            }
            reader.close();
            System.out.println("Successfully loaded from a file \""+fileName+"\".");
        } catch (FileNotFoundException e1) {
            System.out.println("Loading failed: Cannot find the file \""+fileName+"\".");

        }

    }
    
// Method to open the GUI    
    public void openGUI(){
        LoginGUI loginGUI = new LoginGUI(users,productList);
        loginGUI.setTitle("Login or Register");
        loginGUI.setSize(500, 500);
        loginGUI.setResizable(false);
        loginGUI.setVisible(true);
    }
}

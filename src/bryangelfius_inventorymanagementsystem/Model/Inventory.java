/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_inventorymanagementsystem.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author bgelfius
 */
public class Inventory {
    private static ObservableList<Product> products = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int currPartID = 1;
    private static int currProductID = 1;
    
    
    public Inventory() {
        // default
           
    }
    
    public static ObservableList<Product> getAllProducts() {
        return products;
    }
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public static int getCurrPartID() {
        return currPartID;
        
    }
    
    public static int getCurrProductID () {
        return currProductID;
    }
    public static void addProduct(Product product) {
        products.add(product);
        ++currProductID;
    }
    public static boolean removeProduct(int productID) {
        // todo figure out if product exists
        products.remove(productID);
        
        return true;
    }
    
    public static Product lookupProduct(int productID) {
        return products.get(productID);
    }
    
    public static void updateProduct(int productID) {
        //todo figure out what update means
        
    }
      public static boolean deleteProduct(Product product) {
        products.remove(product);
        return true;
   }
        
    public static void addpart(Part part) {
        allParts.add(part);
        ++currPartID;
    }
    
    public static boolean deletePart(Part part) {
        allParts.remove(part);
        return true;
   }
    public static Part lookupPart(int partID) {
        return allParts.get(partID);
    }
    
    public static  void updatePart(Part p) {
        //todo figure out update
        allParts.set(p.getPartID() -1, p);
    }
     public static  void updateProduct(Product p) {
        //todo figure out update
        products.set(p.getProductID() -1, p);
    }
}

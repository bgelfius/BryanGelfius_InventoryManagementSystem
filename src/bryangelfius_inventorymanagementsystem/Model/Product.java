/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_inventorymanagementsystem.Model;

import java.util.ArrayList;
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
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private final IntegerProperty productID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;
    
    private int rc;
    private ArrayList errorList;
    
    public Product() {
        // default
        this.productID = new SimpleIntegerProperty();
        this.name  = new SimpleStringProperty();
        this.price  = new SimpleDoubleProperty();
        this.inStock  = new SimpleIntegerProperty();
        this.min = new SimpleIntegerProperty();
        this.max = new SimpleIntegerProperty();
        
    }
    
    public IntegerProperty getProductIDProperty() {
        return this.productID;
    }
    public IntegerProperty getInStockProperty() {
        return this.inStock;
    }
    public IntegerProperty getMinProperty() {
        return this.min;
    }
    public IntegerProperty getMaxProperty() {
        return this.max;
    }
    public StringProperty getNameProperty() {
        return this.name;
    }
    public DoubleProperty getPriceProperty() {
        return this.price;
    }
    
    
    public  void setName(String name) {
        this.name.set(name);
    } 
    public  String getName() {
        return name.get();
    }
    public  void setPrice(double price) {
        this.price.set(price);
    }
    public  double getPrice() {
        return price.get();
    }
    public  void setInStock(int inStock) {
        this.inStock.set(inStock);
        
    } 
    public  int getInStock() {
        return inStock.get();
    }
    public  void setMin(int min) {
        this.min.set(min);
    } 
    public  int getMin() {
        return min.get();
    }
    public  void setMax(int max) {
        this.max.set(max);
    }        
    public  int getMax() {
        return max.get();
    }
    public  void setProductID(int productID) {
        this.productID.set(productID);
    }
    public  int getProductID() {
        return productID.get();
    }
     public ObservableList<Part> getAssociatedParts() {
        return this.associatedParts;
    }
     
    public void addAssociatedPart(Part associatedPart) {
        this.associatedParts.add(associatedPart);
    }
    public void addAssociatedParts(ObservableList<Part>  associatedParts) {
        this.associatedParts.setAll(associatedParts);
    }
    public boolean removeAssociatedPart(int partID) {
        // todo figure out if part is in list 
        this.associatedParts.remove(partID);
        return true;
        
    }
    public Part lookupAssociatedPart(int partID) {
        return this.associatedParts.get(partID);
    }

    
      public int validateProduct() {
                
        this.errorList = new ArrayList();
        this.rc = 0;
        
        if (this.getMax() > this.getMin()) {
            ;
        } else {
            this.rc = -1;
            this.errorList.add("Maximum value must be greater than minimum value");
        }
        
        if (this.getMin() < this.getMax()) {
            ;
        } else {
            this.rc = -1;
            this.errorList.add("Minimum value must be less than maximum value");
        }
        
        
        if (this.getInStock() <= this.getMax() && this.getInStock() >= this.getMin()) {
            ;
        } else {
            this.rc = -1;
            this.errorList.add("Inventory must be between minimum and maximum value");
        }
        
 
        
        if (this.getName().trim().length() > 0) {
            ;
        } else {
            this.rc = -1;
            this.errorList.add("Must have a name");
        }
        
        if (this.getPrice() > 0) {
            ;
        } else {
            this.rc = -1;
            this.errorList.add("Must have a price");
        }
        
        if (associatedParts.isEmpty()) {
            this.rc = -1;
            this.errorList.add("Must have at least 1 part");
        }
        
        return this.rc;
        
    }
 
    public ArrayList getErrors() {
        
        if (this.rc == -1 ){
            return this.errorList;
        } else {
            return null;
        }
    } 
}

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

/**
 *
 * @author bgelfius
 */
public abstract class Part {
    private final IntegerProperty partID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;
    
    private int rc;
    private ArrayList errorList;
    
    public Part() {
        // default
        this.partID = new SimpleIntegerProperty();
        this.name  = new SimpleStringProperty();
        this.price  = new SimpleDoubleProperty();
        this.inStock  = new SimpleIntegerProperty();
        this.min = new SimpleIntegerProperty();
        this.max = new SimpleIntegerProperty();
        
    }
    public IntegerProperty getPartIDProperty() {
        return this.partID;
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
    
    public DoubleProperty getPriceProperty() {
        return this.price;
    }
    
    public StringProperty getNameProperty() {
        return this.name;
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
    public  void setPartID(int partID) {
        this.partID.set(partID);
    }
    public  int getPartID() {
        return partID.get();
    }
    
    public int validatePart() {
                
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

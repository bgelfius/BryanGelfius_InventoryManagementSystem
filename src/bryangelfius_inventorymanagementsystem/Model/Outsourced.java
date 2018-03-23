/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_inventorymanagementsystem.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author bgelfius
 */
public class Outsourced  extends Part {
     private final StringProperty companyName;
     
     public Outsourced () {
            // default
        this.companyName = new SimpleStringProperty();
     
     }
     public StringProperty getCompanyNameProperty() {
         return this.companyName;
     }
     public void setCompanyName(String companyName) {
         this.companyName.set(companyName);
     }
     
     public String getCompanyName() {
         return this.companyName.get();
     }
}


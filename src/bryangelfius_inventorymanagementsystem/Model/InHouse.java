/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_inventorymanagementsystem.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author bgelfius
 */
public class InHouse extends Part {
    
     private final IntegerProperty machineID;
     
     public InHouse () {
            // default
        this.machineID = new SimpleIntegerProperty();
     
     }
    public IntegerProperty getMachineIDProperty() {
         return this.machineID;
     }
    
     public void setMachineID(int machineID) {
         this.machineID.set(machineID);
     }
     
     public int getMachineID() {
         return this.machineID.intValue();
     }
}

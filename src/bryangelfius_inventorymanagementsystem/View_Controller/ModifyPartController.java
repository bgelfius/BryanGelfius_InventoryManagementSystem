/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_inventorymanagementsystem.View_Controller;

import bryangelfius_inventorymanagementsystem.Model.InHouse;
import bryangelfius_inventorymanagementsystem.Model.Inventory;
import bryangelfius_inventorymanagementsystem.Model.Outsourced;
import bryangelfius_inventorymanagementsystem.Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import static bryangelfius_inventorymanagementsystem.View_Controller.MainScreenController.selectedPart;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
        
 /**
 * FXML Controller class
 *
 * @author bgelfius
 */
public class ModifyPartController implements Initializable {

    @FXML    private Label partID;
    @FXML    private  RadioButton InHouseRadioButton;
    @FXML    private  RadioButton OutsourceRadioButton;
    @FXML    private TextField inv;
    @FXML    private TextField name;
    @FXML    private TextField price;
    @FXML    private TextField min;
    @FXML    private TextField max;
    
    @FXML    private Label companymachineLabel;
    
    @FXML    private TextField companymachineID;
    
    private ToggleGroup wherebuilt;
    private Part modifyPart;
    
    public void handleSave(ActionEvent event) throws IOException {
        //int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        //personTable.getItems().remove(selectedIndex);
         boolean error = false;
         
    try {     
         if (this.companymachineLabel.getText().equals("Machine ID")) {
            //inhouse
            InHouse ihPart = new InHouse();
            ihPart.setName(name.getText());
            ihPart.setMax(Integer.parseInt(max.getText()));
            ihPart.setMin(Integer.parseInt(min.getText()));
            ihPart.setPrice(Double.parseDouble(price.getText()));
            ihPart.setInStock(Integer.parseInt(inv.getText()));
            ihPart.setMachineID(Integer.parseInt(companymachineID.getText()));
            ihPart.setPartID(Integer.parseInt(partID.getText()));
            ihPart.setMachineID(Integer.parseInt(companymachineID.getText()));
            if (ihPart.validatePart() == 0) {
               Inventory.updatePart(ihPart);
            } else {
                error = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error: Modifying Part");
                alert.setHeaderText("Missing Information");
                alert.setContentText(ihPart.getErrors().get(0).toString());
                alert.showAndWait();
            }
            

        } else {
            //outsource
            Outsourced osPart = new Outsourced();
            osPart.setName(name.getText());
            osPart.setMax(Integer.parseInt(max.getText()));
            osPart.setMin(Integer.parseInt(min.getText()));
            osPart.setPrice(Double.parseDouble(price.getText()));
            osPart.setInStock(Integer.parseInt(inv.getText()));
            osPart.setCompanyName(companymachineID.getText());
            osPart.setPartID(Integer.parseInt(partID.getText()));
             if (osPart.validatePart() == 0) {
               Inventory.updatePart(osPart);
            } else {
                error = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error: Modifying Part");
                alert.setHeaderText("Missing Information");
                alert.setContentText(osPart.getErrors().get(0).toString());
                alert.showAndWait();
            }
            
        }
        
     
        
        
        if (!error) {
            Parent main_page = FXMLLoader.load( getClass().getResource("MainScreen.fxml"));

            Scene main_page_scene = new Scene(main_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(main_page_scene);
            app_stage.show();
         }
         
                
    } 
    catch (NumberFormatException e) {
            error = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error: Modifying Part");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Number field is blank");
            alert.showAndWait();
        }
    }
    
    public void handleCancel(ActionEvent event) throws IOException  {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Cancel Modify Part");
        alert.setContentText("Are you sure you want to lose all your changes?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            Parent main_page = FXMLLoader.load( getClass().getResource("MainScreen.fxml"));
            Scene main_page_scene = new Scene(main_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(main_page_scene);
            app_stage.show();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
        

    }
    
    public void handleWhereBuilt(ActionEvent event) throws IOException {
        
        if (this.wherebuilt.getSelectedToggle().equals(this.OutsourceRadioButton) ) {
            this.companymachineLabel.setText("Company Name");
        }
        if (this.wherebuilt.getSelectedToggle().equals(this.InHouseRadioButton) ) {
            this.companymachineLabel.setText("Machine ID");
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.wherebuilt = new ToggleGroup();
        this.InHouseRadioButton.setToggleGroup(this.wherebuilt);
        this.OutsourceRadioButton.setToggleGroup(wherebuilt);
        
        modifyPart = Inventory.lookupPart(selectedPart());
        
        partID.setText(Integer.toString(modifyPart.getPartID()));
        name.setText(modifyPart.getName());
        inv.setText(Integer.toString(modifyPart.getInStock()));
        price.setText(Double.toString(modifyPart.getPrice()));
        min.setText(Integer.toString(modifyPart.getMin()));
        max.setText(Integer.toString(modifyPart.getMax()));
        
        
        
        if (modifyPart instanceof InHouse) {
            //inhouse
            this.wherebuilt.selectToggle(this.InHouseRadioButton);
            this.companymachineLabel.setText("Machine ID");
            companymachineID.setText( Integer.toString( ((InHouse)modifyPart).getMachineID()) );
        } else {
            //outsource
            this.wherebuilt.selectToggle(this.OutsourceRadioButton);
            this.companymachineLabel.setText("Company Name");
            companymachineID.setText( ((Outsourced)modifyPart).getCompanyName() );
        }
        
        
        
        
        
        
    
        
    }    
    
}

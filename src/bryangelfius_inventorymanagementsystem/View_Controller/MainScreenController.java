/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_inventorymanagementsystem.View_Controller;

import bryangelfius_inventorymanagementsystem.Model.InHouse;
import bryangelfius_inventorymanagementsystem.Model.Inventory;
import bryangelfius_inventorymanagementsystem.Model.Product;
import bryangelfius_inventorymanagementsystem.Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bgelfius
 */
public class MainScreenController implements Initializable {
    @FXML    private TableView<Part> partTable;
    @FXML    private TableColumn<Part, Integer> colPartID;
    @FXML    private TableColumn<Part, String> colPartName;
    @FXML    private TableColumn<Part, Integer> colPartInv;
    @FXML    private TableColumn<Part, Double> colPartPrice;
    
    
    @FXML    private TableView<Product> productTable;
    @FXML    private TableColumn<Product, Integer> colProductID;
    @FXML    private TableColumn<Product, String> colProductName;
    @FXML    private TableColumn<Product, Integer> colProductInv;
    @FXML    private TableColumn<Product, Double> colProductPrice;
      
    @FXML    private TextField SearchBox;
    @FXML    private TextField productSearchBox;
    private static int selPart;
    private static int selProduct;
    
    
    public static int selectedPart(){
        return selPart;
    }
    public static int selectedProduct(){
        return selProduct;
    }
    
    public void handleAddPart(ActionEvent event) throws IOException  {
        Parent part_page = FXMLLoader.load( getClass().getResource("AddPart.fxml"));
        
        Scene part_page_scene = new Scene(part_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(part_page_scene);
        app_stage.show();
    }
    
    public void handleModifyPart(ActionEvent event) throws IOException {
        selPart = partTable.getSelectionModel().getSelectedIndex();
        
        Parent part_page = FXMLLoader.load( getClass().getResource("ModifyPart.fxml"));
        
        Scene part_page_scene = new Scene(part_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(part_page_scene);
        app_stage.show();
    }
    
    public void handlDeletePart(ActionEvent event) throws IOException {
        //int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        //personTable.getItems().remove(selectedIndex);
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Add Part");
        alert.setContentText("Are you sure you want to delete this record?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            selPart = partTable.getSelectionModel().getSelectedIndex();
            Part selected = Inventory.lookupPart(selPart);
            Inventory.deletePart(selected);
        } else {
            // ... user chose CANCEL or closed the dialog
        }
     
        
        //partTable.getItems().remove(selPart);
        
        
    }
    
    public void handleSearchPart() {
        //int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        //personTable.getItems().remove(selectedIndex);
        String srch = SearchBox.getText();
        boolean partFound = false;
        ObservableList<Part> srchParts = FXCollections.observableArrayList();
        ObservableList<Part> displayParts = FXCollections.observableArrayList();
        srchParts = Inventory.getAllParts();
        int foundID = 0;
        
       try {
       // test if a number was entered
          int numval = Integer.parseInt(srch);
          
          for(Part p : srchParts) {
              if (p.getPartID() == numval) {
                  partFound = true;
                  foundID = numval - 1;
              }
          }
          
          
       }
       catch(NumberFormatException e){ 
           // value is a string
           for(Part p : srchParts) {
              if (p.getName().equals(srch)) {
                  partFound = true;
                  foundID = p.getPartID() - 1;
              }
          }
           
       }
       
       
       if (partFound) {
           displayParts.add(Inventory.lookupPart(foundID));
           partTable.setItems(displayParts);   
       } else {
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle("Error: Search");
            alert.setHeaderText("Part not found");
            alert.setContentText("Could not find the part specified");
            alert.showAndWait();
       }
       
        
    }
    
    public void handleAddProduct(ActionEvent event) throws IOException  {
 
        Parent product_page = FXMLLoader.load( getClass().getResource("AddProduct.fxml"));
        
        Scene product_page_scene = new Scene(product_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(product_page_scene);
        app_stage.show();
        
    }
    
   public void handleModifyProduct(ActionEvent event) throws IOException {
        //int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        //personTable.getItems().remove(selectedIndex);
        selProduct = productTable.getSelectionModel().getSelectedIndex();

        Parent product_page = FXMLLoader.load( getClass().getResource("ModifyProduct.fxml"));
        
        Scene product_page_scene = new Scene(product_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(product_page_scene);
        app_stage.show();
    }
    
    public void handlDeleteProduct(ActionEvent event) throws IOException {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Add Product");
        alert.setContentText("Are you sure you want to delete this record?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            selProduct = productTable.getSelectionModel().getSelectedIndex();
            Product selected = Inventory.lookupProduct(selProduct);
            
            if (selected.getAssociatedParts().size() > 0) {
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error: Delete");
                alert.setHeaderText("Product cannot be deleted");
                alert.setContentText("Product has at least one part and cannot be deleted");
                alert.showAndWait();
            } else {
                Inventory.deleteProduct(selected);
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }

      
        
    }
    
    public void handleSearchProduct() {
        //int publicselectedIndex = personTable.getSelectionModel().getSelectedIndex();
        //personTable.getItems().remove(selectedIndex);
        String srch = productSearchBox.getText();
        boolean productFound = false;
        ObservableList<Product> srchProduct = FXCollections.observableArrayList();
        ObservableList<Product> displayProduct = FXCollections.observableArrayList();
        srchProduct = Inventory.getAllProducts();
        int foundID = 0;
        
       try {
       // test if a number was entered
          int numval = Integer.parseInt(srch);
          
          for(Product p : srchProduct) {
              if (p.getProductID() == numval) {
                  productFound = true;
                  foundID = numval - 1;
              }
          }
          
          
       }
       catch(NumberFormatException e){ 
           // value is a string
           for(Product p : srchProduct) {
              if (p.getName().equals(srch)) {
                  productFound = true;
                  foundID = p.getProductID() - 1;
              }
          }
           
       }
       
       
       if (productFound) {
           displayProduct.add(Inventory.lookupProduct(foundID));
           productTable.setItems(displayProduct);   
       } else {
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle("Error: Search Product");
            alert.setHeaderText("Product not found");
            alert.setContentText("Could not find the product specified");
            alert.showAndWait();
       }
       
    }
    
    
   public void handleExit() {
        //Stage stage = (Stage) exitButton.getScene().getWindow();
        //stage.close();
        Platform.exit();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO\
                
        colPartID.setCellValueFactory(cellData -> cellData.getValue().getPartIDProperty().asObject());
        colPartName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        colPartInv.setCellValueFactory(cellData -> cellData.getValue().getInStockProperty().asObject());
        colPartPrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        partTable.setItems(Inventory.getAllParts());   
        
        colProductID.setCellValueFactory(cellData -> cellData.getValue().getProductIDProperty().asObject());
        colProductName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        colProductInv.setCellValueFactory(cellData -> cellData.getValue().getInStockProperty().asObject());
        colProductPrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        productTable.setItems(Inventory.getAllProducts());   
        
        
    }    
    
}

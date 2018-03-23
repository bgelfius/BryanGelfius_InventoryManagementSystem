/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bryangelfius_inventorymanagementsystem.View_Controller;
import bryangelfius_inventorymanagementsystem.Model.Inventory;
import bryangelfius_inventorymanagementsystem.Model.Part;
import bryangelfius_inventorymanagementsystem.Model.Product;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import static bryangelfius_inventorymanagementsystem.View_Controller.MainScreenController.selectedProduct;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
 
/**
 * FXML Controller class
 *
 * @author bgelfius
 */
public class ModifyProductController implements Initializable {
    @FXML    private TableView<Part> partTable;
    @FXML    private TableColumn<Part, Integer> colPartID;
    @FXML    private TableColumn<Part, String> colPartName;
    @FXML    private TableColumn<Part, Integer> colPartInv;
    @FXML    private TableColumn<Part, Double> colPartPrice;
    
    @FXML    private TableView<Part> partTable2;
    @FXML    private TableColumn<Part, Integer> colPartID2;
    @FXML    private TableColumn<Part, String> colPartName2;
    @FXML    private TableColumn<Part, Integer> colPartInv2;
    @FXML    private TableColumn<Part, Double> colPartPrice2;
    
    @FXML    private Label productID;
    @FXML    private TextField inv;
    @FXML    private TextField name;
    @FXML    private TextField price;
    @FXML    private TextField min;
    @FXML    private TextField max;
    @FXML    private TextField searchBox;
 
    private Product modifyProduct;
    private int pickedPartID;   
 
    public void handleSearch() {
        //int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        //personTable.getItems().remove(selectedIndex);
               String srch = searchBox.getText();
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
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Error: Search");
            alert.setHeaderText("Part not found");
            alert.setContentText("Could not find the part specified");
            alert.showAndWait();
       }
       
    }
  
    public void handleAdd() {
        //int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        //personTable.getItems().remove(selectedIndex);
        pickedPartID = partTable.getSelectionModel().getSelectedIndex();
        Part tmp;
        tmp = Inventory.lookupPart(pickedPartID);
        ObservableList<Part> displayPart = FXCollections.observableArrayList();
        displayPart = partTable2.getItems();
        displayPart.add(tmp);
              
        
        colPartID2.setCellValueFactory(cellData -> cellData.getValue().getPartIDProperty().asObject());
        colPartName2.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        colPartInv2.setCellValueFactory(cellData -> cellData.getValue().getInStockProperty().asObject());
        colPartPrice2.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        partTable2.setItems(displayPart);   
    }
    
    public void handleSave(ActionEvent event) throws IOException {
        //int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        //personTable.getItems().remove(selectedIndex);
         Product prod = new Product();
         prod.setInStock(Integer.parseInt(inv.getText()));
         prod.setMax(Integer.parseInt(max.getText()));
         prod.setMin(Integer.parseInt(min.getText()));
         prod.setName(name.getText());
         prod.setPrice(Double.parseDouble(price.getText()));
         prod.setProductID(Integer.parseInt(productID.getText()));
         
         // get associated parts
         ObservableList<Part> displayPart = FXCollections.observableArrayList();
         displayPart = partTable2.getItems();
         prod.addAssociatedParts(displayPart);
         
         ObservableList<Part> tempParts = FXCollections.observableArrayList();
         tempParts = prod.getAssociatedParts();
         double pricetotal = 0;
         
         for (Part x : tempParts) {
             pricetotal += x.getPrice();
         }
         
         if ((prod.getAssociatedParts().size() > 0) && (prod.getPrice() >= pricetotal ) ) {
              Inventory.updateProduct(prod);

               Parent main_page = FXMLLoader.load( getClass().getResource("MainScreen.fxml"));

               Scene main_page_scene = new Scene(main_page);
               Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
               app_stage.setScene(main_page_scene);
               app_stage.show();
         }  else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error: Add");
            alert.setHeaderText("Can't Add Product");
            if (prod.getPrice() >= pricetotal ) {
                alert.setContentText("Product must contain at least 1 part");
            } else {
                alert.setContentText("Price cannot be less than cost of parts");
            }
            alert.showAndWait();
         }
         
    }
    
    public void handleDelete() {
        //int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        //personTable.getItems().remove(selectedIndex);
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Modify Product");
        alert.setContentText("Are you sure you want to delete this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
                pickedPartID = partTable2.getSelectionModel().getSelectedIndex();
                ObservableList<Part> displayPart = FXCollections.observableArrayList();
                displayPart = partTable2.getItems();
                displayPart.remove(pickedPartID);
                partTable2.setItems(displayPart); 
        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }
    
    public void handleCancel(ActionEvent event) throws IOException  {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Cancel Add Product");
        alert.setContentText("Are you sure you want to lose all your data?");
    
        Optional<ButtonType> result = alert.showAndWait();
     
        if (result.get() == ButtonType.OK){
            Parent main_page = FXMLLoader.load( getClass().getResource("MainScreen.fxml"));

             Scene main_page_scene = new Scene(main_page);
             Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             app_stage.setScene(main_page_scene);
             app_stage.show();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        modifyProduct = Inventory.lookupProduct(selectedProduct());
        productID.setText(Integer.toString(modifyProduct.getProductID()));
        name.setText(modifyProduct.getName());
        inv.setText(Integer.toString(modifyProduct.getInStock()));
        price.setText(Double.toString(modifyProduct.getPrice()));
        min.setText(Integer.toString(modifyProduct.getMin()));
        max.setText(Integer.toString(modifyProduct.getMax()));
        
       colPartID.setCellValueFactory(cellData -> cellData.getValue().getPartIDProperty().asObject());
       colPartName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
       colPartInv.setCellValueFactory(cellData -> cellData.getValue().getInStockProperty().asObject());
       colPartPrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
       partTable.setItems(Inventory.getAllParts());   

       colPartID2.setCellValueFactory(cellData -> cellData.getValue().getPartIDProperty().asObject());
       colPartName2.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
       colPartInv2.setCellValueFactory(cellData -> cellData.getValue().getInStockProperty().asObject());
       colPartPrice2.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
       partTable2.setItems(modifyProduct.getAssociatedParts());  
        
    }    
    
}

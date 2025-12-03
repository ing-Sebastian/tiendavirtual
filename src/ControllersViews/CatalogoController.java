/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ControllersViews;

import Controllers.ControladorCatalogo;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class CatalogoController implements Initializable {
    
    private ControladorCatalogo controlador = new ControladorCatalogo();
    
    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        int columnas = 0;
        int filas = 0;
        try{
            for(int i=0; i<controlador.tamaño(); i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Views/ItemCatalogo.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
            
                ItemCatalogoController itemCatalogoController = fxmlLoader.getController();
                itemCatalogoController.setData(controlador.buscarPorId(i));
                
                if(columnas == 3){
                    columnas = 0;
                    filas++;
                }
                
                grid.add(anchorPane,columnas++, filas);
                GridPane.setMargin(anchorPane, new Insets(20));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }    
    
}

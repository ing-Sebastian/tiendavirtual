/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ControllersViews;

import Models.Productos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class ItemCatalogoController implements Initializable {
    
    @FXML
    private Button agregarBoton;

    @FXML
    private Label descLabel;

    @FXML
    private Pane favBoton;

    @FXML
    private ImageView imageView;

    @FXML
    private Label nombreLabel;

    @FXML
    private Label precioLabel;
    
    private Productos producto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setData(Productos producto){
        this.producto = producto;
        nombreLabel.setText(producto.nombre);
        descLabel.setText(producto.descripcion);
        precioLabel.setText("$"+String.valueOf(producto.precio));
        Image image = new Image(getClass().getResourceAsStream(producto.nombreImagen));
        imageView.setImage(image);
    }
    
}

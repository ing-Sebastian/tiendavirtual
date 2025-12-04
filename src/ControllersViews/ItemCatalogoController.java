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

    public void setData(Productos producto) {
        this.producto = producto;
        nombreLabel.setText(producto.nombre);
        descLabel.setText(producto.descripcion);
        precioLabel.setText("$" + String.valueOf(producto.precio));

        // --- INICIO CORRECCIÓN ---
        // 1. Crear la ruta completa y correcta. Asumimos que las imágenes están en la carpeta 'images'.
        String rutaCompleta = "/images/" + producto.nombreImagen;

        java.io.InputStream streamImagen = getClass().getResourceAsStream(rutaCompleta);

        if (streamImagen != null) {
            // 2. Cargar imagen si la ruta es válida
            Image image = new Image(streamImagen);
            imageView.setImage(image);
        } else {
            // 3. Manejar error si no se encuentra la imagen
            System.err.println("❌ ERROR: No se encontró la imagen en la ruta: " + rutaCompleta);
            // Si no se encuentra, borramos la imagen anterior para evitar mostrar basura.
            imageView.setImage(null);
        }
        // --- FIN CORRECCIÓN ---
    }

}

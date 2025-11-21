/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ControllersViews;

import javafx.geometry.Rectangle2D;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import Controllers.ControllerUsuario;
import Models.Usuario;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author Sebastian Perez
 */
public class LoginController implements Initializable {
    
    private ControllerUsuario controlador = new ControllerUsuario();
    
    @FXML
    private ImageView imageView;
    
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegistrar;

    @FXML
    private PasswordField jtfContraseña;

    @FXML
    private TextField jtfEmail;
    
    ///////////////////////////////////////////
    @FXML
    private void iniciarSesion(ActionEvent event) {

        String correo = jtfEmail.getText();
        String contraseña = jtfContraseña.getText();

        Usuario u = controlador.login(correo, contraseña);

        Alert alerta;

        if (u != null) {
            alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText(null);
            alerta.setContentText("Bienvenido " + u.nombre);
            alerta.showAndWait();
        } else {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Correo o contraseña incorrectos");
            alerta.showAndWait();
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Rectangle clip = new Rectangle(
            0, 0, 445, 460
        );
        clip.setArcWidth(80);
        clip.setArcHeight(80);
        imageView.setClip(clip);
        
        // snapshot the rounded image.
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageView.snapshot(parameters, null);
        
        // store the rounded image in the imageView.
        imageView.setImage(image);
    }    
 
}

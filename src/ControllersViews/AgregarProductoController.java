/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ControllersViews;

import Controllers.ControladorCatalogo;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class AgregarProductoController implements Initializable {

    private ControladorCatalogo controlador = new ControladorCatalogo();

    @FXML
    private TextArea descProd;

    @FXML
    private TextField nombreProd;

    @FXML
    private TextField precioProd;
    
    @FXML
    private TextField idProd;
    
    @FXML
    private Label lblNombreArchivo;

    private File archivoImagenSeleccionado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleSeleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen del Producto");

        // Filtrar solo tipos de imágenes
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) nombreProd.getScene().getWindow(); // Obtener la ventana actual
        archivoImagenSeleccionado = fileChooser.showOpenDialog(stage);

        // Lógica para actualizar el texto:
        if (archivoImagenSeleccionado != null) {
            // Muestra solo el nombre del archivo
            lblNombreArchivo.setText(archivoImagenSeleccionado.getName());
        } else {
            // Si el usuario cancela, restaurar el texto predeterminado
            lblNombreArchivo.setText("Ningún archivo seleccionado");
        }
    }

    @FXML
    private void handleGuardarProducto(ActionEvent event) {

        // 1. VALIDACIÓN BÁSICA
        if (nombreProd.getText().trim().isEmpty() || precioProd.getText().trim().isEmpty() || descProd.getText().trim().isEmpty() || archivoImagenSeleccionado == null) {
            Alert alerta;
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Error: Debes completar todos los campos y seleccionar una imagen.");
            alerta.showAndWait();
            return;
        }

        // 2. DEFINICIÓN DE RUTAS
        // Obtener el directorio base de la aplicación (donde está src)
        String directorioActual = System.getProperty("user.dir");

        // Crear la ruta completa de la carpeta de destino: [proyecto]/src/Images/
        Path rutaDestinoDir = Paths.get(directorioActual, "src", "Images");

        // Asegurarse de que el directorio exista. Si no existe, lo crea.
        if (!Files.exists(rutaDestinoDir)) {
            try {
                Files.createDirectories(rutaDestinoDir);
            } catch (IOException e) {
                System.err.println("Error al crear el directorio de imágenes: " + e.getMessage());
                return;
            }
        }

        // 3. COPIAR Y RENOMBRAR LA IMAGEN
        String nombreArchivoNuevo = "";
        try {
            // Generar un nombre único para el archivo para evitar colisiones
            String extension = getFileExtension(archivoImagenSeleccionado.getName());
            nombreArchivoNuevo = System.currentTimeMillis() + "_" + nombreProd.getText().replaceAll("\\s+", "_") + "." + extension;

            Path rutaDestinoFinal = rutaDestinoDir.resolve(nombreArchivoNuevo);

            // Copia el archivo. REPLACE_EXISTING es opcional, pero seguro.
            Files.copy(archivoImagenSeleccionado.toPath(), rutaDestinoFinal, StandardCopyOption.REPLACE_EXISTING);

            Alert alerta;
            alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText(null);
            alerta.setContentText("Imagen guardada en: " + rutaDestinoFinal.toString());
            alerta.showAndWait();

        } catch (IOException e) {
            Alert alerta;
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Error al copiar el archivo de imagen: " + e.getMessage());
            alerta.showAndWait();
            return; // Salir si hay un error al guardar el archivo
        }

        // 4. CREAR Y GUARDAR EL PRODUCTO
        try {
            // Agregar el nuevo producto a la lista de nodos/productos           
            if(controlador.registrarProducto(idProd, nombreProd, precioProd, descProd, nombreArchivoNuevo)){
                Alert alerta;
                alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setHeaderText(null);
                alerta.setContentText("Producto '" + nombreProd.getText().trim() + "' agregado con éxito.");
                alerta.showAndWait();
            }

            // Opcional: Cerrar la ventana del formulario
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        } catch (NumberFormatException e) {
            System.err.println("Error: El precio no es un número válido.");
        }
    }

// Método auxiliar para obtener la extensión del archivo
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}

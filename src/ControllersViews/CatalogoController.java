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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import Models.Nodo;
import Models.Productos;

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
        int columnas = 0;
        int filas = 0;

        // 1. Verificar si hay productos antes de intentar cargar nada
        if (controlador.listaVacia()) {
            // Opcional: poner un Label en el grid que diga "No hay productos"
            return;
        }

        try {
            Nodo<Productos> actual = controlador.inicio;

            do {
                FXMLLoader fxmlLoader = new FXMLLoader();

                // 2. IMPORTANTE: Verificar que la ruta del FXML es correcta
                URL fxmlUrl = getClass().getResource("/Views/ItemCatalogo.fxml");

                if (fxmlUrl == null) {
                    // Si entra aquí, el nombre de la carpeta o el archivo está mal
                    System.out.println("ERROR CRÍTICO: No se encuentra /Views/ItemCatalogo.fxml");
                    System.out.println("Verifica mayúsculas/minúsculas en la carpeta 'Views'.");
                    break; // Detenemos el ciclo para no lanzar mil errores
                }

                fxmlLoader.setLocation(fxmlUrl);
                AnchorPane anchorPane = fxmlLoader.load();

                ItemCatalogoController itemCatalogoController = fxmlLoader.getController();

                // Pasamos el objeto Producto real
                itemCatalogoController.setData(actual.dato);

                if (columnas == 3) {
                    columnas = 0;
                    filas++;
                }

                grid.add(anchorPane, columnas++, filas);
                grid.setVgap(30);
                GridPane.setMargin(anchorPane, new Insets(20));

                actual = actual.sig;

            } while (actual != controlador.inicio);

        } catch (IOException e) {
            // 3. Imprimir el error completo en la consola de NetBeans
            System.err.println("Error al cargar ItemCatalogo.fxml:");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAbrirNuevaVentana(ActionEvent event) {
        try {
            // 1. Cargar el FXML de la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/AgregarProducto.fxml"));
            Parent root = loader.load();

            // 2. Crear un nuevo Stage (la ventana)
            Stage stage = new Stage();
            stage.setTitle("Agregar Producto Ventana");
            stage.setScene(new Scene(root));

            // 3. Mostrar la nueva ventana
            stage.show();

            // Opcional: Para bloquear la ventana principal hasta que se cierre esta:
            // stage.initModality(Modality.APPLICATION_MODAL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

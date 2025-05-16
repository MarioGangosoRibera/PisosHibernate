package com.example.pisos.Controller;

import com.example.pisos.DAO.PisoDAO;
import com.example.pisos.DAO.ZonaDAO;
import com.example.pisos.Model.Piso;
import com.example.pisos.Model.Zona;
import com.example.pisos.utilities.StaticCode;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class PisoController implements Initializable {

    @FXML
    private ComboBox<String> cbIdZona;

    @FXML
    private TableView<Piso> tableView;

    @FXML
    private TableColumn<Piso, String> tcDireccion;

    @FXML
    private TableColumn<Piso, Integer> tcIdPiso;

    @FXML
    private TableColumn<Piso, String> tcIdZona;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtIdPiso;

    PisoDAO pisoDAO = new PisoDAO();
    ZonaDAO zonaDAO = new ZonaDAO();

    @FXML
    void onClickAlta(ActionEvent event) {
        try {
            int idPiso = Integer.parseInt(txtIdPiso.getText());
            String direccion = txtDireccion.getText();
            String nombreZona = cbIdZona.getValue();

            Zona zona = zonaDAO.obtenerZonaPorNombre(nombreZona);
            if (zona == null) {
                StaticCode.Alerts("ERROR", "Zona no válida", "¡ERROR!",
                        "La zona seleccionada no existe en la base de datos.");
                return;
            }

            Piso nuevoPiso = new Piso();
            nuevoPiso.setIdPiso(idPiso);
            nuevoPiso.setDireccion(direccion);
            nuevoPiso.setZona(zona);

            if (pisoDAO.insertarPiso(nuevoPiso)) {
                StaticCode.Alerts("INFORMATION", "Alta de piso", "Piso agregado",
                        "Se ha añadido correctamente.");
                refreshTable();
            }
        } catch (NumberFormatException e) {
            StaticCode.Alerts("ERROR", "Formato inválido", "¡ERROR!",
                    "ID del piso debe ser un número.");
        } catch (Exception e) {
            StaticCode.Alerts("ERROR", "Alta fallida", "¡ERROR!",
                    "No se pudo dar de alta el piso: " + e.getMessage());
        }
    }


    @FXML
    void onClickBorrar(ActionEvent event) {
        Piso seleccionada = tableView.getSelectionModel().getSelectedItem();
        if (seleccionada!=null){
            pisoDAO.eliminarPiso(seleccionada);
            StaticCode.Alerts("INFORMATION", "Eliminar piso", "INFORMATION",
                    "Se ha eliminado los datos del piso correctamente.");
            refreshTable();
        }else {
            StaticCode.Alerts("ERROR", "Coche vacio", "¡ERROR!",
                    "Por favor, seleccione un piso para eliminar.");
        }
    }

    @FXML
    void onClickModificar(ActionEvent event) {
        Piso seleccionada = tableView.getSelectionModel().getSelectedItem(); // Obtener piso seleccionado

        // Verificar si hay un piso seleccionado
        if (seleccionada != null) {
            // Verificar si hay campos vacíos
            if (StaticCode.camposVacios(cbIdZona, txtIdPiso, txtDireccion)) {

                String nuevaDireccion = txtDireccion.getText();
                String nuevaZonaNombre = cbIdZona.getValue();

                // Verificar si los campos han cambiado respecto al objeto original
                boolean direccionIgual = nuevaDireccion.equals(seleccionada.getDireccion());
                boolean zonaIgual = nuevaZonaNombre.equals(seleccionada.getZona().getNombre());

                if (direccionIgual && zonaIgual) {
                    StaticCode.Alerts("INFORMATION", "Sin cambios", "Información",
                            "Los campos no han cambiado. No se realizó ninguna modificación.");
                    return;
                }

                // Crear nuevo objeto Piso modificado
                Piso pisoModificar = new Piso();
                pisoModificar.setIdPiso(seleccionada.getIdPiso());
                pisoModificar.setDireccion(nuevaDireccion);

                Zona zona = zonaDAO.obtenerZonaPorNombre(nuevaZonaNombre);
                if (zona == null){
                    StaticCode.Alerts("ERROR", "Zona no válida", "¡ERROR!",
                            "La zona seleccionada no existe en la base de datos.");
                    return;
                }
                pisoModificar.setZona(zona);

                // Modificar el piso en la base de datos
                if (pisoDAO.modificarPiso(pisoModificar)) {
                    StaticCode.Alerts("INFORMATION", "Modificar Piso", "Información",
                            "Se han modificado los datos del piso correctamente.");
                    onClickLimpiar(null); // Limpiar campos
                    refreshTable();       // Refrescar tabla
                } else {
                    StaticCode.Alerts("ERROR", "Error al modificar", "¡ERROR!",
                            "No se pudo modificar el piso.");
                }

            } else {
                StaticCode.Alerts("ERROR", "Campo vacío", "¡ERROR!",
                        "Por favor, rellene todos los datos del formulario.");
            }
        } else {
            StaticCode.Alerts("ERROR", "Piso no seleccionado", "¡ERROR!",
                    "Por favor, seleccione un piso para modificar.");
        }
    }



    @FXML
    void onClickLimpiar(ActionEvent event) {
        txtIdPiso.clear();
        txtDireccion.clear();
        cbIdZona.setValue(null);
    }

    @FXML
    void onClickedTable(MouseEvent event) {
        Piso seleccionada = tableView.getSelectionModel().getSelectedItem();
        if(seleccionada!=null){
            txtDireccion.setText(seleccionada.getDireccion());
            txtIdPiso.setText(String.valueOf(seleccionada.getIdPiso()));
            Zona zona = seleccionada.getZona();
            if(zona != null){
                cbIdZona.setValue(zona.getNombre());
            }
        }
    }

    @FXML
    void onClickZonas(ActionEvent event) {
        String nombreZonaSeleccionada = cbIdZona.getValue();
        if(nombreZonaSeleccionada == null || nombreZonaSeleccionada.isEmpty()){
            StaticCode.Alerts("ERROR", "Zona no seleccionada","ERROR",
                    "Seleccionado una zona antes de continuar");
            return;
        }
        Button sourceButton = (Button) event.getSource();
        StaticCode.cambiarVistaZonas("zonas.fxml", sourceButton, nombreZonaSeleccionada, "Detalle de zona");
    }

    private void refreshTable() {
        tableView.setItems(pisoDAO.listarPisos());
    }

    private void cargarZonasEnCombobox(){
        ObservableList<Zona> zonas = zonaDAO.listarZonas();
        for (Zona zona : zonas){
            cbIdZona.getItems().add(zona.getNombre());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cargarZonasEnCombobox();

        tcIdPiso.setCellValueFactory(new PropertyValueFactory<>("idPiso"));
        tcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tcIdZona.setCellValueFactory(cellData -> {
            Zona zona = cellData.getValue().getZona();
            String nombreZona = zona != null ? zona.getNombre() : "Sin zona";
            return new SimpleStringProperty(nombreZona);
        });

        refreshTable();

    }
}

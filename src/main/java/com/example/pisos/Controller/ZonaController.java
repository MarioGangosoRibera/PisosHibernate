package com.example.pisos.Controller;

import com.example.pisos.DAO.ZonaDAO;
import com.example.pisos.Model.Piso;
import com.example.pisos.Model.Zona;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ZonaController {

    @FXML
    private TextField txtIdZona;

    @FXML
    private TextField txtNombre;

    private ZonaDAO zonaDAO = new ZonaDAO();

    public void cargarZonaPorNombre(String nombreZona) {
        Zona zona = zonaDAO.obtenerZonaPorNombre(nombreZona);
        if (zona != null) {
            txtIdZona.setText(String.valueOf(zona.getIdZona()));
            txtNombre.setText(zona.getNombre());
        } else {
            txtIdZona.setText("Zona no encontrada");
            txtNombre.setText("");
        }
    }
}

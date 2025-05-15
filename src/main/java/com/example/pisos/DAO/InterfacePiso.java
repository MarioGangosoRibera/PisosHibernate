package com.example.pisos.DAO;

import com.example.pisos.Model.Piso;
import javafx.collections.ObservableList;

public interface InterfacePiso {
    boolean insertarPiso(Piso piso);

    boolean modificarPiso(Piso piso);

    boolean eliminarPiso(Piso piso);

    ObservableList<Piso> listarPisos();
}

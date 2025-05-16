package com.example.pisos.DAO;

import com.example.pisos.Model.Zona;
import com.example.pisos.utilities.HibernateUtil;
import com.example.pisos.utilities.StaticCode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import java.util.List;

public class ZonaDAO {
    private final Session session;

    public ZonaDAO() {
        this.session = HibernateUtil.getSession();
    }

    public ObservableList<Zona> listarZonas(){
        ObservableList<Zona> listaZonas = FXCollections.observableArrayList();
        try {
            List<Zona> zonas = session.createQuery("from Zona", Zona.class).list();
            listaZonas.addAll(zonas);
        }catch (Exception e){
            StaticCode.Alerts("ERROR", "Error al listar zonas", "ERROR",
                    "No se pudieron obtener las zonas" + e.getMessage());
        }
        return listaZonas;
    }
    public Zona obtenerZonaPorNombre(String nombre) {
        try {
            return session.createQuery("from Zona where nombre = :nombre", Zona.class)
                    .setParameter("nombre", nombre)
                    .uniqueResult();
        } catch (Exception e) {
            StaticCode.Alerts("ERROR", "Error al buscar zona", "¡ERROR!",
                    "No se pudo encontrar la zona: " + e.getMessage());
            return null;
        }
    }
}

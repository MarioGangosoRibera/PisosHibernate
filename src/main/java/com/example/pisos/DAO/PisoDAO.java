package com.example.pisos.DAO;

import com.example.pisos.Model.Piso;
import com.example.pisos.utilities.HibernateUtil;
import com.example.pisos.utilities.StaticCode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import java.util.List;

public class PisoDAO implements InterfacePiso{

    public Session session;

    public PisoDAO(){
        session = HibernateUtil.getSession(); // CUANDO SE CREA SE ESTABLECE LA SESSION
    }

    public boolean insertarPiso(Piso piso){
        try {
            session.beginTransaction();
            session.save(piso);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            if(session.getTransaction()!=null){
                session.getTransaction().rollback();
            }
            StaticCode.Alerts("Error", "Error al insertar", "ERROR",
                    "Ha habido un error al insertar el piso" + e);
            return false;
        }
    }

    public boolean modificarPiso(Piso piso){
        try {
            session.beginTransaction();
            Piso existente = session.get(Piso.class, piso.getIdPiso());

            existente.setDireccion(piso.getDireccion());

            session.update(existente);
            session.getTransaction().commit();
            session.clear();
            return true;
        }catch (Exception e){
            if (session.getTransaction()!=null){
                session.getTransaction().rollback();
            }
            StaticCode.Alerts("ERROR", "Error al modificar", "¡ERROR!",
                    "Ha ocurrido un error al modificar el piso: " + e);
            return false;
        }
    }

    public boolean eliminarPiso(Piso piso){
        try {
            session.beginTransaction();
            session.delete(piso);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            if(session.getTransaction()!=null){
                session.getTransaction().rollback();
            }
            StaticCode.Alerts("ERROR", "Error al eliminar", "¡ERROR!",
                    "Ha ocurrido un error al eliminar el piso: " + e);
            return false;
        }
    }

    public ObservableList<Piso> listarPisos(){
        ObservableList<Piso> observableList = null;
        try {
            List listaPisosDB = session.createQuery("from Piso").list();
            observableList = FXCollections.observableArrayList(listaPisosDB);
        }catch (Exception e){
            StaticCode.Alerts("ERROR", "Error al listar", "¡ERROR!",
                    "Ha ocurrido un error al listar los pisos: " + e);
        }
        return observableList;
    }
}

package com.example.pisos.DAO;

import com.example.pisos.Model.Zona;
import com.example.pisos.utilities.HibernateUtil;
import org.hibernate.Session;

public class ZonaDAO {
    private final Session session;

    public ZonaDAO() {
        this.session = HibernateUtil.getSession();
    }

    public Zona obtenerZonaPorNombre(String nombre) {
        try {
            return (Zona) session.createQuery("FROM Zona WHERE nombre = :nombre")
                    .setParameter("nombre", nombre)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

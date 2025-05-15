package com.example.pisos.utilities;

import com.example.pisos.Model.Piso;
import com.example.pisos.Model.Zona;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    static SessionFactory factory = null;

    static {
        Configuration cfg = new Configuration();
        cfg.configure("/Configuration/hibernate.cfg.xml");
        cfg.addAnnotatedClass(Piso.class);
        cfg.addAnnotatedClass(Zona.class);
        factory = cfg.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static Session getSession() {
        return factory.openSession();
    }
} // HibernateUtil NOS DA LAS CONEXIONES CON LA BASE DE DATOS
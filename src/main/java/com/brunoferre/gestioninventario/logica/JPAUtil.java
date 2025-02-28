package com.brunoferre.gestioninventario.logica;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory mysqlFactory = Persistence.createEntityManagerFactory("PersistenceMYSQL");
    private static final EntityManagerFactory postgresFactory = Persistence.createEntityManagerFactory("PersistencePOST");

    public static EntityManager getMysqlEntityManager() {
        return mysqlFactory.createEntityManager();
    }

    public static EntityManager getPostgresEntityManager() {
        return postgresFactory.createEntityManager();
    }
}

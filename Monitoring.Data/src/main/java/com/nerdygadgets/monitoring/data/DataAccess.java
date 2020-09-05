package com.nerdygadgets.monitoring.data;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DataAccess {

    public static class EntityManager {
        public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-monitoring");
    }
}

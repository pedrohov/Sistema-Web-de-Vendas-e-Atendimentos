package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FabricaEntity {
    
    private static EntityManagerFactory emf = 
            Persistence.createEntityManagerFactory("projeto01_PU");
    
    public static EntityManager getEntityManager(){
           return emf.createEntityManager();
    }
    
}

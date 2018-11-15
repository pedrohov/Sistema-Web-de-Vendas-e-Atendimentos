/*
 * Desenvolvimento Java Avancado
 * 1o sem/2018
 * 0002346 - Pedro Veloso
 */

package dao;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.Marca;

public class MarcaDAO extends HibernateGenericDAO<Marca, Integer> {
    
    public MarcaDAO() {
        
    }
    
    public List<Marca> buscarPorNome(String nome){
        String sql = "Select c from Marca c " +
                    "where c.nome like ?1";
       
        EntityManager em = getEntityManager();
        return em.createQuery(sql, Marca.class).setParameter("1", nome).getResultList();
    }
    
}

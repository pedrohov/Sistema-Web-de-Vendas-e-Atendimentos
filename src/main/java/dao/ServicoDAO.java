/*
 * Desenvolvimento Java Avancado
 * 1o sem/2018
 * 0002346 - Pedro Veloso
 */

package dao;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.Servico;

public class ServicoDAO extends HibernateGenericDAO<Servico, Integer> {

    public ServicoDAO() {
        
    }
    
    public List<Servico> buscarPorNome(String nome){
        String sql = "Select c from Servico c " +
                    "where c.nome like ?1";
       
        EntityManager em = getEntityManager();
        return em.createQuery(sql, Servico.class).setParameter("1", nome).getResultList();
    }
    
}

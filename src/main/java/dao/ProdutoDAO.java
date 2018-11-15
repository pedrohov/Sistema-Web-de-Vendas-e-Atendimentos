/*
 * Desenvolvimento Java Avancado
 * 1o sem/2018
 * 0002346 - Pedro Veloso
 */

package dao;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.Produto;

public class ProdutoDAO extends HibernateGenericDAO<Produto, Integer> {
    
    public ProdutoDAO() {
        
    }
    
    public List<Produto> getProdutoPeloNome(String nome){
        String sql = "Select c from Produto c " +
                    "where c.nome like ?1";
       
        EntityManager em = getEntityManager();
        return em.createQuery(sql, Produto.class).setParameter("1", nome).getResultList();
    }
    
}

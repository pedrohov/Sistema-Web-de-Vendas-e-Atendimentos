package dao;

import java.util.List;

import javax.persistence.EntityManager;

import modelo.Venda;

public class VendaDAO extends HibernateGenericDAO<Venda, Integer> {
    
    public VendaDAO() {
        
    }
    
    public List<Venda> maiorLucro(Integer max) {
    	String qry = "SELECT v FROM Venda v ORDER BY v.total DESC"; 
    	EntityManager em = getEntityManager();
        return em.createQuery(qry).setMaxResults(max).getResultList();  
    }
    
}

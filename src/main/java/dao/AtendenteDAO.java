package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import modelo.Atendente;
import modelo.Usuario;

public class AtendenteDAO extends HibernateGenericDAO<Atendente, Integer> {

    public AtendenteDAO() {
        
    }
    
    public List<Atendente> buscarPorNome(String nome){
        String sql = "Select c from Atendente c " +
                    "where c.nome like ?1";
       
        EntityManager em = getEntityManager();
        return em.createQuery(sql, Atendente.class).setParameter("1", nome).getResultList();
    }
    
    public boolean isLoginValido(String login){
        String sql = "SELECT u FROM Usuario u WHERE u.login = :Login";
       
        EntityManager em = getEntityManager();

        try {
    		em.createQuery(sql, Usuario.class).setParameter("Login", login).getSingleResult();
        	return false;
        } catch(NoResultException e) {
        	return true;
        } catch(NonUniqueResultException e) {
        	return false;
        }
    }
    
}

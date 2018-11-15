package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import modelo.Cliente;
import modelo.Usuario;

public class ClienteDAO extends HibernateGenericDAO<Cliente, Integer> {
    
    public ClienteDAO() {
    }
    
    public List<Cliente> buscarPorNome(String nome){
        String sql = "Select c from Cliente c " +
                    "where c.nome like ?1";
       
        EntityManager em = getEntityManager();
        return em.createQuery(sql, Cliente.class).setParameter("1", nome).getResultList();
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

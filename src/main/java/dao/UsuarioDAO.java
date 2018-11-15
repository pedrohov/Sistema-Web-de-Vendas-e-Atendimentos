package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import modelo.Usuario;

public class UsuarioDAO extends HibernateGenericDAO<Usuario, Integer> {
    
    public UsuarioDAO() {
    }
    
    public List<Usuario> buscarPorNome(String nome){
        String sql = "Select c from Usuario c " +
                    "where c.nome like ?1";
       
        EntityManager em = getEntityManager();
        return em.createQuery(sql, Usuario.class).setParameter("1", nome).getResultList();
    }
    
    public Usuario buscarPorLogin(String login) {
        String sql = "Select c from Usuario c " +
                     "where c.login = :login";
        EntityManager em = getEntityManager();
        Usuario result;
        try {
        	result = em.createQuery(sql, Usuario.class).setParameter("login", login).getSingleResult();
        } catch(NoResultException e) {
        	return null;
        }
        
        return result;
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

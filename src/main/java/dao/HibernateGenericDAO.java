package dao;

import util.FabricaEntity;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;

public class HibernateGenericDAO<T, ID extends Serializable> implements GenericDAO<T, ID>{

    private EntityManager em;
    private Class<T> classeEntidade;

    public HibernateGenericDAO() {
        em = FabricaEntity.getEntityManager();

        this.classeEntidade = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];                
    }  
    
    protected EntityManager getEntityManager(){
        return em;
    }
    
    @Override
    public T buscarPorCodigo(ID id) {
        return em.find(classeEntidade, id);
    }

    @Override
    public void salvar(T entidade) {
        em.getTransaction().begin();
        em.merge(entidade);
        em.getTransaction().commit();
    }

    @Override
    public boolean remover(ID id) {
    	
    	//T aux = em.find(classeEntidade, entidade.getCodigo());
    	
        T aux = em.find(classeEntidade, id);
        
        if (aux != null){
            em.getTransaction().begin();
            em.remove(aux);
            em.getTransaction().commit();
            return true;            
        }
        else
            return false;
    }

    @Override
    public List<T> buscarTodos() {
        
        String qry = "from "+classeEntidade.getName()+" e ORDER BY e.codigo DESC";        
        return em.createQuery(qry).getResultList();      
    }
    
}
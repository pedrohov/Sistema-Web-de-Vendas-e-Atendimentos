package dao;

import java.util.List;

import javax.persistence.EntityManager;

import modelo.Atendente;
import modelo.Atendimento;

public class AtendimentoDAO extends HibernateGenericDAO<Atendimento, Integer> {
    
    public AtendimentoDAO() {
        
    }
    
    public List<Atendimento> buscarAtendimentosPorAtendente(Atendente entidade) {
    	String sql = "SELECT a FROM Atendimento a WHERE a.atendente.codigo = :Codigo ORDER BY a.codigo DESC";
        
        EntityManager em = getEntityManager();
        List<Atendimento> atendimentos = em.createQuery(sql, Atendimento.class)
        								 .setParameter("Codigo", entidade.getCodigo())
        								 .getResultList();
        return atendimentos;
    }
    
}

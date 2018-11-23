package service;

import dao.AtendimentoDAO;

import java.util.List;

import org.joda.time.DateTime;

import modelo.Atendimento;
import modelo.Cliente;
import util.NegocioException;

public class AtendimentoService {
    
    private AtendimentoDAO aDAO;
    
    public AtendimentoService() {
        aDAO = new AtendimentoDAO();
    }
    
    public Atendimento buscarPorCodigo(Integer id) throws NegocioException {
    	if(id == null || id <= 0)
        	throw new NegocioException("Registro inválido.");  
        return aDAO.buscarPorCodigo(id);
    }
    
    public void salvar(Atendimento entidade) throws NegocioException {
    	if(entidade == null)
    		throw new NegocioException("Registro inválido.");
    	else if(entidade.getCliente() == null || entidade.getCliente().getNome() == null
    			|| entidade.getCliente().getNome().trim().equals("") || (entidade.getCliente().getNome().length() < 4))
    		throw new NegocioException("Cliente inválido.");
    	else if(entidade.getAtendente() == null || entidade.getAtendente().getNome() == null
    	    	|| entidade.getAtendente().getNome().trim().equals("") || (entidade.getAtendente().getNome().length() < 4))
    		throw new NegocioException("Atendente inválido.");
    	else if(entidade.getServico() == null || entidade.getServico().getNome() == null
    	    	|| entidade.getServico().getNome().trim().equals("") || (entidade.getServico().getNome().length() < 4))
    		throw new NegocioException("Serviço inválido.");
    	else if(entidade.getData() == null)
    		throw new NegocioException("Data inválida.");
    	else if(!isDataAtendimentoValida(entidade))
    		throw new NegocioException("Este atendente já possui um atendimento marcado para este horário.");
    	
        aDAO.salvar(entidade);
    }
    
    public boolean remover(Atendimento entidade) throws NegocioException {
    	if((entidade == null) || (entidade.getCodigo() == null) || (entidade.getCodigo() < 0))
    		throw new NegocioException("Registro inválido.");
    	
        return aDAO.remover(entidade.getCodigo());
    }
    
    public List<Atendimento> buscarTodos() {
        return aDAO.buscarTodos();
    }
    
    public List<Atendimento> buscarPorCliente(Cliente entidade) throws NegocioException {
    	if((entidade == null) || (entidade.getCodigo() == null) || (entidade.getCodigo() < 0))
    		throw new NegocioException("Registro inválido.");
    	
    	return aDAO.buscarPorCliente(entidade);
    }
    
    public boolean isDataAtendimentoValida(Atendimento at) {
    	
    	List<Atendimento> atendimentos = aDAO.buscarPorAtendente(at.getAtendente());
    	DateTime inicioNovo = new DateTime(at.getData());
    	DateTime fimNovo = inicioNovo.plusMinutes(at.getServico().getDuracao());
    	
    	for(int i = 0; i < atendimentos.size(); i++) {
    		DateTime inicioMarcado = new DateTime(atendimentos.get(i).getData());
    		DateTime fimMarcado = inicioMarcado.plusMinutes(atendimentos.get(i).getServico().getDuracao());
    		
    		// Se o atendente for o mesmo:
    		if(at.getAtendente().equals(atendimentos.get(i).getAtendente())) {

    			// E se os horarios interceptam:
	    		if((inicioNovo.isBefore(fimMarcado) && fimNovo.isBefore(fimMarcado) && fimNovo.isAfter(inicioMarcado))
	    		||(inicioNovo.isBefore(inicioMarcado) && inicioNovo.isAfter(fimMarcado) && fimNovo.isAfter(fimMarcado)))
	    			return false;
    		}
    	}
    	
    	return true;
    }
}

package service;

import dao.ServicoDAO;
import java.util.List;
import modelo.Servico;
import util.NegocioException;

public class ServicoService {
    
    private ServicoDAO sDAO;
    
    public ServicoService(){
        sDAO = new ServicoDAO();
    }
    
    public Servico buscarPorCodigo(Integer id) throws NegocioException {
        if(id == null || id <= 0)
        	throw new NegocioException("Registro inválido.");
        return sDAO.buscarPorCodigo(id);
    }
    
    public List<Servico> buscarPorNome(String nome) throws NegocioException { 
    	if(nome.trim().equals("") || (nome.length() < 4))
    		throw new NegocioException("Registro inválido.");
    	
        return sDAO.buscarPorNome(nome);
    }
    
    public void salvar(Servico entidade) throws NegocioException {
    	if(entidade == null)
    		throw new NegocioException("Registro inválido.");
    	else if((entidade.getNome() == null) || entidade.getNome().trim().equals("")
    			|| (entidade.getNome().length() < 5))
    		throw new NegocioException("Nome inválido.");
    	else if((entidade.getDuracao() == null) || (entidade.getDuracao() <= 0))
    		throw new NegocioException("Duração deve ser um valor positivo em minutos.");
    	else if((entidade.getValor() == null) || (entidade.getValor() < 0))
    		throw new NegocioException("Valor não pode ser negativo.");
    	
        sDAO.salvar(entidade);
    }
    
    public boolean remover(Servico entidade) throws NegocioException {
    	if((entidade == null) || (entidade.getCodigo() == null) || (entidade.getCodigo() < 0))
    		throw new NegocioException("Registro inválido.");
    	
        return sDAO.remover(entidade.getCodigo());
    }
    
    public List<Servico> buscarTodos(){
        return sDAO.buscarTodos();
    }
}

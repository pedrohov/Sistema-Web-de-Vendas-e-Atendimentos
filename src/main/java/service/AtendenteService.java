package service;

import dao.AtendenteDAO;
import java.util.List;
import modelo.Atendente;
import util.NegocioException;

public class AtendenteService {
    
    private AtendenteDAO aDAO;
    
    public AtendenteService(){
        aDAO = new AtendenteDAO();
    }
    
    public Atendente buscarPorCodigo(Integer id) throws NegocioException {
    	if(id == null || id <= 0)
        	throw new NegocioException("Registro inválido.");
        return aDAO.buscarPorCodigo(id);
    }
    
    public List<Atendente> buscarPorNome(String nome) throws NegocioException { 
    	if(nome == null || nome.trim().equals("") || (nome.length() < 4))
    		throw new NegocioException("Registro inválido.");
    	
        return aDAO.buscarPorNome(nome);
    }
    
    public void salvar(Atendente entidade) throws NegocioException {
    	if(entidade == null)
    		throw new NegocioException("Registro inválido.");
    	else if(entidade.getNome() == null || entidade.getNome().trim().equals("")
    			|| (entidade.getNome().length() < 4))
    		throw new NegocioException("Nome inválido.");
    	else if(entidade.getSenha() == null || entidade.getSenha().length() < 4)
    		throw new NegocioException("Senha inválida.");
    	else if(entidade.getPermissao() == null || (entidade.getPermissao().size() == 0))
    		throw new NegocioException("Nenhuma permissão atribuída ao usuário.");
    	else if(entidade.getLogin() == null || !isLoginValido(entidade.getLogin()))
    		throw new NegocioException("Login inválido.");
    	
        aDAO.salvar(entidade);
    }
    
    public boolean remover(Atendente entidade) throws NegocioException {
    	if((entidade == null) || (entidade.getCodigo() == null) || (entidade.getCodigo() < 0))
    		throw new NegocioException("Registro inválido.");
    	
        return aDAO.remover(entidade.getCodigo());
    }
    
    public List<Atendente> buscarTodos(){
        return aDAO.buscarTodos();
    }
    
    public Boolean isLoginValido(String login) {
        if(login.trim().equals("") || login.length() < 4)
        	return false;
        
        return aDAO.isLoginValido(login);
    }
}

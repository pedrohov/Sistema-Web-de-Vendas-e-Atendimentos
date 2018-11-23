package service;

import dao.ClienteDAO;
import java.util.List;
import modelo.Cliente;
import util.NegocioException;

public class ClienteService {
    
    private ClienteDAO cDAO;
    
    public ClienteService(){
        cDAO = new ClienteDAO();
    }
    
    public Cliente buscarPorCodigo(Integer id) throws NegocioException {
    	if(id == null || id <= 0)
        	throw new NegocioException("Registro inválido.");
        return cDAO.buscarPorCodigo(id);
    }
    
    public List<Cliente> buscarPorNome(String nome) throws NegocioException {
    	if(nome == null || nome.trim().equals("") || (nome.length() < 4))
    		throw new NegocioException("Registro inválido.");
    	
        return cDAO.buscarPorNome(nome);
    }
    
    public void salvar(Cliente entidade) throws NegocioException {
    	if(entidade == null)
    		throw new NegocioException("Registro inválido.");
    	else if(entidade.getNome() == null || entidade.getNome().trim().equals("")
    			|| (entidade.getNome().length() < 4))
    		throw new NegocioException("Nome inválido.");
    	else if(entidade.getSenha() == null || entidade.getSenha().length() < 4)
    		throw new NegocioException("Senha inválida.");
    	else if(entidade.getPermissao() == null || (entidade.getPermissao().size() == 0))
    		throw new NegocioException("Nenhuma permissão atribuída ao usuário.");
    	else if(entidade.getCpf() == null || entidade.getCpf().trim().equals("")
    			/*|| Util.isCpfValido(entidade.getCpf())*/) {
    		throw new NegocioException("CPF inválido.");
    	}
    	else if(entidade.getLogin() == null /*|| !isLoginValido(entidade.getLogin())*/)
    		throw new NegocioException("Login inválido.");
    	
        cDAO.salvar(entidade);
    }
    
    public boolean remover(Cliente entidade) throws NegocioException {
    	if((entidade == null) || (entidade.getCodigo() == null) || (entidade.getCodigo() < 0))
    		throw new NegocioException("Registro inválido.");
    	
        return cDAO.remover(entidade.getCodigo());
    }
    
    public List<Cliente> buscarTodos(){
        return cDAO.buscarTodos();
    }
    
    public Boolean isLoginValido(String login) {
        if(login.trim().equals("") || login.length() < 4)
        	return false;
        
        return cDAO.isLoginValido(login);
    }
}

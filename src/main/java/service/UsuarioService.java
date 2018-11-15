package service;

import dao.UsuarioDAO;
import java.util.List;
import modelo.Usuario;
import util.NegocioException;

public class UsuarioService {
    
    private UsuarioDAO cDAO;
    
    public UsuarioService(){
        cDAO = new UsuarioDAO();
    }
    
    public Usuario buscarPorCodigo(Integer id) throws NegocioException {
    	if(id == null || id <= 0)
        	throw new NegocioException("Registro inválido.");
        return cDAO.buscarPorCodigo(id);
    }
    
    public List<Usuario> buscarPorNome(String nome) throws NegocioException {
    	if(nome == null || nome.trim().equals("") || (nome.length() < 4))
    		throw new NegocioException("Registro inválido.");
    	
        return cDAO.buscarPorNome(nome);
    }
    
    public void salvar(Usuario entidade) throws NegocioException {
    	if(entidade == null)
    		throw new NegocioException("Registro inválido.");
    	else if(entidade.getNome() == null || entidade.getNome().trim().equals("")
    			|| (entidade.getNome().length() < 4))
    		throw new NegocioException("Nome inválido.");
    	else if(entidade.getSenha() == null || entidade.getSenha().length() < 4)
    		throw new NegocioException("Senha inválida.");
    	else if(entidade.getPermissao() == null || (entidade.getPermissao().size() == 0))
    		throw new NegocioException("Nenhuma permissão atribuída ao usuário.");
    	else if(entidade.getLogin() == null /*|| !isLoginValido(entidade.getLogin())*/)
    		throw new NegocioException("Login inválido.");
    	
        cDAO.salvar(entidade);
    }
    
    public boolean remover(Usuario entidade) throws NegocioException {
    	if((entidade == null) || (entidade.getCodigo() == null) || (entidade.getCodigo() < 0))
    		throw new NegocioException("Registro inválido.");
    	
        return cDAO.remover(entidade.getCodigo());
    }
    
    public List<Usuario> buscarTodos(){
        return cDAO.buscarTodos();
    }
    
    public Boolean isLoginValido(String login) {
        if(login.trim().equals("") || login.length() < 4)
        	return false;
        
        return cDAO.isLoginValido(login);
    }
    
    public Usuario buscarPorLogin(String login) {
    	return cDAO.buscarPorLogin(login);
    }
}

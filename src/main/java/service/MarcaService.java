package service;

import dao.MarcaDAO;
import java.util.List;
import modelo.Marca;
import util.NegocioException;

public class MarcaService {
    
    private MarcaDAO mDAO;
    
    public MarcaService(){
        mDAO = new MarcaDAO();
    }
    
    public Marca buscarPorCodigo(Integer id) throws NegocioException {
    	if(id == null || id <= 0)
        	throw new NegocioException("Registro inválido.");
        return mDAO.buscarPorCodigo(id);
    }
    
    public List<Marca> buscarPorNome(String nome) throws NegocioException {
    	if(nome == null || nome.trim().equals("") || (nome.length() < 3))
    		throw new NegocioException("Registro inválido.");
    	
        return mDAO.buscarPorNome(nome);
    }
    
    public void salvar(Marca entidade) throws NegocioException {
    	if(entidade == null)
    		throw new NegocioException("Registro inválido.");
    	else if(entidade.getNome() == null || entidade.getNome().trim().equals("")
    			|| (entidade.getNome().length() < 3))
    		throw new NegocioException("Nome inválido.");
    	
        mDAO.salvar(entidade);
    }
    
    public boolean remover(Marca entidade) throws NegocioException {
    	if((entidade == null) || (entidade.getCodigo() == null) || (entidade.getCodigo() < 0))
    		throw new NegocioException("Registro inválido.");
    	
        return mDAO.remover(entidade.getCodigo());
    }
    
    public List<Marca> buscarTodos(){
        return mDAO.buscarTodos();
    }
}

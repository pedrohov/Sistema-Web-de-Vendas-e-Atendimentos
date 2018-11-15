package service;

import dao.ProdutoDAO;
import java.util.List;
import modelo.Produto;
import util.NegocioException;

public class ProdutoService {
    
    private ProdutoDAO pDAO;
    
    public ProdutoService(){
        pDAO = new ProdutoDAO();
    }
    
    public Produto buscarPorCodigo(Integer id) throws NegocioException {
    	if(id == null || id <= 0)
        	throw new NegocioException("Registro inválido.");  
    	
        return pDAO.buscarPorCodigo(id);
    }
    
    public List<Produto> getProdutoPeloNome(String nome) throws NegocioException {
    	if(nome == null || nome.trim().equals("") || (nome.length() < 4))
    		throw new NegocioException("Registro inválido.");
    	
        return pDAO.getProdutoPeloNome(nome);
    }
    
    public void salvar(Produto entidade) throws NegocioException {
    	if(entidade == null)
    		throw new NegocioException("Registro inválido.");
    	else if(entidade.getEstoque() == null)
    		throw new NegocioException("Estoque do produto inválido.");
    	else if(entidade.getNome() == null || entidade.getNome().trim().equals("") || (entidade.getNome().length() < 4))
    		throw new NegocioException("Nome inválido.");
    	else if(entidade.getPreco() == null || entidade.getPreco() < 0)
    		throw new NegocioException("Preço inválido.");
    	else if(entidade.getEstoque() == null || entidade.getEstoque() < 0)
    		throw new NegocioException("Estoque inválido.");
    		
        pDAO.salvar(entidade);
    }
    
    public boolean remover(Produto entidade) throws NegocioException {
    	if((entidade == null) || (entidade.getCodigo() == null) || (entidade.getCodigo() < 0))
    		throw new NegocioException("Registro inválido.");
    	
        return pDAO.remover(entidade.getCodigo());
    }
    
    public List<Produto> buscarTodos(){
        return pDAO.buscarTodos();
    }
}

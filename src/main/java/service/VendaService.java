package service;

import dao.VendaDAO;
import java.util.List;
import modelo.Venda;
import util.NegocioException;

public class VendaService {
    
    private VendaDAO vDAO;
    
    public VendaService(){
        vDAO = new VendaDAO();
    }
    
    public Venda buscarPorCodigo(Integer id) throws NegocioException {
    	if(id == null || id <= 0)
        	throw new NegocioException("Registro inválido.");  
        
        return vDAO.buscarPorCodigo(id);
    }
    
    public void salvar(Venda entidade) throws NegocioException {
    	if(entidade == null)
    		throw new NegocioException("Registro inválido.");
    	else if(((entidade.getCliente() == null) || (entidade.getCliente().getNome().equals(""))))
    		throw new NegocioException("Cliente inválido.");
    	else if((entidade.getVendedor() == null) || (entidade.getVendedor().getNome().equals("")))
    		throw new NegocioException("Atendente inválido.");
    	else if(entidade.getItens().size() == 0)
    		throw new NegocioException("Nenhum item para ser vendido.");
    	
        vDAO.salvar(entidade);
    }
    
    public boolean remover(Venda entidade) throws NegocioException {
    	if((entidade == null) || (entidade.getCodigo() == null) || (entidade.getCodigo() < 0))
    		throw new NegocioException("Registro inválido.");
    	
        return vDAO.remover(entidade.getCodigo());
    }
    
    public List<Venda> buscarTodos(){
        return vDAO.buscarTodos();
    }
    
    public List<Venda> maiorLucro(Integer max) {
    	return vDAO.maiorLucro(max);
    }
}

package controller;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;

import modelo.Venda;
import modelo.VendaProduto;
import modelo.Atendente;
import modelo.Cliente;
import modelo.Produto;
import service.AtendenteService;
import service.ClienteService;
import service.ProdutoService;
import service.VendaService;
import util.FacesMensagens;
import util.NegocioException;

@ManagedBean(name="venda")
@SessionScoped
public class VendaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private VendaService service = new VendaService();
	private List<Venda> vendasList;
	private Venda obj = new Venda();
	
	private ClienteService cService = new ClienteService();
	private List<Cliente> clientes;
	
	private AtendenteService aService = new AtendenteService();
	private List<Atendente> atendentes;
	
	private ProdutoService pService = new ProdutoService();
	private List<Produto> produtos;
	private Produto produto = new Produto();
	private VendaProduto vproduto = new VendaProduto();
	private List<VendaProduto> vprodutos = new LinkedList<VendaProduto>();
	private Integer quantidade;
	private float total;
	
	public VendaBean() {
		setVendasList(service.buscarTodos());
		setClientes(cService.buscarTodos());
		setAtendentes(aService.buscarTodos());
		setProdutos(pService.buscarTodos());
		quantidade = 0;
		obj.setTotal(0f);
		total = 0f;
	}	
	
	public String adicionar() {
		try {
			obj.setData(new Date());
			obj.setTotal(total);
			
			// Remove os produtos vendidos do estoque
			// e adiciona cada VendaProduto a Venda:
			for(int i = 0; i < vprodutos.size(); i++) {
				
				obj.addItem(vprodutos.get(i));
				
				for(int j = 0; j < produtos.size(); j++) {
					if(vprodutos.get(i).getProduto().equals(produtos.get(j))) {
						produtos.get(j).setEstoque(produtos.get(j).getEstoque() - vprodutos.get(i).getQuantidade());
						pService.salvar(produtos.get(j));
					}
				}
			}
			
			service.salvar(obj);
			setVendasList(service.buscarTodos());
			FacesMensagens.info("Registro salvo com sucesso.");
			limpar();
		}
		catch (NegocioException e) {
			FacesMensagens.error(e.getMessage());
		}
		
		return "";
	}
	
	public void addProduto() {
		
		if(produto == null) {
			FacesMensagens.info("Selecione um produto para adicionar.");
			return;
		} else if(quantidade <= 0) {
			FacesMensagens.info("A quantidade deve ser um inteiro positivo.");
			return;
		}
		
		boolean encontrado = false;
		for(int i = 0; i < vprodutos.size(); i++) {
			VendaProduto vp = vprodutos.get(i);
			if(vp.getProduto().equals(produto)) {
				int nquant = vp.getQuantidade() + quantidade;
				if(nquant <= vp.getProduto().getEstoque()) {
					vp.setQuantidade(nquant);
					vp.setTotal(vp.getQuantidade() * produto.getPreco());
				} else {
					FacesMensagens.info("A quantidade informada é maior que o estoque (" + vp.getProduto().getEstoque() + ").");
					return;
				}
				encontrado = true;
				break;
			}
		}
		
		if(!encontrado) {
			if(quantidade <= produto.getEstoque()) {
				vproduto.setQuantidade(quantidade);
				vproduto.setTotal(vproduto.getQuantidade() * produto.getPreco());
			} else {
				FacesMensagens.info("A quantidade informada é maior que o estoque (" + produto.getEstoque() + ").");
				return;
			}
			
			vproduto.setProduto(produto);
			vprodutos.add(vproduto);
		}
		
		vproduto = new VendaProduto();
		quantidade = 0;
		calcTotal();
	}
	
	public void delProduto() {
		
		if(produto == null) {
			FacesMensagens.info("Selecione um produto para remover.");
			return;
		} else if(quantidade <= 0) {
			FacesMensagens.info("A quantidade deve ser um inteiro positivo.");
			return;
		}
		
		boolean encontrado = false;
		for(int i = 0; i < vprodutos.size(); i++) {
			VendaProduto vp = vprodutos.get(i);
			if(vp.getProduto().equals(produto)) {
				int nquant = vp.getQuantidade() - quantidade;
				if(nquant > 0) {
					vp.setQuantidade(nquant);
					vp.setTotal(vp.getQuantidade() * produto.getPreco());
				} else {
					vprodutos.remove(i);
				}
									
				encontrado = true;
				break;
			}
		}
		
		if(!encontrado) {
			FacesMensagens.info("O produto não consta na venda.");
			return;
		}
		
		vproduto = new VendaProduto();
		quantidade = 0;
		calcTotal();
	}
	
	public void calcTotal() {
		total = 0f;
		for(int i = 0; i < vprodutos.size(); i++) {
			total += vprodutos.get(i).getTotal();
		}
	}
	
	public String novo(){
		limpar();
		return "nova_venda?faces-redirect=true";
	}
	
	public String editar(){
		if(obj == null) {
			FacesMensagens.error("Selecione um registro para editar.");
			return "";
		}
		
		vprodutos = obj.getItens();
		
		return "nova_venda?faces-redirect=true";
	}	
	
	public void excluir() {
		try {
			service.remover(obj);
			setVendasList(service.buscarTodos());
		} catch(NegocioException e) {
			FacesMensagens.error(e.getMessage());
		}
	}
	
	private void limpar() {
		obj = new Venda();
		vproduto = new VendaProduto();
		vprodutos = new LinkedList<VendaProduto>();
	}
	
	public void preRender(ComponentSystemEvent e) {
		setVendasList(service.buscarTodos());
		setClientes(cService.buscarTodos());
		setAtendentes(aService.buscarTodos());
		setProdutos(pService.buscarTodos());
	}

	public void setVendasList(List<Venda> Vendas) {
		this.vendasList = Vendas;
	}

	public Venda getObj() {
		return obj;
	}

	public void setObj(Venda obj) {
		this.obj = obj;
	}
	
	public List<Venda> getVendasList() {
		return vendasList;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Atendente> getAtendentes() {
		return atendentes;
	}

	public void setAtendentes(List<Atendente> atendentes) {
		this.atendentes = atendentes;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public VendaProduto getVproduto() {
		return vproduto;
	}

	public void setVproduto(VendaProduto vproduto) {
		this.vproduto = vproduto;
	}

	public List<VendaProduto> getVprodutos() {
		return vprodutos;
	}

	public void setVprodutos(List<VendaProduto> vprodutos) {
		this.vprodutos = vprodutos;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

}
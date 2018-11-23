package controller;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import modelo.Produto;
import service.ProdutoService;
import util.FacesMensagens;

@ManagedBean(name="entProduto")
@SessionScoped
public class EntradaProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto obj = new Produto();
	private ProdutoService service = new ProdutoService();
	private List<Produto> produtos;
	
	
	public EntradaProdutoBean() {
		setProdutos(service.buscarTodos());
	}
	
	public void altEstoque() {
		try {
			service.salvar(obj);
			FacesMensagens.info("Produtos adicionados com sucesso.");
			limpar();
		} catch (Exception e) {
			FacesMensagens.error(e.getMessage());
		}
	}
	
	private void limpar() {
		obj = null;
	}
	
	public void preRender(ComponentSystemEvent e) {
		setProdutos(service.buscarTodos());
	}

	public Produto getObj() {
		return obj;
	}

	public void setObj(Produto obj) {
		this.obj = obj;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public void changeProduto(AjaxBehaviorEvent event) {
		if(event.getComponent().getAttributes().get("value") == null)
			obj = new Produto();
		else
			System.out.println(event.getComponent().getAttributes().get("value"));
	}
	
}

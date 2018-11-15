package controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;

import modelo.Servico;
import service.ServicoService;
import util.FacesMensagens;
import util.NegocioException;

@ManagedBean(name="servico")
@SessionScoped
public class ServicoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ServicoService service = new ServicoService();
	private List<Servico> servicos;
	private Servico obj = new Servico();
	
	public ServicoBean() {
		setServicos(service.buscarTodos());
	}
	
	public String adicionar() {
		try {
			service.salvar(obj);
			setServicos(service.buscarTodos());
			FacesMensagens.info("Registro salvo com sucesso.");
			limpar();
		}
		catch (NegocioException e) {
			FacesMensagens.error(e.getMessage());
		}
		
		return "";
	}
	
	public String novo(){
		limpar();
		return "novo_servico?faces-redirect=true";
	}
	
	public String editar(){
		if((obj == null) || (obj.getNome().equals(""))) {
			FacesMensagens.error("Selecione um registro para editar.");
			return "";
		}
		
		return "novo_servico?faces-redirect=true";
	}	
	
	public void excluir() {
		try {
			service.remover(obj);
			setServicos(service.buscarTodos());
		} catch(NegocioException e) {
			FacesMensagens.error(e.getMessage());
		}
	}

	private void limpar() {
		obj = new Servico();
	}
	
	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public Servico getObj() {
		return obj;
	}
	
	public void preRender(ComponentSystemEvent e) {
		setServicos(service.buscarTodos());
	}

	public void setObj(Servico obj) {
		this.obj = obj;
	}
}
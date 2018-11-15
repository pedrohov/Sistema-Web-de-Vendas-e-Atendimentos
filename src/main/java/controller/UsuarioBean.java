package controller;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;

import modelo.Atendente;
import modelo.Sexo;
import service.AtendenteService;
import util.FacesMensagens;
import util.NegocioException;

@ManagedBean(name="usuario")
@SessionScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AtendenteService service = new AtendenteService();
	private List<Atendente> usuarios;
	private Atendente obj = new Atendente();
	private String[] permissoes;
	
	public UsuarioBean() {
		setAtendentes(service.buscarTodos());
		obj.setSexo(Sexo.MASCULINO);
	}	
	
	public String adicionar() {
		try {
			Set<String> roles = new HashSet<String>();
			for(int i = 0; i < permissoes.length; i++)
				roles.add(permissoes[i]);
			
			obj.setPermissao(roles);
			service.salvar(obj);
			setAtendentes(service.buscarTodos());
			FacesMensagens.info("Registro salvo com sucesso.");
			limpar();
		}
		catch (Exception e) {
			FacesMensagens.error(e.getMessage());
		}
		
		return "";
	}
	
	public String novo(){
		limpar();
		return "novo_usuario?faces-redirect=true";
	}
	
	public String editar(){
		if((obj == null) || (obj.getNome().equals(""))) {
			FacesMensagens.error("Selecione um registro para editar.");
			return "";
		}
		return "novo_usuario?faces-redirect=true";
	}	
	
	public void excluir() {
		try {
			service.remover(obj);
			setAtendentes(service.buscarTodos());	
		} catch (NegocioException e) {
			FacesMensagens.error(e.getMessage());
		}
	}
	
	private void limpar() {
		obj = new Atendente();
		obj.setSexo(Sexo.MASCULINO);
		permissoes = null;
	}
	
	public void preRender(ComponentSystemEvent e) {
		setAtendentes(service.buscarTodos());
	}
	
	public Sexo[] getSexos() {
        return Sexo.values();
    }
	
	public List<Atendente> getAtendentes() {
		return usuarios;
	}

	public void setAtendentes(List<Atendente> Atendentes) {
		this.usuarios = Atendentes;
	}

	public Atendente getObj() {
		return obj;
	}

	public void setObj(Atendente obj) {
		this.obj = obj;
	}
	
	public List<Atendente> getUsuarios() {
		return usuarios;
	}

	public String[] getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(String[] permissoes) {
		this.permissoes = permissoes;
	}

}
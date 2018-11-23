package controller;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import modelo.Atendente;
import modelo.Atendimento;
import modelo.Cliente;
import modelo.Servico;
import service.AtendenteService;
import service.AtendimentoService;
import service.ClienteService;
import service.ServicoService;
import util.FacesMensagens;
import util.NegocioException;

@ManagedBean(name="atendimento")
@SessionScoped
public class AtendimentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AtendimentoService service = new AtendimentoService();
	private List<Atendimento> atendimentos;
	private Atendimento obj = new Atendimento();
	
	private ClienteService cService = new ClienteService();
	private List<Cliente> clientes;
	private AtendenteService aService = new AtendenteService();
	private List<Atendente> atendentes;
	private ServicoService sService = new ServicoService();
	private List<Servico> servicos;
	
	private Cliente filtroCliente;
	private Atendente filtroAtendente;
	
	public AtendimentoBean() {
		setAtendimentos(service.buscarTodos());
		setClientes(cService.buscarTodos());
		setAtendentes(aService.buscarTodos());
		setServicos(sService.buscarTodos());
	}

	public List<Atendimento> getatendimentos() {
		return atendimentos;
	}

	public void setAtendimentos(List<Atendimento> atendimentos) {
		this.atendimentos = atendimentos;
	}

	public Atendimento getObj() {
		return obj;
	}

	public void setObj(Atendimento obj) {
		this.obj = obj;
	}
	
	public String adicionar() {
		try {
			service.salvar(obj);
			setAtendimentos(service.buscarTodos());
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
		return "novo_atendimento?faces-redirect=true";
	}
	
	public String editar(){
		return "novo_atendimento?faces-redirect=true";
	}	
	
	public void excluir() {
		try {
			service.remover(obj);
			setAtendimentos(service.buscarTodos());
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}

	private void limpar() {
		obj = new Atendimento();
	}
	
	public void filtrar() {
		
		if(filtroCliente != null) {
			try {
				setAtendimentos(service.buscarPorCliente(filtroCliente));
				for(int i = 0; i < atendimentos.size(); i++)
					System.out.println(atendimentos.get(i));
			} catch (NegocioException e) {
				e.printStackTrace();
			}
			System.out.println(filtroCliente);
		} else
			System.out.println("CLIENTE NULO");
		
	}

	public void preRender(ComponentSystemEvent e) {
		setAtendimentos(service.buscarTodos());
		setClientes(cService.buscarTodos());
		setAtendentes(aService.buscarTodos());
		setServicos(sService.buscarTodos());
	}
	
	public void preRenderGeral(ComponentSystemEvent e) {
		setAtendimentos(service.buscarTodos());
		setClientes(cService.buscarTodos());
		setAtendentes(aService.buscarTodos());
		setServicos(sService.buscarTodos());
		limpar();
	}
	
	public StreamedContent getFoto() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getRenderResponse()) {
            return new DefaultStreamedContent();
        }
        else {
            String id = context.getExternalContext().getRequestParameterMap().get("id");
			
			try {
				Cliente c = cService.buscarPorCodigo(Integer.valueOf(id));
				if((c == null) || (c.getFoto() == null))
					return new DefaultStreamedContent();
				return new DefaultStreamedContent(new ByteArrayInputStream(c.getFoto()));
			} catch (NumberFormatException e) {
				return new DefaultStreamedContent();
			} catch (NegocioException e) {
				return new DefaultStreamedContent();
			}
			
        }
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

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public List<Atendimento> getAtendimentos() {
		return atendimentos;
	}

	public Cliente getFiltroCliente() {
		return filtroCliente;
	}

	public void setFiltroCliente(Cliente filtroCliente) {
		this.filtroCliente = filtroCliente;
	}

	public Atendente getFiltroAtendente() {
		return filtroAtendente;
	}

	public void setFiltroAtendente(Atendente filtroAtendente) {
		this.filtroAtendente = filtroAtendente;
	}
}
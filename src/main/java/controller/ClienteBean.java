package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.Part;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import modelo.Cliente;
import modelo.Sexo;
import service.ClienteService;
import util.FacesMensagens;
import util.NegocioException;
import util.Util;

@ManagedBean(name="cliente")
@SessionScoped
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ClienteService service = new ClienteService();
	private List<Cliente> clientes;
	private Cliente obj = new Cliente();
	private Part part;
	private byte[] image;
	
	public ClienteBean() {
		setClientes(service.buscarTodos());
		obj.setSexo(Sexo.MASCULINO);
	}	
	
	public String adicionar() {
		try {
			// Adicionar foto:
			if(part != null) {
				try {
					image = Util.getBytesFromInputStream(part.getInputStream());
					if(image != null)
						obj.setFoto(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			Set<String> roles = new HashSet<String>();
			roles.add("CLIENTE");
			obj.setPermissao(roles);
			service.salvar(obj);
			setClientes(service.buscarTodos());
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
		return "novo_cliente?faces-redirect=true";
	}	
	
	public String editar(){
		return "novo_cliente?faces-redirect=true";
	}	
	
	public void excluir() {
		try {
			service.remover(obj);
			setClientes(service.buscarTodos());	
		} catch (NegocioException e) {
			FacesMensagens.error(e.getMessage());
		}
	}
	
	private void limpar() {
		obj = new Cliente();
		obj.setSexo(Sexo.MASCULINO);
	}
	
	public void preRender(ComponentSystemEvent e) {
		setClientes(service.buscarTodos());
	}
	
	public StreamedContent getFoto() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getRenderResponse()) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Get ID value from actual request param.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
			
			try {
				Cliente c = service.buscarPorCodigo(Integer.valueOf(id));
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
	
	public StreamedContent getImage() {
		return Util.getImage(image, "imgShow");
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public Sexo[] getSexos() {
        return Sexo.values();
    }
	
	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> Clientes) {
		this.clientes = Clientes;
	}

	public Cliente getObj() {
		return obj;
	}

	public void setObj(Cliente obj) {
		this.obj = obj;
	}
	
	public List<Cliente> getclientes() {
		return clientes;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

}
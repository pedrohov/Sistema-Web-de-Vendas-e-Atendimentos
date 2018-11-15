package controller;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.Part;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import modelo.Marca;
import service.MarcaService;
import util.FacesMensagens;
import util.NegocioException;
import util.Util;

@ManagedBean(name="marca")
@SessionScoped
public class MarcaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private MarcaService service = new MarcaService();
	private List<Marca> marcas;
	private Marca obj = new Marca();
	
	private Part part;
	private byte[] image;
	
	public MarcaBean() {
		setMarcas(service.buscarTodos());
	}
	
	public String adicionar() {
		try {
			// Adicionar foto:
			if(part != null) {
				image = Util.getBytesFromInputStream(part.getInputStream());
				if(image != null)
					obj.setLogo(image);
			} 
			
			service.salvar(obj);
			setMarcas(service.buscarTodos());
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
		return "nova_marca?faces-redirect=true";
	}
	
	public String editar(){
		return "nova_marca?faces-redirect=true";
	}	
	
	public void excluir() {
		try {
			service.remover(obj);
			setMarcas(service.buscarTodos());
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}

	private void limpar() {
		obj = new Marca();
	}
	
	public void preRender(ComponentSystemEvent e) {
		setMarcas(service.buscarTodos());
	}
	
	public void getDisplayImage() {
		FacesContext context = FacesContext.getCurrentInstance();
		String id = context.getExternalContext().getRequestParameterMap().get("index");
		System.out.println(id);
		
	}
	
	public StreamedContent getFoto ()  {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getRenderResponse()) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Get ID value from actual request param.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
			try {
				Marca m = service.buscarPorCodigo(Integer.valueOf(id));
				if((m == null) || (m.getLogo() == null))
					return new DefaultStreamedContent();
				return new DefaultStreamedContent(new ByteArrayInputStream(m.getLogo()));
			} catch (NumberFormatException e) {
				return new DefaultStreamedContent();
			} catch (NegocioException e) {
				return new DefaultStreamedContent();
			}
        }
    }
	
	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}
	
	public StreamedContent getImage() {
		return Util.getImage(image, "imgShow");
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public List<Marca> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}

	public Marca getObj() {
		return obj;
	}

	public void setObj(Marca obj) {
		this.obj = obj;
	}
}
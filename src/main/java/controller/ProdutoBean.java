package controller;

import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import modelo.Marca;
import modelo.Produto;
import service.MarcaService;
import service.ProdutoService;
import util.FacesMensagens;
import util.NegocioException;
import util.Util;

@ManagedBean(name="produto")
@SessionScoped
public class ProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto obj = new Produto();
	private ProdutoService service = new ProdutoService();
	private List<Produto> produtos;
	private List<String> attributes = new ArrayList<String>();
	private String attribute;
	private Part part;
	private byte[] image;
	
	private MarcaService mService = new MarcaService();
	private List<Marca> marcas;
	
	
	public ProdutoBean() {
		setProdutos(service.buscarTodos());
		setMarcas(mService.buscarTodos());
		obj.setEstoque(0);
		obj.setPreco(0f);
	}
	
	public String adicionar() {
		try {
			// Adicionar foto:
			if(part != null) {
				image = Util.getBytesFromInputStream(part.getInputStream());
				if(image != null)
					obj.setFoto(image);
			}
			
			// Adicionar Atributos:
			obj.setAtributos(attributes);
			service.salvar(obj);
			FacesMensagens.info("Registro salvo com sucesso.");
			limpar();
			
		} catch (IOException e) {
			FacesMensagens.error(e.getMessage());
		} catch(NegocioException e) {
			FacesMensagens.error(e.getMessage());
		} 
		
		return "";
	}
	
	public void altEstoque() {
		try {
			service.salvar(obj);
			FacesMensagens.info("Registro salvo com sucesso.");
			limpar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excluir() {
		try {
			service.remover(obj);
			setProdutos(service.buscarTodos());
		} catch (NegocioException e) {
			FacesMensagens.error(e.getMessage());
		}
	}
	
	public String novo() {
		limpar();
		return "novo_produto?faces-redirect=true";
	}
	
	public String editar() {
		attributes = obj.getAtributos();
		return "novo_produto?faces-redirect=true";
	}
	
	private void limpar() {
		obj = new Produto();
		attributes = new ArrayList<String>();
		attribute = "";
	}
	
	public void preRender(ComponentSystemEvent e) {
		setProdutos(service.buscarTodos());
		setMarcas(mService.buscarTodos());
		attributes = new ArrayList<String>();
		image = null;
	}

	public Produto getObj() {
		return obj;
	}

	public void setObj(Produto obj) {
		this.obj = obj;
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

	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Marca> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}

	public String addAttribute() {
		if(!attributes.contains(attribute)) {
			this.attributes.add(attribute);
			attribute = "";
		}
		return "";
	}
	
	public String deleteAttribute(ActionEvent e) {
		UIComponent parent = e.getComponent().getParent();
		String value = (String)parent.getChildren().get(0).getAttributes().get("value");
		attributes.remove(value);
		parent = null;
		
		return "";
	}
	
	public StreamedContent getFoto () {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getRenderResponse()) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Get ID value from actual request param.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
			try {
				Produto p = service.buscarPorCodigo(Integer.valueOf(id));
				if((p == null) || (p.getFoto() == null))
					return new DefaultStreamedContent();
				return new DefaultStreamedContent(new ByteArrayInputStream(p.getFoto()));
			} catch (NumberFormatException e) {
				return new DefaultStreamedContent();
			} catch (NegocioException e) {
				 return new DefaultStreamedContent();
			}
        }
    }
	
}

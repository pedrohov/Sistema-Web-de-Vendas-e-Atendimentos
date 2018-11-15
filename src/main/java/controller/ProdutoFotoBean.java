package controller;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.StreamedContent;

import modelo.Produto;
import util.Util;

@ManagedBean(name="produtoFoto")
@ApplicationScoped
public class ProdutoFotoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Produto obj = new Produto();

	public StreamedContent getFoto() {
		
		return Util.getImage(obj.getFoto(), "imgShow");
	}

	public Produto getObj() {
		return obj;
	}

	public void setObj(Produto obj) {
		this.obj = obj;
	}
	
	
}
package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modelo.Produto;
import service.ProdutoService;
import util.NegocioException;

@FacesConverter(forClass=Produto.class)
public class ProdutoConverter implements Converter {

	private ProdutoService service = new ProdutoService();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Produto obj = null;
		
		if((value != null) && (!value.isEmpty())) {
			try {
				obj = service.buscarPorCodigo(Integer.parseInt(value));
			} catch (NegocioException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value != null) {
			Produto aux = (Produto) value;
			Integer cod = aux.getCodigo();
			if(cod != null)
				return cod.toString();
			return "";
		}
		else
			return "";
	}
	
}

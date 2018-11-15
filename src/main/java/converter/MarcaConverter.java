package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modelo.Marca;
import service.MarcaService;
import util.NegocioException;

@FacesConverter(forClass=Marca.class)
public class MarcaConverter implements Converter {

	private MarcaService service = new MarcaService();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Marca obj = null;
		
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
			Marca aux = (Marca) value;
			Integer cod = aux.getCodigo();
			return cod.toString();
		}
		else
			return null;
	}
	
}

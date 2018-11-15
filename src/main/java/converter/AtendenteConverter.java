package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modelo.Atendente;
import service.AtendenteService;
import util.NegocioException;

@FacesConverter(forClass=Atendente.class)
public class AtendenteConverter implements Converter {

	private AtendenteService service = new AtendenteService();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Atendente obj = null;
		
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
			Atendente aux = (Atendente) value;
			Integer cod = aux.getCodigo();
			return cod.toString();
		}
		else
			return null;
	}
	
}

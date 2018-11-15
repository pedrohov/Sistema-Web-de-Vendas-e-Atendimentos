package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modelo.Cliente;
import service.ClienteService;
import util.NegocioException;

@FacesConverter(forClass=Cliente.class)
public class ClienteConverter implements Converter {

	private ClienteService service = new ClienteService();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Cliente obj = null;
		
		if((value != null) && (!value.isEmpty())) {
			try {
				obj = service.buscarPorCodigo(Integer.parseInt(value));
			}catch (NegocioException e) {
				e.printStackTrace();
			}
		}
		
		return obj;
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value != null) {
			Cliente aux = (Cliente) value;
			Integer cod = aux.getCodigo();
			return cod.toString();
		}
		else
			return null;
	}
	
}

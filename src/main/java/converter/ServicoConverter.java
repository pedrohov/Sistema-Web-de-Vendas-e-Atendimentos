package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modelo.Servico;
import service.ServicoService;
import util.NegocioException;

@FacesConverter(forClass=Servico.class)
public class ServicoConverter implements Converter {

	private ServicoService service = new ServicoService();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Servico obj = null;
		
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
			Servico aux = (Servico) value;
			Integer cod = aux.getCodigo();
			return cod.toString();
		}
		else
			return null;
	}
	
}

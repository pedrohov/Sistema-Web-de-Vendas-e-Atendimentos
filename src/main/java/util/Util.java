package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class Util {
	
	public static boolean isCpfValido(String CPF) {
		
		// Formato: "xxx.xxx.xxx-xx":
		if(!CPF.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
			return false;
		}
		
		// Checa digito verificador:
		int d1 = Character.getNumericValue(CPF.charAt(12));
		int d2 = Character.getNumericValue(CPF.charAt(13));
		
		// Calcula o somatorio das posicoes:
		int somaD1 = 0;
		int somaD2 = 0;
		int k = 0;
		for(int i = 0; i < CPF.length() - 3; i++) {
			if((i != 3) && (i != 7)) {
				int digito = Character.getNumericValue(CPF.charAt(i));
				somaD1 += digito * (k + 1);
				somaD2 += digito * k;
				k++;
			}
		}

		// Checa se o primeiro digito esta correto:
		int calcD1 = somaD1 % 11;
		if(calcD1 != d1)
			return false;
		
		// Checa se o segundo digito esta correto:		
		somaD2 += (calcD1 * 9);
		int calcD2 = somaD2 % 11;
		
		if(calcD2 != d2)
			return false;
		
		return true;
	}

	public static byte[] getBytesFromInputStream(InputStream is) {
	    ByteArrayOutputStream os = new ByteArrayOutputStream(); 
	    byte[] buffer = new byte[0xFFFF];
	    try {
			for (int len = is.read(buffer); len != -1; len = is.read(buffer)) { 
			    os.write(buffer, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	    return os.toByteArray();
	}
	
	public static StreamedContent getImage(byte[] image, String componentId) {
		
		FacesContext context = FacesContext.getCurrentInstance();

        if ((context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) || (image == null)) {
            return new DefaultStreamedContent();
        } else {
            /*String imageId = context.getExternalContext().getRequestParameterMap().get(componentId);*/
            return new DefaultStreamedContent(new ByteArrayInputStream(image));
        }
	}
	
	public static void filterAttributes(String div) {
		
		String str = div.substring(div.indexOf(">") + 1, div.length());
		str = str.substring(str.indexOf(">") + 1, str.length());
		str = str.substring(0, str.indexOf("<"));
		System.out.println(str);
		
	}
	
}

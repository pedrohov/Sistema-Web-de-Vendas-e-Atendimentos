package util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class FacesMensagens implements Serializable{

	private static final long serialVersionUID = 1L;

	private static void add(String message, Severity severity){
		
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(severity);
		context.addMessage(null, msg);
		
	}
	
	public static void info(String message){
		add(message,FacesMessage.SEVERITY_INFO);
	}
	
	public static void error(String message){
		add(message,FacesMessage.SEVERITY_ERROR);
	}
	
}

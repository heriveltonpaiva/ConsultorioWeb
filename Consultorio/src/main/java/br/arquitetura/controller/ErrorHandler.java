package br.arquitetura.controller;

import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class ErrorHandler {

	public String getStatusCode(){
		String val = String.valueOf((Integer)FacesContext.getCurrentInstance().getExternalContext().
				getRequestMap().get("javax.servlet.error.status_code"));
		return val;
	}

	public String getMessage(){
		String val =  (String)FacesContext.getCurrentInstance().getExternalContext().
			getRequestMap().get("javax.servlet.error.message");
		return val;
	}

	public String getExceptionType(){
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.get("javax.servlet.error.exception_type") != null) {
			String val = FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
					.get("javax.servlet.error.exception_type").toString();
			return val;
		} else {
			return "";
		}
	}

	public String getException(){
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.get("javax.servlet.error.exception") != null) {
			String val = (String) ((Exception) FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
					.get("javax.servlet.error.exception")).toString();
			return val;
		} else {
			return "";
		}
	}

	public String getRequestURI(){
		return (String)FacesContext.getCurrentInstance().getExternalContext().
			getRequestMap().get("javax.servlet.error.request_uri");
	}

	public String getServletName(){
		return (String)FacesContext.getCurrentInstance().getExternalContext().
			getRequestMap().get("javax.servlet.error.servlet_name");
	}

	
}

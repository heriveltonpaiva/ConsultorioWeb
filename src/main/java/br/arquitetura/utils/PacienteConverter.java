package br.arquitetura.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.arquitetura.dominio.Pessoa;
import br.arquitetura.service.PessoaServiceImpl;

@FacesConverter("pacienteConverter")
public class PacienteConverter implements Converter{
	 
	    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
	        if(value != null && value.trim().length() > 0) {
	            try {
	                PessoaServiceImpl service = new PessoaServiceImpl();
	                Object obj = null;
	                Pessoa pessoa = null;
	                try{  
	                    
	                	Integer.parseInt(value);  
	                	if(Integer.parseInt(value)>0){
	                	 obj =  service.listarPorId("Pessoa", Integer.parseInt(value));
	                	 pessoa = (Pessoa) obj;
	                	}
		            	 
	               }catch(NumberFormatException e){ 
	            	  pessoa = new Pessoa();
		              pessoa.setNome(value);
	               }
	                return pessoa;
	            } catch(NumberFormatException e) {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
	            }
	        }
	        else {
	            return null;
	        }
	    }
	 
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) {
	            return String.valueOf(((Pessoa) object).getId());
	        }
	        else {
	            return null;
	        }
	    }   
}

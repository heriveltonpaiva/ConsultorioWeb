package br.arquitetura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils {
    
    public static ApplicationContext ctx;
    private String currentUser;
    /**
     * Make Spring inject the application context
     * and save it on a static variable,
     * so that it can be accessed from any point in the application. 
     */
    @Autowired
    private void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;       
        
    }
    
    public String getCurrentUser() {
    	if(SecurityContextHolder.getContext().getAuthentication()==null){
    		return "";
    	}else{
    	  Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    			    if (principal instanceof User){ 
    			    	currentUser = ((User) principal).getUsername();
    			    }
    	}
    	return "";
	}
    
    public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
}
package br.arquitetura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils {

    public static ApplicationContext ctx;
    
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
    	  Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    			    if (principal instanceof User) 
    			    	return ((User) principal).getUsername();
    			    return "";
	}
    
}
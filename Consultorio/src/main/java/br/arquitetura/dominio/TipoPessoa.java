package br.arquitetura.dominio;

public class TipoPessoa {

   public static final int PACIENTE = 1;

   public static final int DENTISTA = 2;

   public static final int USUARIO = 3;

   
   public boolean isPaciente(int i){
	   if(i == PACIENTE){return true;}
	   return false;
   }
   public boolean isDentista(int i){
	   if(i == DENTISTA){return true;}
	   return false;
   }
   
   public boolean isUsuario(int i){
	   if(i == USUARIO){return true;}
	   return false;
   }
   
   
	
}

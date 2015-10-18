package br.arquitetura.utils;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.arquitetura.dao.PessoaDao;
import br.arquitetura.dominio.Arquivo;
import br.arquitetura.service.PessoaServiceImpl;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {
    private static final long serialVersionUID = -4635239628819022096L;
 	private PessoaServiceImpl service;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Retirando a barra / da URL
        // No caso, se o usuário digitasse www.projeto.com/images/1
        // o request.getPathInfo() iria retornar: /1
        String s = request.getPathInfo().substring(1);
 
        
        service = new PessoaServiceImpl();
        
        if(s!=null){
        Arquivo arq =  service.findArquivoByIdPessoa(Integer.parseInt(s));
        //Recuperando um objeto imagem do banco de dados através do ID passado na URL digitada
	        if(arq!=null){
		        response.setContentType(arq.getContentType()); //Passando o tipo da foto ex: jpg, png, etc.
		        OutputStream out = response.getOutputStream();
		        out.write(arq.getConteudo()); // Passando o array de bytes
		        out.close();
	        }
        }
        // Aqui a imagem estará renderizada e disponível em,
        // por exemplo: www.projeto.com/images/1
    }
}


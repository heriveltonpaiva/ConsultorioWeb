package br.arquitetura.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dao.GenericDaoImpl;

@Service
public class GenericServiceImpl<T> implements GenericService<T>{

	private GenericDaoImpl dao;
	
	public void cadastrar(T obj) {
		    dao = new GenericDaoImpl();
		    dao.cadastrar(obj);	
	}

	public void alterar(T obj) {
			dao.alterar(obj);		
	}

	public void remover(T obj) {
		   dao.remover(obj);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> listarTodos(String nameClass) {
	    dao = new GenericDaoImpl();
		return (List<T>) dao.findByClassName(nameClass);
	}

	@SuppressWarnings("unchecked")
	public T listarPorId(String nameClass, int id) {
	    dao = new GenericDaoImpl();
		return (T) dao.findById(nameClass, id);
	}
	
	public Integer nextSequence(String sequence) {
	    dao = new GenericDaoImpl();
		return dao.nextSequence(sequence);
	}

}

package br.arquitetura.dao;

import java.util.List;

import org.hibernate.Session;


public interface GenericDao<T> {

	public void cadastrar(T obj);
	
	public void alterar(T obj);

	public void remover(T obj);

	public List<T> findByClassName(String className);
	
	public T findById(String className, int id);
	
	public Integer nextSequence(String sequence);
	
	public Session getSession();

	
}

package br.arquitetura.service;

import java.util.List;

public interface GenericService<T> {

	public void cadastrar(T obj);
	
	public void alterar(T obj);

	public void remover(T obj);

	public List<T> listarTodos(String nameClass);
	
	public T listarPorId(String nameClass, int id);
	
	public Integer nextSequence(String sequence);
}

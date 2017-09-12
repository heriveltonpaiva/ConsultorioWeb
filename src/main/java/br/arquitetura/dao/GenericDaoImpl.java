package br.arquitetura.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.controller.SpringUtils;

@Repository
@EnableTransactionManagement
@Transactional
public class GenericDaoImpl implements GenericDao<Object>, Serializable{

	private static final long serialVersionUID = 1L;
	private SessionFactory sessionFactory = null;
	
	public GenericDaoImpl() {
		if(sessionFactory == null){
			sessionFactory = (SessionFactory) SpringUtils.ctx.getBean("sessionFactory");
		}
	}
	
	public void cadastrar(Object obj) {
		try{
			getSession().save(obj);
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
	}

	public void alterar(Object obj) {
		try{
			getSession().merge(obj);
		}catch(HibernateException h){
           h.printStackTrace();
		}	
	}


	public void remover(Object obj) {
		try{
			getSession().delete(obj);
		}catch(HibernateException h){
			h.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> findByClassName(String nameClass) {
		 List<Object> objects = null;
	        try {
	        	
	            Query query = getSession().createQuery("from "+nameClass);
	            
	            if(query!=null)
	            objects = query.list();
	            
	        } catch (Exception e) {
	              e.printStackTrace();
	        }
	        return objects;
	}
	
	public Object findById(String className, int id) {
        Query query = getSession().createQuery("from "+className +" obj where obj.id = "+id);
		return query.uniqueResult();
	}
	
	public Integer nextSequence(String sequence) {
		Query q = getSession().createSQLQuery(" select nextval ('"+sequence+"')");
		return ((BigInteger) q.uniqueResult()).intValue();
	}
	
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
}

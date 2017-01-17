package in.brewcode.api.persistence.dao.common;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
@Repository
public abstract class GenericDao<T extends Serializable> implements
		IOperationsDao<T> {

	
	@PersistenceContext
	protected EntityManager entityManager;
	

	private Class<T> clazz;

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T getById(Long id) {
		System.out.println(clazz.getName() +" Entity Name");
		T t = entityManager.find(clazz, new Long(id));
	
		return t;
	}

	public void delete(T t) {
		entityManager.remove(t);
	}

	public void update(T t) {
		entityManager.merge(t);
	}
	
	public void save(T t) {
		
		entityManager.persist(t);
		
		
//		return id;
	}

}

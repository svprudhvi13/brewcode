package in.brewcode.api.persistence.dao.common;

import java.io.Serializable;

public interface IOperationsDao<T extends Serializable> {

	public T getById(Long id);
	public void delete(T t);
	public void update(T t);
	public void save(T t);
}

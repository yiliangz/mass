package org.mass.framework.core.service;

import org.mass.framework.core.repository.BaseRepository;
import org.apache.commons.beanutils.MethodUtils;

import java.util.List;

public abstract class BaseService<T>{


	public abstract BaseRepository<T> getMapper();

	public void add(T t)  throws Exception{
		getMapper().add(t);
	}

	public void update(T t)  throws Exception{
		getMapper().update(t);
	}

	public void updateBySelective(T t){
		getMapper().updateBySelective(t);
	}

	public void delete(Object... ids) throws Exception{
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			getMapper().delete(id);
		}
	}

	public void addAll(List<T> list) throws Exception{
		for(T t : list ){
			getMapper().add(t);
		}
	}

	public int queryByCount(Object object)throws Exception{
		return getMapper().queryByCount(object);
	}

	public List<T> queryByList(Object object) throws Exception{
		int rowCount = getMapper().queryByCount(object);
		MethodUtils.invokeMethod(object,"setRowCount",rowCount);
		return getMapper().queryByList(object);
	}

	public List<T> queryAll(Object object) throws Exception{
		return getMapper().queryByList(object);
	}

	public T queryById(Object id) throws Exception{
		return getMapper().queryById(id);
	}

	public List<T> queryByMode(Object object) throws Exception{
		return getMapper().queryByMode(object);
	}

	public T queryByOne(Object object) {
		List<T> list = getMapper().queryByList(object);
		if (list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	public boolean isExists(Object object)throws Exception{
		return getMapper().queryByCount(object) > 0;
	}

}

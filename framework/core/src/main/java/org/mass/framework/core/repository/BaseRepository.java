package org.mass.framework.core.repository;

import java.util.List;

public interface BaseRepository<T> {

	public void add(T t);

	public void update(T t);

	public void updateBySelective(T t);

	public void delete(Object id);

	public int queryByCount(Object object);

	public List<T> queryByList(Object object);

	public T queryById(Object id);

	public List<T> queryByMode(Object object);

}

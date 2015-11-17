package org.mass.framework.core.repository;

/**
 * Created by Allen on 2015-11-17.
 */
public interface CrudRepository<T,PK> {

    public T get(PK id);

    public T add(T entity);

//    public List<T> saveEntities(List<T> entities);
//
    public void update(T entity);
//
//    public void delete(PK id);
//
//    public List<T> getAll();
//
//    public List<T> query(String sql,Object[] params);
//
//    public long getCount(Map<String, String> searchParams);

}

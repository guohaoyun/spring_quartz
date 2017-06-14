package com.ghy.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

import com.ghy.vo.Page;

/**  
*  
*
* @author ghy  
* @date 2017年5月13日
* 类说明  :
*/
public interface BaseDao <T, PK extends Serializable>{

	public byte[] getSessionByte();
	
	public PK save(T entity);

	public void saveOrUpdate(T entity);

	public void update(T entity);

	public void merge(T entity);

	public void deleteById(PK id);
	
	public void deleteByIds(int[] ids);
	
	public void deleteObject(T entity);

	public boolean exists(PK id);

	public T load(PK id);

	public T get(PK id);

	public int countAll();

	public int countAll(Criteria criteria);

	public void deleteAll(Collection<?> entities);

	public List<T> list();

	public List<T> list(Criteria criteria);



	public List<T> list(String orderBy, boolean isAsc);

	public List<T> list(String propertyName, Object value);

	public List<T> list(Criterion... criterions);

	public T uniqueResult(String propertyName, Object value);

	public T uniqueResult(Criterion... criterions);

	public T uniqueResult(Criteria criteria);

	public void flush();

	public void clear();

	public Criteria createCriteria();

	public Criteria createCriteria(Criterion... criterions);

	public List<T> findListByCriteria(Criteria criteria, int pageSize,int pageIndex);
	
	public Page<T> findPageByCriteria(Criteria criteria,int pageSize ,
			int pageIndex );

	public Page<T> findPage(int pageSize, int pageIndex, String hql);

	public List<T> getList(int pageSize, int pageIndex, String hql);
	
	public List<T> findByHql(String hql);
	
	public List<T> findBySql(String sql);
	
}


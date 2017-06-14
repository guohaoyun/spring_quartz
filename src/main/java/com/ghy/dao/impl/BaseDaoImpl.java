package com.ghy.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ghy.dao.BaseDao;
import com.ghy.vo.Page;

/**  
*  
*
* @author ghy  
* @date 2017年5月13日
* 类说明  :
*/
public class BaseDaoImpl<T, PK extends java.io.Serializable> implements BaseDao<T, Serializable>{

	// 泛型反射类
			private Class<T> entityClass;
			// 通过反射获取子类确定的泛型类
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public BaseDaoImpl() {
				//返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type
				//然后将其转换ParameterizedType
				Type genType = getClass().getGenericSuperclass();
				//返回表示此类型实际类型参数的 Type 对象的数组。[0]就是这个数组中第一个了(T,Pk)
				Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
				entityClass = (Class) params[0];

			}
			
			
			/**
			 * 
			 * 注入sessionFactory
			 */

			@Autowired
			@Qualifier("sessionFactory")
			private SessionFactory sessionFactory;

			public Session openSession() {
				return sessionFactory.openSession();
			}

			public Session getSession() {

				// 事务必须是开启的(Required)，否则获取不到
				return sessionFactory.getCurrentSession();

			}

			/*
			 * 
			 * 密钥
			 */
			@Override
			public byte[] getSessionByte() {
				
				return  getSession().toString().getBytes();
				
			}
			/*
			 * 
			 * 保存PO
			 */
			@Override
			public Serializable save(T entity) {

				return getSession().save(entity);

			}

			/*
			 * 
			 * 保存或更新PO
			 */
			@Override
			public void saveOrUpdate(T entity) {

				getSession().saveOrUpdate(entity);

			}

			/*
			 * 
			 * 更新PO
			 */
			@Override
			public void update(T entity) {

				getSession().update(entity);

			}

			/*
			 * 
			 * 合并PO
			 */
			@Override
			public void merge(T entity) {

				getSession().merge(entity);

			}

			 /** 
			 * 根据id删除PO
			 */
			@Override
			public void deleteById(Serializable id) {

				getSession().delete(this.get(id));

			}
			
			@Override
			public void deleteByIds(int[] ids){
				for(int i=0;i<ids.length;i++){
					getSession().delete(this.get(ids[i]));
				}
			}

			/*
			 * 
			 * 删除PO
			 */
			@Override
			public void deleteObject(T entity) {

				getSession().delete(entity);

			}

			
			/** 
			 * 根据id判断PO是否存在
			 */
			@Override
			public boolean exists(Serializable id) {

				return get(id) != null;

			}

			/**
			 * 
			 * 根据id加载PO
			 */
			@Override
			@SuppressWarnings("unchecked")
			public T load(Serializable id) {

				return (T) getSession().load(this.entityClass, id);

			}

			/**
			 * 
			 * 根据id获取PO
			 */
			@Override
			@SuppressWarnings("unchecked")
			public T get(Serializable id) {

				return (T) getSession().get(this.entityClass, id);

			}
			
			

			/**
			 * 
			 * 获取PO总数(默认为entityClass)
			 */
			@Override
			public int countAll() {

				Criteria criteria = createCriteria();

				return Integer.valueOf(criteria.setProjection(Projections.rowCount())

				.uniqueResult().toString());

			}

			/**
			 * 
			 * 根据Criteria查询条件，获取PO总数
			 */
			@Override
			public int countAll(Criteria criteria) {

				return Integer.valueOf(criteria.setProjection(Projections.rowCount())

				.uniqueResult().toString());

			}

			/**
			 * 
			 * 删除所有
			 */
			@Override
			public void deleteAll(Collection<?> entities) {

				if (entities == null)

					return;

				for (Object entity : entities) {

					getSession().delete(entity);

				}

			}

			/**
			 * 
			 * 获取全部对象
			 */
			@Override
			@SuppressWarnings("unchecked")
			public List<T> list() {

				return createCriteria().list();

			}

			/**
			 * 
			 * 获取对象列表根据Criteria
			 */
			@Override
			@SuppressWarnings("unchecked")
			public List<T> list(Criteria criteria) {

				return criteria.list();

			}

		

			/**
			 * 
			 * 获取全部对象，支持排序
			 * 
			 * @param orderBy
			 * @param isAsc
			 * @return
			 */

			@Override
			@SuppressWarnings("unchecked")
			public List<T> list(String orderBy, boolean isAsc) {

				Criteria criteria = createCriteria();

				if (isAsc) {

					criteria.addOrder(Order.asc(orderBy));

				} else {

					criteria.addOrder(Order.desc(orderBy));

				}

				return criteria.list();

			}

			/**
			 * 
			 * 按属性查找对象列表，匹配方式为相等
			 * 
			 * @param propertyName
			 * @param value
			 * @return
			 */
			@Override
			public List<T> list(String propertyName, Object value) {

				Criterion criterion = Restrictions

				.like(propertyName, "%" + value + "%");

				return list(criterion);

			}

			/**
			 * 
			 * 根据查询条件获取数据列表
			 */
			@SuppressWarnings("unchecked")
			private List<T> list(Criterion criterion) {

				Criteria criteria = createCriteria();

				criteria.add(criterion);

				return criteria.list();

			}

			/**
			 * 
			 * 按Criteria查询对象列表
			 * 
			 * @param criterions数量可变的Criterion
			 * 
			 * @param criterions
			 * 
			 * @return
			 */
			@Override
			@SuppressWarnings("unchecked")
			public List<T> list(Criterion... criterions) {

				return createCriteria(criterions).list();

			}

			/**
			 * 
			 * 按属性查找唯一对象，匹配方式为相等
			 * 
			 * @param propertyName
			 * 
			 * @param value
			 * 
			 * @return
			 */
			@Override
			@SuppressWarnings("unchecked")
			public T uniqueResult(String propertyName, Object value) {

				Criterion criterion = Restrictions.eq(propertyName, value);

				return (T) createCriteria(criterion).uniqueResult();

			}
			
			public Object getUniqueResultByHql(String hql){
				Query query = getSession().createQuery(hql);   
				
				List<Object> result = query.list();
				
				return result.get(0);
			}

			/**
			 * 
			 * 按Criteria查询唯一对象
			 * @param criterions数量可变的Criterion
			 * @param criterions
			 * @return
			 */
			@Override
			public T uniqueResult(Criterion... criterions) {

				Criteria criteria = createCriteria(criterions);

				return uniqueResult(criteria);

			}

			/**
			 * 
			 * 按Criteria查询唯一对象
			 * 
			 * @param criterions
			 * 
			 * @return
			 */
			@Override
			@SuppressWarnings("unchecked")
			public T uniqueResult(Criteria criteria) {

				return (T) criteria.uniqueResult();

			}

			/**
			 * 
			 * 为Criteria添加distinct transformer
			 * 
			 * @param criteria
			 * 
			 * @return
			 */

			/**
			 * 
			 * 强制清空session
			 */
			@Override
			public void flush() {

				getSession().flush();

			}

			/**
			 * 
			 * 清空session
			 */
			@Override
			public void clear() {

				getSession().clear();

			}

			/**
			 * 
			 * 创建Criteria实例
			 */
			@Override
			public Criteria createCriteria() {

				return getSession().createCriteria(entityClass);

			}

			/**
			 * 
			 * 根据Criterion条件创建Criteria
			 * 
			 * @param criterions数量可变的Criterion
			 */
			@Override
			public Criteria createCriteria(Criterion... criterions) {

				Criteria criteria = createCriteria();

				for (Criterion c : criterions) {

					criteria.add(c);

				}

				return criteria;

			}

			
			
			public Page<T> findPageByCriteria(Criteria criteria,int pageSize ,int pageIndex){
				if(pageSize < 1){
					pageSize = 10;
				}
				
				int allRow = this.list(criteria).size();
				int totalPage = Page.countTotalPage(pageSize, allRow);
				
				int startpage;
				
				if(pageIndex < 1) {
					startpage = 1;
				}else if(pageIndex > totalPage){
					startpage = totalPage;
				}else{
					startpage = pageIndex;
				}
				
				Page<T> page = new Page<T>();
				
				/**
			       * 当前 页的 记录行
			       */
			      int pageStartRow = Page.countOffset(pageSize, startpage);

			      if (pageStartRow < 0) {
			        pageStartRow = 0;
			      }
			      final int offset = pageStartRow;
			      final int length = pageSize;
			      final int currentPage = Page.countCurrentPage(startpage);

				List<T> list = this.findListByCriteria(criteria,length, offset);
				
				page.setPageIndex(pageIndex);
				page.setPageSize(pageSize); 
				page.setCurrentPage(currentPage);
				page.setAllRow(allRow);
				page.setTotalPage(totalPage);
				page.setList(list);
				page.init();
				return page;
				
			}

			/**
			 * 分页 封装到 Page 对象 中 By HQL
			 */
			@Override
			public Page<T> findPage(int pageSize, int pageIndex, String hql) {
				
				if(pageSize < 1){
					pageSize = 10;
				}
				
				int allRow = getSession().createQuery(hql).list().size();
				int totalPage = Page.countTotalPage(pageSize, allRow);
				
				int startpage;
				
				
				if(pageIndex < 1) {
					startpage = 1;
				}else if(pageIndex > totalPage){
					startpage = totalPage;
				}else{
					startpage = pageIndex;
				}
				
				
				
				Page<T> page = new Page<T>();
				
				/**
				 * 当前 页的 记录行
				 */
				int pageStartRow = Page.countOffset(pageSize, startpage);

				if (pageStartRow < 0) {
					pageStartRow = 0;
				}
				final int offset = pageStartRow;
				final int length = pageSize;
				final int currentPage = Page.countCurrentPage(startpage);

				List<T> list = this.getList(length, offset, hql);
				page.setPageIndex(pageIndex);
				page.setPageSize(pageSize);
				page.setCurrentPage(currentPage);
				page.setAllRow(allRow);
				page.setTotalPage(totalPage);
				page.setList(list);
				page.init();
				return page;
			}

			/**
			 * 分页 封装到 List 对象 中 By HQL
			 */
			@Override
			@SuppressWarnings("unchecked")
			public List<T> getList(int pageSize, int pageIndex, String hql) {
				List<T> list = new ArrayList<T>();
				Query query = getSession().createQuery(hql);
				query.setFirstResult(pageIndex);
				query.setMaxResults(pageSize);
				list = query.list();
				return list;
			}
			
			/**
			 * 
			 * 分页 封装到 List 对象 中 By Criteria
			 * @param
			 * @return
			 */
			@Override
			public List<T> findListByCriteria(Criteria criteria, int pageSize,int pageIndex) {

				// 设置起始结果数
				criteria.setFirstResult(pageIndex);

				// 返回的最大结果集
				criteria.setMaxResults(pageSize);

				return list(criteria);

			}
			@Override
			@SuppressWarnings("unchecked")
			public  List<T>  findByHql(String hql) {
				
				List<T> list = new ArrayList<T>();
				Query query = getSession().createQuery(hql);
				list = query.list();
				return list;
				
			}

			@SuppressWarnings("unchecked")
			@Override
			public List<T> findBySql(String sql) {
				List<T> list = new ArrayList<T>();
				Query query = getSession().createSQLQuery(sql);
				list = query.list();
				return list;
			}
}


package jmrs.service.impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jmrs.service.BaseService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.io.Serializable;
import java.util.List;

@Component
@Transactional
public class BaseServiceImpl implements jmrs.service.BaseService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public <T> T save(T t) {
		entityManager.persist(t);
		return t;
	}

	@Override
	public <T> T update(T t) {
		entityManager.merge(t);
		return t;
	}

	@Override
	public <T> void delete(T t) {
		entityManager.remove(t);
	}

	@Override
	public <T> void delete(Class<T> clazz, Serializable id) {
		T t = entityManager.find(clazz, id);
		if (t != null) {
			delete(t);
		}
	}

	@Override
	public <T> T findOne(Class<T> clazz, Serializable id) {

		return entityManager.find(clazz, id);
	}

	@Override
	public <T> List<T> findAll(Class<T> clazz) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> rootEntry = cq.from(clazz);
		CriteriaQuery<T> all = cq.select(rootEntry);
		TypedQuery<T> allQuery = entityManager.createQuery(all);
		return allQuery.getResultList();
	}
}

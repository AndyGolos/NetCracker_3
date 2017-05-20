package com.Golosov.dao.implementations;

import com.Golosov.dao.interfaces.BaseDao;
import com.Golosov.entities.BaseEntity;
import com.Golosov.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Андрей on 16.05.2017.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractDao<T extends BaseEntity> implements BaseDao<T> {

    private static Logger logger = Logger.getLogger(AbstractDao.class);

    public AbstractDao(Class<T> persistentClass, String hql) {
        this.persistentClass = persistentClass;
        this.hql = hql;
    }

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> persistentClass;
    private String hql;

    public long save(T entity) {
        try {
            entityManager.persist(entity);
            return entity.getId();
        } catch (HibernateException e) {
            logger.error("Error was thrown in DAO: " + e);
            throw new DaoException();
        }
    }

    public List<T> getAll() {
        List<T> list;
        try {
            list = entityManager.createQuery(hql).getResultList();
        } catch (HibernateException e) {
            logger.error("Error was thrown in DAO: " + e);
            throw new DaoException();
        }
        return list;
    }

    public T getById(long id) {
        T entity;
        try {
            entity = entityManager.find(persistentClass, id);
        } catch (HibernateException e) {
            logger.error("Error was thrown in DAO: " + e);
            throw new DaoException();
        }
        return entity;
    }

    public void update(T entity) {
        try {
            entityManager.merge(entity);
        } catch (HibernateException e) {
            logger.error("Error was thrown in DAO: " + e);
            throw new DaoException();
        }
    }

    public void delete(long id) {
        try {
            T entity = entityManager.find(persistentClass, id);
            entityManager.remove(entity);
        } catch (HibernateException e) {
            logger.error("Error was thrown in DAO: " + e);
            throw new DaoException();
        }
    }
}

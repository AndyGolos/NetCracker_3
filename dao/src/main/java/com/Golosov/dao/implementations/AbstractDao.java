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

    AbstractDao(Class<T> persistentClass, String hql) {
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
            logger.debug("Entity: " + entity + " successfully saved!");
        } catch (HibernateException e) {
            logger.error("Error was thrown in AbstractDao method save: " + e);
            throw new DaoException(e);
        }
        return entity.getId();
    }

    public List<T> getAll() {
        List<T> list;
        try {
            list = entityManager.createQuery(hql).getResultList();
            logger.debug("All entities successfully found!");
        } catch (HibernateException e) {
            logger.error("Error was thrown in AbstractDao method getAll: " + e);
            throw new DaoException(e);
        }
        return list;
    }

    public T getById(long id) {
        T entity;
        try {
            entity = entityManager.find(persistentClass, id);
            logger.debug("Entity by id: " + id + " successfully found!");
        } catch (HibernateException e) {
            logger.error("Error was thrown in AbstractDao method getById: " + e);
            throw new DaoException(e);
        }
        return entity;
    }

    public void update(T entity) {
        try {
            entityManager.merge(entity);
            logger.debug("Entity: " + entity + " successfully updated!");
        } catch (HibernateException e) {
            logger.error("Error was thrown in AbstractDao method update: " + e);
            throw new DaoException(e);
        }
    }

    public void delete(long id) {
        try {
            T entity = entityManager.find(persistentClass, id);
            entityManager.remove(entity);
            logger.debug("Entity by id: " + id + " successfully deleted!");
        } catch (HibernateException e) {
            logger.error("Error was thrown in AbstractDao method delete: " + e);
            throw new DaoException(e);
        }
    }
}

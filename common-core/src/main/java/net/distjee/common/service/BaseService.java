package net.distjee.common.service;

import net.distjee.common.dao.BaseDao;
import net.distjee.common.exception.DaoException;
import net.distjee.common.pages.Pager;

import java.io.Serializable;
import java.util.List;

/**
 * Created by king on 2016/1/19 0019.
 */
public interface BaseService<T, PK extends Serializable> {

    public void insert(T t);

    public void insertList(List<T> ts);

    public void deleteById(PK pk);

    /**
     * 根据主键批量删除
     * @param pks
     */
    public void deleteByIds(PK... pks) throws DaoException;

    public void delete(T t);

    public void update(T t);

    public void updateNotNull(T t);

    public T getById(PK pk);

    public T get(T t);

    public List<T> list(T t);

    public List<T> listAll();

    public Pager query(T t,int pageSize,int pageNum);

}

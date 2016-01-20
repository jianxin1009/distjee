package net.distjee.common.service.impl;

import net.distjee.common.dao.BaseDao;
import net.distjee.common.dao.mapper.entity.Example;
import net.distjee.common.exception.DaoException;
import net.distjee.common.model.BaseModel;
import net.distjee.common.pages.Pager;
import net.distjee.common.service.BaseService;
import net.distjee.common.utils.ArrayUtil;
import net.distjee.common.utils.ClassUtil;
import net.distjee.common.utils.CollectionUtil;
import net.distjee.common.utils.ListUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by king on 2016/1/19 0019.
 */
@Service
public class BaseServiceImpl<T extends BaseModel,PK extends Serializable> implements BaseService<T,PK> {
    private Class<T> entityClass;
    private Class<PK> pkClass;
    public BaseServiceImpl(){
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass =  (Class)params[0];
        pkClass =  (Class)params[1];
    }

    @Autowired
    private BaseDao baseDao;

    @Override
    public void insert(T t) {
        if (null == t) {
            return;
        }
        baseDao.insert(t);
    }

    @Override
    public void insertList(List<T> ts) {
        if (CollectionUtil.isEmpty(ts)) {
            return;
        }
    }

    @Override
    public void deleteById(PK pk){
        if (null == pk) {
            return;
        }
        baseDao.deleteByPrimaryKey(pk);
    }

    @Override
    public void deleteByIds(PK... pks) throws DaoException {
        isPKJavaClass();
        if (ArrayUtil.isNotEmpty(pks)) {
            baseDao.deleteByPrimaryKeys(pks);
        }
    }

    @Override
    public void delete(T t) {
        if (null == t) {
            return;
        }
        baseDao.delete(t);
    }

    @Override
    public void update(T t) {
        baseDao.updateByPrimaryKey(t);
    }

    @Override
    public void updateNotNull(T t) {
        baseDao.updateByPrimaryKeySelective(t);
    }

    @Override
    public T getById(PK pk) {
        return (T)baseDao.selectByPrimaryKey(pk);
    }

    @Override
    public T get(T t) {
        return (T)baseDao.selectOne(t);
    }

    @Override
    public List<T> list(T t) {
        return baseDao.select(t);
    }

    @Override
    public List<T> listAll() {
        List<T> result = new ArrayList<T>();
        try {
            result= baseDao.select(entityClass.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Pager query(T t, int pageSize, int pageNum) {
        Pager pager = new Pager();
        pager.setPageSize(pageSize);
        pager.setCurrentPage(pageNum);
        pager.setData(baseDao.selectByRowBounds(t, new RowBounds(pageNum - 1, pageSize)));
        return pager;
    }

    private void isPKJavaClass() throws DaoException {
        if (!ClassUtil.isJavaClass(pkClass)) {
            throw new DaoException("此方法不支持复合主键");
        }
    }
}

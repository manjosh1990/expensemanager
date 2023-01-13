package com.xpense.services.app.repository;

import com.xpense.services.app.models.Category;
import com.xpense.services.app.models.XpenseCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository("xpenseRepositoryDao")
public class XpenseRepositoryDaoImpl implements XpenseRepositoryDao{

    @PersistenceContext
    private EntityManager entityManager;

    private <T>List<T> castList(Collection<?> c,Class<? extends T> clazz){
        List<T> r = new ArrayList<>(c.size());
        for(Object o: c){
            r.add(clazz.cast(o));
        }
        return r;
    }

    @Override
    public List<XpenseCategory> findAll() {
        return castList(entityManager.createQuery("From XpenseCategory").getResultList(),XpenseCategory.class);
    }

    @Override
    public List<Category> findAllCategory() {
        return castList(entityManager.createQuery("From Category").getResultList(),Category.class);
    }
}

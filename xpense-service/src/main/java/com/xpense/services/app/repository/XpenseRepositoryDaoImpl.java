package com.xpense.services.app.repository;

import com.xpense.services.app.fileprocessing.DefaultRawTransaction;
import com.xpense.services.app.models.Category;
import com.xpense.services.app.models.XpenseCategory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository("xpenseRepositoryDao")
public class XpenseRepositoryDaoImpl implements XpenseRepositoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    private <T> List<T> castList(Collection<?> c, Class<? extends T> clazz) {
        List<T> r = new ArrayList<>(c.size());
        for (Object o : c) {
            r.add(clazz.cast(o));
        }
        return r;
    }

    @Override
    public List<XpenseCategory> findAll() {
        return castList(entityManager.createQuery("From XpenseCategory").getResultList(), XpenseCategory.class);
    }

    @Override
    public List<Category> findAllCategory() {
        return castList(entityManager.createQuery("From Category").getResultList(), Category.class);
    }

    @Override
    @Transactional
    public <S extends DefaultRawTransaction> List<S> saveRawTransaction(List<S> rawTransactions) {
        List<S> result = new ArrayList<>();
        if (rawTransactions == null) {
            return result;
        }
        for (S entity : rawTransactions) {
            entityManager.persist(entity);
            result.add(entity);
        }
        return result;
    }
}

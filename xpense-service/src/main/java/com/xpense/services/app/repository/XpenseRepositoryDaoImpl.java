package com.xpense.services.app.repository;

import com.xpense.services.app.dto.NetWorthResponse;
import com.xpense.services.app.exceptions.ServiceException;
import com.xpense.services.app.fileprocessing.DefaultRawTransaction;
import com.xpense.services.app.models.Category;
import com.xpense.services.app.models.DescCategoryMapping;
import com.xpense.services.app.models.XpenseCategory;
import com.xpense.services.app.models.XpenseTransactions;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository("xpenseRepositoryDao")
public class XpenseRepositoryDaoImpl implements XpenseRepositoryDao {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
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

    @Override
    public List<XpenseTransactions> findAllXpenseTransactions() {
        return castList(entityManager.createQuery("From XpenseTransactions").getResultList(), XpenseTransactions.class);
    }

    @Override
    public List<DescCategoryMapping> findAllDescCategoryMapping() {
        return castList(entityManager.createQuery("From DescCategoryMapping").getResultList(), DescCategoryMapping.class);
    }

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public List<DescCategoryMapping> saveAllDescMappings(Set<DescCategoryMapping> mappingSet) {
        List<DescCategoryMapping> mappings = new ArrayList<>();
        if (mappingSet == null) {
            return mappings;
        }
        for (DescCategoryMapping descCategoryMapping : mappingSet) {
            try {
                entityManager.persist(descCategoryMapping);
            } catch (PersistenceException e) {
                Throwable t = e.getCause();
                while ((t != null) && !(t instanceof ConstraintViolationException)) {
                    t = t.getCause();
                }
                if (t != null) {
                    // Here you're sure you have a ConstraintViolationException, you can handle it
                    log.error("duplicate searchString {}", descCategoryMapping.getSearchString());
                }
            }
            mappings.add(descCategoryMapping);
        }
        return mappings;
    }

    @Override
    @Transactional
    public List<XpenseTransactions> saveAllXpenseTransactions(List<XpenseTransactions> xpenseTransactions) {
        List<XpenseTransactions> transactions = new ArrayList<>();
        if (xpenseTransactions == null) {
            return transactions;
        }
        for (XpenseTransactions xpenseTransaction : xpenseTransactions) {
            entityManager.persist(xpenseTransaction);
            transactions.add(xpenseTransaction);
        }
        return transactions;
    }

    @Override
    public NetWorthResponse findNetWorthFromLedger() {
        String query = "SELECT SUM(debit) AS total_debit, SUM(credit) AS total_credit FROM xpense_transactions";
        NetWorthResponse netWorthResponse = new NetWorthResponse();
        List<?> result = entityManager.createNativeQuery(query).getResultList();
        if (result != null && !result.isEmpty()) {
            Object[] row = (Object[]) result.get(0);
            BigDecimal debit = (BigDecimal) row[0];
            BigDecimal credit = (BigDecimal) row[1];
            BigDecimal balance = credit.subtract(debit);
            netWorthResponse.setBalance(balance);
            netWorthResponse.setTotalSpent(debit);
        }
        return netWorthResponse;
    }

    @Override
    public Double findSumFromCategoryName(String categoryName) {
        List<Category> categories = castList(entityManager.createQuery("from Category where name = ?1").setParameter(1, categoryName).getResultList(), Category.class);
        if (categories.isEmpty()) {
            throw new ServiceException(1001, "Category not found");
        }
        String jpqlQuery = "SELECT SUM(e.debit) FROM XpenseTransactions e WHERE e.xpenseCategory IN (SELECT re.id FROM XpenseCategory re WHERE re.category = :category)";

        TypedQuery<Double> query = entityManager.createQuery(jpqlQuery, Double .class);
        query.setParameter("category", categories.get(0).getId());

        List<Double> result = query.getResultList();
        if(result == null){
            throw new ServiceException(1002, "error computing sum");
        }
        return result.get(0);
    }
}

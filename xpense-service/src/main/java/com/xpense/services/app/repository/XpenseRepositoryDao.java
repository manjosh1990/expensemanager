package com.xpense.services.app.repository;

import com.xpense.services.app.dto.NetWorthResponse;
import com.xpense.services.app.fileprocessing.DefaultRawTransaction;
import com.xpense.services.app.models.Category;
import com.xpense.services.app.models.DescCategoryMapping;
import com.xpense.services.app.models.XpenseCategory;
import com.xpense.services.app.models.XpenseTransactions;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface XpenseRepositoryDao {
    List<XpenseCategory> findAll();

    List<Category> findAllCategory();

    <S extends DefaultRawTransaction> List<S> saveRawTransaction(List<S> rawTransactions);

    List<XpenseTransactions> findAllXpenseTransactions();

    List<DescCategoryMapping> findAllDescCategoryMapping();

    public List<DescCategoryMapping> saveAllDescMappings(Set<DescCategoryMapping> mappingSet);

    List<XpenseTransactions> saveAllXpenseTransactions(List<XpenseTransactions> xpenseTransactions);

    NetWorthResponse findNetWorthFromLedger();

    Double findSumFromCategoryName(String categoryName);
}

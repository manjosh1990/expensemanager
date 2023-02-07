package com.xpense.services.app.repository;

import com.xpense.services.app.fileprocessing.DefaultRawTransaction;
import com.xpense.services.app.models.Category;
import com.xpense.services.app.models.XpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface XpenseRepositoryDao{
    List<XpenseCategory> findAll();
    List<Category> findAllCategory();

    <S extends DefaultRawTransaction> List<S> saveRawTransaction(List<S> rawTransactions);
}

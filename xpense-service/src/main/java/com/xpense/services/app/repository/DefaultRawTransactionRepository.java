package com.xpense.services.app.repository;

import com.xpense.services.app.fileprocessing.DefaultRawTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DefaultRawTransactionRepository extends CrudRepository<DefaultRawTransaction,Integer> {
}

package com.xpense.services.app.repository;

import com.xpense.services.app.models.XpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface XpenseCategoryRepository extends JpaRepository<XpenseCategory,Integer> {
    List<XpenseCategory> findAll();
}

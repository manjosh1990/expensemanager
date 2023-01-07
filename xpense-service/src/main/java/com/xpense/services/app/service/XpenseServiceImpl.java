package com.xpense.services.app.service;

import com.xpense.services.app.dto.CategoryCard;
import com.xpense.services.app.dto.NetWorthResponse;
import com.xpense.services.app.models.XpenseCategory;
import com.xpense.services.app.repository.XpenseCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class XpenseServiceImpl implements XpenseService{

    @Autowired
    XpenseCategoryRepository xpenseCategoryRepository;
    @Override
    public NetWorthResponse getNetWorth() {
        Random random = new Random();
        return NetWorthResponse.builder().balance(150000.00).id(random.nextInt(1000)+1).build();
    }

    @Override
    public List<CategoryCard> getCategoryCardsData() {
        List<XpenseCategory> xpenseCategories = xpenseCategoryRepository.findAll();
        return null;
    }
}

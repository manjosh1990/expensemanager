package com.xpense.services.app.service;

import com.xpense.services.app.dto.CategoryCard;
import com.xpense.services.app.dto.NetWorthResponse;
import com.xpense.services.app.models.Category;
import com.xpense.services.app.models.XpenseCategory;
import com.xpense.services.app.repository.XpenseRepositoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class XpenseServiceImpl implements XpenseService{

    @Autowired
    XpenseRepositoryDao xpenseRepository;
    @Override
    public NetWorthResponse getNetWorth() {
        Random random = new Random();
        return NetWorthResponse.builder().balance(150000.00).id(random.nextInt(1000)+1).build();
    }

    @Override
    public List<CategoryCard> getCategoryCardsData() {
        List<XpenseCategory> xpenseCategories = xpenseRepository.findAll();
        List<Category> getCateGories = xpenseRepository.findAllCategory();
        System.out.println(getCateGories);
        System.out.println(xpenseCategories);
        return null;
    }
}

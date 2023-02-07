package com.xpense.services.app.service;

import com.xpense.services.app.dto.CategoryCard;
import com.xpense.services.app.dto.NetWorthResponse;
import com.xpense.services.app.fileprocessing.DefaultRawTransaction;
import com.xpense.services.app.fileprocessing.HdfcRawTransaction;
import com.xpense.services.app.fileprocessing.PDFFileProcessor;
import com.xpense.services.app.models.Category;
import com.xpense.services.app.models.XpenseCategory;
import com.xpense.services.app.repository.HdfcRawTransactionRepository;
import com.xpense.services.app.repository.XpenseRepositoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class XpenseServiceImpl implements XpenseService{

    @Autowired
    XpenseRepositoryDao xpenseRepository;

    @Autowired
    HdfcRawTransactionRepository hdfcRawTransactionRepository;

    @Autowired
    PDFFileProcessor pdfFileProcessor;
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

    @Override
    public <S extends DefaultRawTransaction> List<S> uploadStatement(String type) {
        List<HdfcRawTransaction> transactions = new ArrayList<>();
        if ("PDF".equals(type)) {
         transactions = pdfFileProcessor.loadPdfFile(true);
         hdfcRawTransactionRepository.deleteAll();
         transactions = (List<HdfcRawTransaction>) hdfcRawTransactionRepository.saveAll(transactions);
        }

        return (List<S>) transactions;
    }
}

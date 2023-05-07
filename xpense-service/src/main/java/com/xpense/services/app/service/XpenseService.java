package com.xpense.services.app.service;

import com.xpense.services.app.dto.CategoryCard;
import com.xpense.services.app.dto.NetWorthResponse;
import com.xpense.services.app.dto.XpenseResponse;
import com.xpense.services.app.fileprocessing.DefaultRawTransaction;
import com.xpense.services.app.models.XpenseTransactions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface XpenseService {
    NetWorthResponse getNetWorth();
    List<CategoryCard> getCategoryCardsData();
    <S extends DefaultRawTransaction> List<S> uploadStatement(String type);
    List<XpenseTransactions> processTransactions ();

    XpenseResponse getExpense(String category);
}

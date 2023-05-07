package com.xpense.services.app.service;

import com.xpense.services.app.dto.CategoryCard;
import com.xpense.services.app.dto.NetWorthResponse;
import com.xpense.services.app.dto.XpenseResponse;
import com.xpense.services.app.fileprocessing.DefaultRawTransaction;
import com.xpense.services.app.fileprocessing.HdfcRawTransaction;
import com.xpense.services.app.fileprocessing.PDFFileProcessor;
import com.xpense.services.app.models.Category;
import com.xpense.services.app.models.DescCategoryMapping;
import com.xpense.services.app.models.XpenseCategory;
import com.xpense.services.app.models.XpenseTransactions;
import com.xpense.services.app.repository.HdfcRawTransactionRepository;
import com.xpense.services.app.repository.XpenseRepositoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.xpense.services.app.utils.CategoryNames.FOODANDRESTAURANTS;

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
        return calculateNetWorth();
    }

    public NetWorthResponse calculateNetWorth() {
        return xpenseRepository.findNetWorthFromLedger();
    }

    @Override
    public List<CategoryCard> getCategoryCardsData() {
        List<XpenseCategory> xpenseCategories = xpenseRepository.findAll();
        List<Category> getCategories = xpenseRepository.findAllCategory();
        System.out.println(getCategories);
        System.out.println(xpenseCategories);
        return null;
    }

    @Override
    public <S extends DefaultRawTransaction> List<S> uploadStatement(String type) {
        List<HdfcRawTransaction> transactions = new ArrayList<>();
        if ("PDF".equals(type)) {
         transactions = pdfFileProcessor.loadPdfFile(true);
         hdfcRawTransactionRepository.deleteAll();
         HashSet<HdfcRawTransaction> newHdfcRawTransactions = new LinkedHashSet<>();
         transactions.forEach(transaction ->{
             if(!newHdfcRawTransactions.add(transaction)){
                 String desc = "dup-"+newHdfcRawTransactions.size();
                 transaction.setNarration(transaction.getNarration()+desc);
                 newHdfcRawTransactions.add(transaction);
             }
         });
         transactions = (List<HdfcRawTransaction>) hdfcRawTransactionRepository.saveAll(newHdfcRawTransactions);
        }
        /*
        //start processing the raw transactions

        List<String> searchStrings = null;
        // check for transactions which are not categorized;
        List<String> descriptions = transactions.stream()
                .filter(transaction -> transaction.getFkSubXpenseCategory() == null)
                .map(HdfcRawTransaction::getNarration).collect(Collectors.toList());
        Map<String,List<String>> groups = GroupStringsUtil.groupString(descriptions);
        searchStrings = GroupStringsUtil.getPrefixFromGroups(groups);
        Set<String> uniqueSearchStrings = new HashSet<>(searchStrings);
        Set<DescCategoryMapping> descCategoryMappings = uniqueSearchStrings.stream().map(this::mapToDescCategoryMapping).collect(Collectors.toSet());
        xpenseRepository.saveAllDescMappings(descCategoryMappings);*/
        return (List<S>) transactions;
    }

    private XpenseTransactions mapToXpenseTransactions(HdfcRawTransaction hdfcRawTransaction) {
        return XpenseTransactions.builder().date(hdfcRawTransaction.getTransactionDate()).xpenseCategory(hdfcRawTransaction.getFkSubXpenseCategory())
                .credit(hdfcRawTransaction.getDepositAmt()).debit(hdfcRawTransaction.getWithdrawalAmt()).description(hdfcRawTransaction.getNarration())
                .type("BANK").fkAccounts(1).build();
    }

    private DescCategoryMapping mapToDescCategoryMapping(String uniqueSearchString) {
        return DescCategoryMapping.builder().searchString(uniqueSearchString).build();
    }

    @Override
    public List<XpenseTransactions> processTransactions() {
        Iterable<DefaultRawTransaction> transactions = hdfcRawTransactionRepository.findAll();
        List<XpenseTransactions> newXpenseTransactions = new ArrayList<>();
        for (DefaultRawTransaction defaultRawTransaction : transactions){
            newXpenseTransactions.add(mapToXpenseTransactions((HdfcRawTransaction) defaultRawTransaction));
        }
       return xpenseRepository.saveAllXpenseTransactions(newXpenseTransactions);
    }

    @Override
    public XpenseResponse getExpense(String category) {
        XpenseResponse res = new XpenseResponse();
        switch (category){
            case "food" :
                Double sum = xpenseRepository.findSumFromCategoryName(FOODANDRESTAURANTS);
                res.setExpense(new BigDecimal(sum));
                res.setName(FOODANDRESTAURANTS);
                break;
            case "":
                break;
            default:

        }
        return res;
    }
}

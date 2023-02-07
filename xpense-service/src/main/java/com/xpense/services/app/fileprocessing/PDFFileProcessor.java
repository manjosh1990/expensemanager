package com.xpense.services.app.fileprocessing;


import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import com.xpense.services.app.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class PDFFileProcessor {
    protected String dateRegex = "^\\d{2}/\\d{2}/\\d{2}.*";
    public List<HdfcRawTransaction> loadPdfFile(boolean isWithdrawal) {
        List<HdfcRawTransaction> result = null;
        try {
            Resource resource = new ClassPathResource("transactions.pdf");
            List<HdfcRawTransaction> transactions = new ArrayList<>();
            PdfReader pdfReader = new PdfReader(resource.getInputStream());
            int pages = pdfReader.getNumberOfPages();
            log.info("no of pages {}", pages);
            PdfTextExtractor pdfTextExtractor = new PdfTextExtractor(pdfReader);
            boolean[] startFlag = new boolean[1];
            for (int i = 1; i <= pages; i++) {
                String contentOfPage = pdfTextExtractor.getTextFromPage(i, true);
                process(contentOfPage, transactions, isWithdrawal,startFlag);
            }
            result = transactions;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private void process(String pageContent, List<HdfcRawTransaction> rawTransactions, boolean isWithdrawal,boolean[] startFlag) {
        boolean start = startFlag[0];
        boolean fileHeader = true;
        boolean incompleteTransaction = false;
        StringBuilder descBuilder = null;
        String lineEnd = null;
        for (String line : pageContent.split("\\r?\\n")) {
            line = line.trim();
            if (!start) {
                log.debug("Starting line processing after this line: {}", line);
                start = true;
                startFlag[0]=true;
            } else {
                if (line.matches(dateRegex)) {
                    // log.info("new line : {}", line);
                    fileHeader = false;
                    String[] words;
                    if (incompleteTransaction) {
                        processIncompleteTransaction(descBuilder,lineEnd, (List<HdfcRawTransaction>) rawTransactions,isWithdrawal);
                        incompleteTransaction=false;
                        descBuilder=null;
                    }
                    words = line.split(" ");
                    boolean fullTransaction = checkIfContainsBalance(words);
                    if (!fullTransaction) {
                        //construct the correct transaction
                        incompleteTransaction = true;
                        descBuilder = new StringBuilder();
                        descBuilder.append(line).append(" ");
                        continue;
                    }
                    processLine(words, rawTransactions, isWithdrawal);
                } else if (incompleteTransaction) {
                    String[] words = line.split(" ");
                    boolean fullTransaction = checkIfContainsBalance(words);
                    if (!fullTransaction) {
                        descBuilder.append(line).append(" ");
                        continue;
                    }
                    lineEnd = line;
                } else {
                    if (line.contains("Page No .:") || line.contains("STATEMENT SUMMARY")) {
                        fileHeader = true;
                    }
                    if (!rawTransactions.isEmpty() && !fileHeader) {
                        log.info("next line :{}", line);
                        HdfcRawTransaction lastTransaction = (HdfcRawTransaction) rawTransactions.get(rawTransactions.size() - 1);
                        lastTransaction.setNarration(lastTransaction.getNarration().trim() + line);
                    }
                }
            }
        }
        if(incompleteTransaction){
            processIncompleteTransaction(descBuilder,lineEnd,rawTransactions,isWithdrawal);
        }
    }
    private void processIncompleteTransaction(StringBuilder descBuilder, String lineEnd,List<HdfcRawTransaction> rawTransactions, boolean isWithdrawal){
        if(lineEnd!=null)
            descBuilder.append(lineEnd);
        String[]   words = descBuilder.toString().split(" ");
        processLine(words, rawTransactions,isWithdrawal);
    }
    private boolean checkIfContainsBalance(String[] words) {
        String lastWord = words[words.length - 1];
        if (!lastWord.contains(",")) return false;
        String num = words[words.length - 1].replace(",", "");
        try {
            Double.parseDouble(num);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void processLine(String[] words, List<HdfcRawTransaction> rawTransactions, boolean isWithdrawal) {
        try {
            if (words == null || words.length < 2) return;
            HdfcRawTransaction hdfcRawTransaction = new HdfcRawTransaction();
            String date = words[0];
            Date valueDate = DateUtils.readDate(date);
            StringBuilder desc = new StringBuilder();
            hdfcRawTransaction.setTransactionDate(valueDate);
            int index = 0;
            for (int i = 1; i < words.length; i++) {
                if (i + 1 < words.length) {
                    if (words[i + 1].matches(dateRegex)) {
                        hdfcRawTransaction.setChqRefNo(words[i]);
                        index = i + 1;
                        break;
                    } else {
                        desc.append(words[i]).append(" ");
                    }
                }
            }
            hdfcRawTransaction.setNarration(desc.toString());
            double amt = Double.parseDouble(words[index + 1].replace(",", ""));
            double closingBalance = Double.parseDouble(words[words.length - 1].replace(",", ""));
            if (rawTransactions.isEmpty()) {
                //first transaction
                if ((words.length - index) - 1 == 3) {
                    hdfcRawTransaction.setWithdrawalAmt(amt);
                    hdfcRawTransaction.setDepositAmt(Double.parseDouble(words[index + 2].replace(",", "")));
                } else {
                    if (isWithdrawal) {
                        hdfcRawTransaction.setWithdrawalAmt(amt);
                    } else {
                        hdfcRawTransaction.setDepositAmt(amt);
                    }
                    hdfcRawTransaction.setClosingBalance(Double.parseDouble(words[index + 2].replace(",", "")));
                }
                hdfcRawTransaction.setClosingBalance(closingBalance);
                rawTransactions.add(hdfcRawTransaction);
                return;
            }
            if ((words.length - index) - 1 == 3) {
                hdfcRawTransaction.setWithdrawalAmt(amt);
                hdfcRawTransaction.setDepositAmt(Double.parseDouble(words[index + 2].replace(",", "")));
                hdfcRawTransaction.setClosingBalance(closingBalance);
                rawTransactions.add(hdfcRawTransaction);
                return;
            }
            HdfcRawTransaction lastTransaction = rawTransactions.get(rawTransactions.size() - 1);
            Double lastClosingBalance = lastTransaction.getClosingBalance();
            if (closingBalance < lastClosingBalance) {
                //withdrawal
                hdfcRawTransaction.setWithdrawalAmt(amt);
            } else {
                //deposit
                if(amt<0){//check reversal transactions
                    hdfcRawTransaction.setWithdrawalAmt(amt);
                }else
                 hdfcRawTransaction.setDepositAmt(amt);
            }
            hdfcRawTransaction.setClosingBalance(closingBalance);

            rawTransactions.add(hdfcRawTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PDFFileProcessor p = new PDFFileProcessor();
        //  p.loadPdfFile(true);
        String content = "UPI-MANJOSH RAMESH-ELECTRODE.1990-2@OKHD FCBANK-HDFC0004386\n" +
                "13/09/22 UPI-MANJOSH RAMESH-ELECTRODE.1990@OKHD 0000225639849307 13/09/22 16,000.00 9,404.48\n" +
                "FCBANK-HDFC0004386-225639849307-IPHONE M\n" +
                "UTIPL\n" +
                "13/09/22 UPI-ZERODHA BROKING\n" +
                "LTD-ZERODHABROKING@H\n" +
                "0000225679047277 13/09/22 5,316.00 4,088.48\n" +
                "DFCBANK-HDFC0000523-225679047277-6014842\n" +
                "506419946 Z\n" +
                "13/09/22 UPI-MANJOSH RAMESH-ELECTRODE.1990@OKHD 0000225639849307 13/09/22 16,000.00 9,404.48\n" +
                "FCBANK-HDFC0004386-225639849307-IPHONE M\n" +
                "UTIPL\n";
        List<HdfcRawTransaction> transactions = new ArrayList<>();
        boolean[] startFlag = new boolean[1];
        p.process(content, transactions, true,startFlag);
        transactions.forEach(transaction->{
            log.info(transaction.getTransactionDate().toString()+"|"+transaction.getNarration()+"|"+transaction.getChqRefNo()+"|"+transaction.getWithdrawalAmt()+"|"+transaction.getDepositAmt()+"|"+transaction.getClosingBalance());
        });
    }
}

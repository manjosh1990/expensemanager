package com.xpense.services.app.controller;

import com.xpense.services.app.dto.NetWorthResponse;
import com.xpense.services.app.dto.XpenseResponse;
import com.xpense.services.app.fileprocessing.DefaultRawTransaction;
import com.xpense.services.app.models.XpenseTransactions;
import com.xpense.services.app.service.XpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/dashboard")
@CrossOrigin
public class XpenseDashBoardController {

    @Autowired
    XpenseService xpenseService;

    @GetMapping("/getNetWorth")
    @ResponseStatus(HttpStatus.OK)
    public NetWorthResponse getNetWorth() {
        return xpenseService.getNetWorth();
    }

    @GetMapping("/{category}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getExpenseByCategory(@PathVariable String category) {
        XpenseResponse foodExpense = xpenseService.getExpense(category);
        return ResponseEntity.ok(foodExpense);
    }

    @PostMapping("/uploadStatement")
    public ResponseEntity<?> updateStatement(@RequestParam("type") String type){
        List<DefaultRawTransaction> rawTransactions =xpenseService.uploadStatement(type);
        return ResponseEntity.ok(rawTransactions);
    }

    @GetMapping("/processTransaction")
    public ResponseEntity<?> getXpenseTransactionAfterProcessing(){
       List<XpenseTransactions> xpenseTransactions = xpenseService.processTransactions();
        return ResponseEntity.ok(xpenseTransactions);
    }

}

package antifraud.controller;

import antifraud.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Validated
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/api/antifraud/transaction")
    public ResponseEntity transactionReq(@RequestBody Map<String, Long> transaction) {
        return transactionService.checkValidTransaction(transaction);
    }
}

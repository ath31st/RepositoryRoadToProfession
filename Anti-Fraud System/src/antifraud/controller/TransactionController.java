package antifraud.controller;

import antifraud.dto.ManualProcessingReq;
import antifraud.dto.StatusResp;
import antifraud.dto.TransactionReq;
import antifraud.dto.TransactionResp;
import antifraud.entity.StolenCard;
import antifraud.entity.SuspiciousIp;
import antifraud.entity.Transaction;
import antifraud.service.StolenCardService;
import antifraud.service.SuspiciousIpService;
import antifraud.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/antifraud")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private SuspiciousIpService suspiciousIpService;
    @Autowired
    private StolenCardService stolenCardService;

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResp> transactionReq(@RequestBody Transaction request) {
        return new ResponseEntity<>(transactionService.checkAndSaveTransactionInDb(request), HttpStatus.OK);
    }

    @PutMapping("/transaction")
    public ResponseEntity<Transaction> transactionFeedback(@RequestBody ManualProcessingReq request) {
        return new ResponseEntity<>(transactionService.manualProcessingTransaction(request), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Transaction>> getTransactionsHistory() {
        return new ResponseEntity<>(transactionService.getTransactionsHistory(), HttpStatus.OK);
    }

    @GetMapping("/history/{number}")
    public ResponseEntity<List<Transaction>> getTransactionsHistory(@PathVariable String number) {
        return new ResponseEntity<>(transactionService.getTransactionsHistoryByNumber(number), HttpStatus.OK);
    }

    @PostMapping("/suspicious-ip")
    public ResponseEntity<SuspiciousIp> saveSuspIpInDb(@RequestBody SuspiciousIp ip) {
        return new ResponseEntity<>(suspiciousIpService.saveSuspiciousIpInDb(ip), HttpStatus.OK);
    }

    @DeleteMapping("/suspicious-ip/{ip}")
    public ResponseEntity<StatusResp> deleteIpFromDb(@PathVariable String ip) {
        return new ResponseEntity<>(suspiciousIpService.deleteSuspiciousIpFromDb(ip), HttpStatus.OK);
    }

    @GetMapping("/suspicious-ip")
    public ResponseEntity<List<SuspiciousIp>> getIpListFromDb() {
        return new ResponseEntity<>(suspiciousIpService.getIpListFromDb(), HttpStatus.OK);
    }

    @PostMapping("/stolencard")
    public ResponseEntity<StolenCard> saveStolenCardInDb(@RequestBody StolenCard card) {
        return new ResponseEntity<>(stolenCardService.saveStolenCardInDb(card), HttpStatus.OK);
    }

    @DeleteMapping("/stolencard/{number}")
    public ResponseEntity<StatusResp> deleteStolenCardFromDb(@PathVariable String number) {
        return new ResponseEntity<>(stolenCardService.deleteStolenCardFromDb(number), HttpStatus.OK);
    }

    @GetMapping("/stolencard")
    public ResponseEntity<List<StolenCard>> getStolenCardListFromDb() {
        return new ResponseEntity<>(stolenCardService.getStolenCardListFromDb(), HttpStatus.OK);
    }
}

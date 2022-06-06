package antifraud.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TransactionService {
    public ResponseEntity<Map<String, String>> checkValidTransaction(Map<String, Long> transaction) {
        if (transaction.get("amount") == null || transaction.get("amount") <= 0) {
            return ResponseEntity.badRequest().build();
        } else if (transaction.get("amount") <= 200) {
            return ResponseEntity.ok().body(Map.of("result", "ALLOWED"));
        } else if (transaction.get("amount") <= 1500) {
            return ResponseEntity.ok().body(Map.of("result", "MANUAL_PROCESSING"));
        } else {
            return ResponseEntity.ok().body(Map.of("result", "PROHIBITED"));
        }
    }
}

package antifraud.service;

import antifraud.dto.TransactionReq;
import antifraud.dto.TransactionResp;
import antifraud.repository.StolenCardRepository;
import antifraud.repository.SuspiciousIpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class TransactionService {
    @Autowired
    private StolenCardRepository stolenCardRepository;
    @Autowired
    private SuspiciousIpRepository suspiciousIpRepository;

    public TransactionResp checkValidTransaction(TransactionReq request) {
        checkNotNullAmount(request.getAmount());
        Set<String> reasons = new HashSet<>();
        String result = "";

        if (isStolenCard(request.getNumber())) {
            result = "PROHIBITED";
            reasons.add("card-number");
        }
        if (isSuspiciousIp(request.getIp())) {
            result = "PROHIBITED";
            reasons.add("ip");
        }
        if (request.getAmount() > 1500){
            reasons.add("amount");
        }

        if(result.isBlank()){
            if (request.getAmount() <= 200) {
                result = "ALLOWED";
            } else if (request.getAmount() <= 1500) {
                result = "MANUAL_PROCESSING";
                reasons.add("amount");
            } else if (request.getAmount() > 1500) {
                result = "PROHIBITED";
                reasons.add("amount");
            }
        }

        return new TransactionResp(result, getInfo(reasons));
    }

    private void checkNotNullAmount(Long amount) {
        if (amount == null || amount <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong amount!");
        }
    }

    private boolean isStolenCard(String number) {
        return stolenCardRepository.findStolenCardByNumber(number).isPresent();
    }

    private boolean isSuspiciousIp(String ip) {
        return suspiciousIpRepository.findSuspiciousIpByIp(ip).isPresent();
    }

    private String getInfo(Set<String> reasons) {
        if (reasons.isEmpty()) reasons.add("none");
        StringJoiner joiner = new StringJoiner(", ");
        for (String r : reasons) {
            joiner.add(r);
        }
        return joiner.toString();
    }
}

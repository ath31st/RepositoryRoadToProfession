package antifraud.service;

import antifraud.dto.TransactionReq;
import antifraud.dto.TransactionResp;
import antifraud.repository.StolenCardRepository;
import antifraud.repository.SuspiciousIpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private StolenCardRepository stolenCardRepository;
    @Autowired
    private SuspiciousIpRepository suspiciousIpRepository;

    public TransactionResp checkValidTransaction(TransactionReq request) {
        checkNotNullAmount(request.getAmount());
        List<String> reasons = new ArrayList<>();
        String result = "";
        System.out.println(request.getAmount());

        if (request.getAmount() <= 200) {
            result = "ALLOWED";
        } else if (request.getAmount() <= 1500) {
            result = "MANUAL_PROCESSING";
            reasons.add("amount");
        } else if (request.getAmount() > 1500) {
            result = "PROHIBITED";
            reasons.add("amount");
        }

        if (isStolenCard(request.getNumber())) {
            result = "PROHIBITED";
            reasons.add("card-number");
        }
        if (isSuspiciousIp(request.getIp())) {
            result = "PROHIBITED";
            reasons.add("ip");
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

    private String getInfo(List<String> reasons) {
        StringBuilder info = new StringBuilder();
        if (reasons.isEmpty()) {
            info = new StringBuilder("none");
        } else {
            for (String r : reasons) {
                info.append(r).append(" ");
            }
        }
        return info.toString().trim();
    }
}

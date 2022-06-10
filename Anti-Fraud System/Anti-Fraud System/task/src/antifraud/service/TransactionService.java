package antifraud.service;

import antifraud.dto.TransactionResp;
import antifraud.entity.Transaction;
import antifraud.repository.StolenCardRepository;
import antifraud.repository.SuspiciousIpRepository;
import antifraud.repository.TransactionRepository;
import antifraud.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private StolenCardRepository stolenCardRepository;
    @Autowired
    private SuspiciousIpRepository suspiciousIpRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionResp checkAndSaveTransactionInDb(Transaction transaction) {
        checkNotNullAmount(transaction.getAmount());
        checkRegionExisting(transaction.getRegion());

        Set<String> reasons = new HashSet<>();
        String result = "";

        transactionRepository.save(transaction);

        if (isStolenCard(transaction.getNumber())) {
            result = "PROHIBITED";
            reasons.add("card-number");
        }
        if (isSuspiciousIp(transaction.getIp())) {
            result = "PROHIBITED";
            reasons.add("ip");
        }

        Optional<List<Transaction>> transactions = transactionRepository
                .findByNumberAndDateBetween(transaction.getNumber()
                        , transaction.getDate().minusHours(1)
                        , transaction.getDate()
                        , Sort.by("date"));

        if (transactions.isPresent()) {
            int regionCount = transactions.get()
                    .stream()
                    .collect(Collectors.groupingBy(Transaction::getRegion, Collectors.counting())).size();
            int ipCount = transactions.get()
                    .stream()
                    .collect(Collectors.groupingBy(Transaction::getIp, Collectors.counting())).size();


            if (regionCount == 3) {
                result = "MANUAL_PROCESSING";
                reasons.add("region-correlation");
            } else if (regionCount > 3) {
                result = "PROHIBITED";
                reasons.add("region-correlation");
            }
            if (ipCount == 3) {
                result = "MANUAL_PROCESSING";
                reasons.add("ip-correlation");
            } else if (ipCount > 3) {
                result = "PROHIBITED";
                reasons.add("ip-correlation");
            }
        }

        if (transaction.getAmount() > 1500) {
            reasons.add("amount");
        }

        if (result.isBlank()) {
            if (transaction.getAmount() <= 200) {
                result = "ALLOWED";
            } else if (transaction.getAmount() <= 1500) {
                result = "MANUAL_PROCESSING";
                reasons.add("amount");
            } else if (transaction.getAmount() > 1500) {
                result = "PROHIBITED";
                reasons.add("amount");
            }
        }
        System.out.println(transaction);


        return new TransactionResp(result, getInfo(reasons));
    }

    private void checkNotNullAmount(Long amount) {
        if (amount == null || amount <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong amount!");
        }
    }

    private void checkRegionExisting(String region) {
        if (Arrays.stream(Region.values()).noneMatch(r -> r.name().equals(region.toUpperCase()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong region!");
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

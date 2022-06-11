package antifraud.service;

import antifraud.dto.ManualProcessingReq;
import antifraud.dto.TransactionResp;
import antifraud.entity.Card;
import antifraud.entity.Transaction;
import antifraud.repository.CardRepository;
import antifraud.repository.StolenCardRepository;
import antifraud.repository.SuspiciousIpRepository;
import antifraud.repository.TransactionRepository;
import antifraud.util.Region;
import antifraud.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

import static antifraud.util.Reason.*;
import static antifraud.util.Result.*;


@Service
public class TransactionService {
    @Autowired
    private StolenCardRepository stolenCardRepository;
    @Autowired
    private SuspiciousIpRepository suspiciousIpRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CardRepository cardRepository;

    public TransactionResp checkAndSaveTransactionInDb(Transaction transaction) {

        checkNotNullAmount(transaction.getAmount());
        checkRegionExisting(transaction.getRegion());

        Set<String> reasons = new HashSet<>();
        String result = "";

        if (isStolenCard(transaction.getNumber())) {
            result = PROHIBITED.name();
            reasons.add(CARD_NUMBER.getReason());
        }
        if (isSuspiciousIp(transaction.getIp())) {
            result = PROHIBITED.name();
            reasons.add(IP.getReason());
        }

        long defaultAllowedAmountLimit = 200;
        long defaultManualAmountLimit = 1500;

        Card card = cardRepository
                .findByNumber(transaction.getNumber())
                .orElse(new Card(transaction.getNumber(), defaultAllowedAmountLimit, defaultManualAmountLimit));

        Optional<List<Transaction>> transactions = transactionRepository
                .findByNumberAndDateBetween(transaction.getNumber()
                        , transaction.getDate().minusHours(1)
                        , transaction.getDate()
                        , Sort.by("date"));

        if (transactions.isPresent()) {
            transactions.get().add(transaction);

            int regionCount = transactions.get()
                    .stream()
                    .collect(Collectors.groupingBy(Transaction::getRegion, Collectors.counting())).size();
            int ipCount = transactions.get()
                    .stream()
                    .collect(Collectors.groupingBy(Transaction::getIp, Collectors.counting())).size();


            if (regionCount == 3) {
                result = MANUAL_PROCESSING.name();
                reasons.add(REGION_CORRELATION.getReason());
            } else if (regionCount > 3) {
                result = PROHIBITED.name();
                reasons.add(REGION_CORRELATION.getReason());
            }
            if (ipCount == 3) {
                result = MANUAL_PROCESSING.name();
                reasons.add(IP_CORRELATION.getReason());
            } else if (ipCount > 3) {
                result = PROHIBITED.name();
                reasons.add(IP_CORRELATION.getReason());
            }
        }

        if (transaction.getAmount() > card.getManualAmountLimit()) {
            reasons.add(AMOUNT.getReason());
        }

        if (result.isBlank()) {
            if (transaction.getAmount() <= card.getAllowedAmountLimit()) {
                result = ALLOWED.name();
            } else if (transaction.getAmount() <= card.getManualAmountLimit()) {
                result = MANUAL_PROCESSING.name();
                reasons.add(AMOUNT.getReason());
            } else if (transaction.getAmount() > card.getManualAmountLimit()) {
                result = PROHIBITED.name();
                reasons.add(AMOUNT.getReason());
            }
        }

        transaction.setResult(result);
        transactionRepository.save(transaction);
        cardRepository.save(card);
        return new TransactionResp(result, getInfo(reasons));
    }

    public Transaction manualProcessingTransaction(ManualProcessingReq request) {
        checkResultExisting(request.getFeedback());

        Transaction transaction = getTransactionsHistory()
                .stream()
                .filter(t -> t.getId().equals(request.getTransactionId()))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

        checkFeedbackException(transaction.getResult(), request.getFeedback());
        checkTransactionAlreadyHaveFeedback(transaction.getFeedback());

        Card card = cardRepository.findByNumber(transaction.getNumber()).get();

        transaction.setResult(getResultFromAmount(transaction.getAmount(), card));
        transaction.setFeedback(request.getFeedback());
        transactionRepository.save(transaction);

        changeLimit(transaction, card);
        cardRepository.save(card);

        return transaction;
    }

    public List<Transaction> getTransactionsHistoryByNumber(String number) {
        StolenCardService.checkValidStolenCardNumber(number);

        if (transactionRepository.findByNumberEquals(number).isPresent()) {
            return transactionRepository.findByNumberEquals(number).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card number are not found!");
        }
    }

    public List<Transaction> getTransactionsHistory() {
        List<Transaction> transactions = new ArrayList<>();
        transactionRepository.findAll().iterator().forEachRemaining(transactions::add);
        return transactions;
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

    private void checkResultExisting(String result) {
        if (Arrays.stream(Result.values()).noneMatch(r -> r.name().equals(result.toUpperCase()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong result!");
        }
    }

    private void checkFeedbackException(String transactionFeedback, String requestFeedback) {
        if (transactionFeedback.equals(requestFeedback)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Feedback throws an Exception!");
        }
    }

    private void checkTransactionAlreadyHaveFeedback(String feedback) {
        if (!feedback.isBlank()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Transaction already have feedback!");
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

    private void changeLimit(Transaction transaction, Card card) {
        if (transaction.getFeedback().equals(ALLOWED.name())) {
            if (transaction.getResult().equals(MANUAL_PROCESSING.name())) {
                card.setAllowedAmountLimit(upperLimit(card.getAllowedAmountLimit(), transaction.getAmount()));
            } else if (transaction.getResult().equals(PROHIBITED.name())) {
                card.setAllowedAmountLimit(upperLimit(card.getAllowedAmountLimit(), transaction.getAmount()));
                card.setManualAmountLimit(upperLimit(card.getManualAmountLimit(), transaction.getAmount()));
            }
        } else if (transaction.getFeedback().equals(MANUAL_PROCESSING.name())) {
            if (transaction.getResult().equals(ALLOWED.name())) {
                card.setAllowedAmountLimit(lowerLimit(card.getAllowedAmountLimit(), transaction.getAmount()));
            } else if (transaction.getResult().equals(PROHIBITED.name())) {
                card.setManualAmountLimit(upperLimit(card.getManualAmountLimit(), transaction.getAmount()));
            }
        } else if (transaction.getFeedback().equals(PROHIBITED.name())) {
            if (transaction.getResult().equals(ALLOWED.name())) {
                card.setAllowedAmountLimit(lowerLimit(card.getAllowedAmountLimit(), transaction.getAmount()));
                card.setManualAmountLimit(lowerLimit(card.getManualAmountLimit(), transaction.getAmount()));
            } else if (transaction.getResult().equals(MANUAL_PROCESSING.name())) {
                card.setManualAmountLimit(lowerLimit(card.getManualAmountLimit(), transaction.getAmount()));
            }
        }
    }

    private long upperLimit(long currentLimit, long amountFromTransaction) {
        return (long) Math.ceil(0.8 * currentLimit + 0.2 * amountFromTransaction);
    }

    private long lowerLimit(long currentLimit, long amountFromTransaction) {
        return (long) Math.ceil(0.8 * currentLimit - 0.2 * amountFromTransaction);
    }

    private String getResultFromAmount(long amount, Card card) {
        String result;
        if (amount <= card.getAllowedAmountLimit()) {
            result = ALLOWED.name();
        } else if (amount <= card.getManualAmountLimit()) {
            result = MANUAL_PROCESSING.name();
        } else {
            result = PROHIBITED.name();
        }
        return result;
    }
}

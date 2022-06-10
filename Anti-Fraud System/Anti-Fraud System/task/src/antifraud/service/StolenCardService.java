package antifraud.service;

import antifraud.dto.StatusResp;
import antifraud.entity.StolenCard;
import antifraud.entity.SuspiciousIp;
import antifraud.repository.StolenCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class StolenCardService {
    @Autowired
    private StolenCardRepository stolenCardRepository;

    public StolenCard saveStolenCardInDb(StolenCard card) {
        checkValidStolenCardNumber(card.getNumber());
        checkExistingStolenCard(card.getNumber());
        stolenCardRepository.save(card);
        return card;
    }

    public StatusResp deleteStolenCardFromDb(String number) {
        checkValidStolenCardNumber(number);
        StolenCard card = retrieveCardFromDb(number);
        stolenCardRepository.delete(card);
        return new StatusResp(String.format("Card %s successfully removed!", card.getNumber()));
    }

    public List<StolenCard> getStolenCardListFromDb() {
        List<StolenCard> stolenCards = new ArrayList<>();
        stolenCardRepository.findAll().iterator().forEachRemaining(stolenCards::add);
        return stolenCards;
    }

    public static void checkValidStolenCardNumber(String number) {
        if (!checkCC(number)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card number has the wrong format!");
        }
    }

    private void checkExistingStolenCard(String number) {
        if (stolenCardRepository.findStolenCardByNumber(number).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Card is already in the database!");
    }

    private StolenCard retrieveCardFromDb(String number) {
        return stolenCardRepository.findStolenCardByNumber(number)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found!"));
    }

    private static boolean checkCC(String input) {
        String purportedCC = input.replaceAll(" ", "");
        int sum = 0;

        for (int i = 0; i < purportedCC.length(); i++) {
            int cardNum = Integer.parseInt(
                    Character.toString(purportedCC.charAt(i)));

            if ((purportedCC.length() - i) % 2 == 0) {
                cardNum = cardNum * 2;

                if (cardNum > 9) {
                    cardNum = cardNum - 9;
                }
            }

            sum += cardNum;
        }
        return sum % 10 == 0;
    }
}

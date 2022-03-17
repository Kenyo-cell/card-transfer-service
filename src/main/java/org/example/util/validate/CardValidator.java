package org.example.util.validate;

import org.example.exception.IncorrectInputException;
import org.example.entity.request.transfer.TransferData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardValidator {
    private final String formatPattern = "MM/yy";
    private final SimpleDateFormat simpleDateFormat;

    public CardValidator() {
        simpleDateFormat = new SimpleDateFormat(formatPattern);
    }

    public void validate(TransferData data) throws IncorrectInputException {
        if (isCardNumbersAreEqual(data.getCardFromNumber(), data.getCardToNumber()))
            throw new IncorrectInputException("Card From And Card To Numbers must be different");

        if (!isDateCorrect(data.getCardFromValidTill()))
            throw new IncorrectInputException("Card has incorrect date");
    }

    private boolean isCardNumbersAreEqual(String cardFrom, String cardTo) {
        return cardFrom.equals(cardTo);
    }

    private boolean isDateCorrect(String date) {
        try {
            return simpleDateFormat.parse(date).after(new Date());
        } catch (ParseException e) {
            return false;
        }
    }
}

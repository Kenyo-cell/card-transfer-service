package org.example.entity.writer;

import org.example.entity.request.transfer.TransferData;

public record WriteData(String cardFromNumber, String cardToNumber, String date, Integer amount,
                        double percent, String currency) {
    public static WriteData from(TransferData data) {
        return new WriteData(
                data.getCardFromNumber(),
                data.getCardToNumber(),
                data.getCardFromValidTill(),
                data.getAmount().getValue(),
                data.getAmount().getValue() * 0.1f,
                data.getAmount().getCurrency()
        );
    }
}

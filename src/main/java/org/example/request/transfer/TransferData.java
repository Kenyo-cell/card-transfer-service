package org.example.request.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class TransferData {
    @NotBlank(message = "Card From Number must be not blank")
    @Pattern(regexp = "^\\d{16}$", message = "Card From Number must be 16 digits only")
    private String cardFromNumber;

    @NotBlank(message = "Card From Date form must be not blank")
    @Pattern(regexp = "^([0]?[1-9]|10|11|12)/\\d\\d$", message = "Card From Date form must be please with format mm/YY")
    private String cardFromValidTill;

    @NotBlank(message = "Card From CVV code must be not blank")
    @Pattern(regexp = "^\\d{3}$", message = "Card From CVV code must be 3 digits only")
    private String cardFromCVV;

    @NotBlank(message = "Card To Number must be not blank")
    @Pattern(regexp = "^\\d{16}$", message = "Card To Number must be 16 digits only")
    private String cardToNumber;

    @Valid
    @NotNull(message = "Card From Number must be not null")
    private Amount amount;
}

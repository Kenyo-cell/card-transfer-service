package org.example.request.confirm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmData {
    @NotBlank(message = "Operation id must be non blank value")
    @Pattern(regexp = "^\\d+$", message = "Operation id must be 1 or more digits from 0 to 9")
    private String operationId;

    @NotBlank(message = "Verification code must be no blank value")
    @Pattern(regexp = "^\\d{4}$", message = "Verification code must be 4 digits only")
    private String code;
}

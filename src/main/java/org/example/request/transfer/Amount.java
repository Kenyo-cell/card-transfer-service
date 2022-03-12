package org.example.request.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;


@Component
@Data
@NoArgsConstructor
public class Amount {
    @Positive(message = "Amount value must be positive value")
    @NotNull(message = "Amount value must be not null")
    private int value;

    @NotBlank(message = "Amount currency must be not blank value")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Amount currency must be letters only")
    private String currency;
}

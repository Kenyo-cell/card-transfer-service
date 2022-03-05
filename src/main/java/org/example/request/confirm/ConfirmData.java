package org.example.request.confirm;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConfirmData {
    private String operationId;
    private String code;
}

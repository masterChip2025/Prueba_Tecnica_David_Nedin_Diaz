package tarjeta.santander.pruebatarjetasantander.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardStatusResponseDTO {
    private Integer cardId;
/*    private String cardNumber;
    private String holderName;*/
    private String status;
}

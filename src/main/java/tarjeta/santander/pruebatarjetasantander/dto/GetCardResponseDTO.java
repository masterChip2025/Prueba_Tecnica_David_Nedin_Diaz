package tarjeta.santander.pruebatarjetasantander.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tarjeta.santander.pruebatarjetasantander.model.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCardResponseDTO{
    Integer cardId;
    String cardNumber;
    String holderName;
     CardStatus status ;
     LocalDateTime blockedAt;
     LocalDate expiresAt;
     BigDecimal balance;
     String currency;
}
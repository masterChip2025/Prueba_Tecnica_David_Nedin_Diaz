package tarjeta.santander.pruebatarjetasantander.dto;

import tarjeta.santander.pruebatarjetasantander.model.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CardResponseDTO(
        String cardId,
        String cardNumber,
        String status,
        String expiresAt,
        BigDecimal balance,
        String currency
) { }

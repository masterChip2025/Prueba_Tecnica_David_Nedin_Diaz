package tarjeta.santander.pruebatarjetasantander.dto;

import java.math.BigDecimal;

public record CardResponseDTO(
        String cardId,
        String cardNumber,
        String status,
        String expiresAt,
        BigDecimal balance,
        String currency
) { }

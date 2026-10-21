package tarjeta.santander.pruebatarjetasantander.dto;

import tarjeta.santander.pruebatarjetasantander.model.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CardResponseDTO(Integer cardId,
                              String cardNumber,
                              String holderName,
                              CardStatus status,
                              LocalDateTime blockedAt,
                              String blockedReason,
                              LocalDate expiresAt,
                              BigDecimal balance,
                              String currency) { }

package tarjeta.santander.pruebatarjetasantander.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CardRequestDTO(@NotNull(message = "El productId es obligatorio")
                             Integer productId,
                             @NotBlank(message = "El nombre del titular es obligatorio")
                             String holderName
                             ) { }

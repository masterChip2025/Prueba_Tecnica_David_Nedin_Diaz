package tarjeta.santander.pruebatarjetasantander.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CardRequestDTO(@NotNull Integer productId,
                             @NotBlank String holderName,
                             @NotBlank String currency
                             ) { }

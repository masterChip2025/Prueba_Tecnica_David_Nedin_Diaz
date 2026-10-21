package tarjeta.santander.pruebatarjetasantander.dto;

import jakarta.validation.constraints.NotBlank;

public record BlockRequestDTO(
        @NotBlank String blockedReason
) { }

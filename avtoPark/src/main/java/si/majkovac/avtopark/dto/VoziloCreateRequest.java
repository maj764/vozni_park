package si.majkovac.avtopark.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VoziloCreateRequest(
        @NotBlank String registrska,
        @NotNull @Min(1900) Integer letnik,
        Integer kw,
        @NotNull @Min(0) Integer kilometri,
        String opis,
        @NotNull Integer uporabnikId,
        @NotBlank String model,
        @NotBlank String znamka
) {}

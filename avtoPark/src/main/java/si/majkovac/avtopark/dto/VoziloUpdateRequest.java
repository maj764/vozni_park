package si.majkovac.avtopark.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VoziloUpdateRequest(
        @NotBlank String registrska,
        @NotNull @Min(1900) Integer letnik,
        Integer kw,
        @NotNull @Min(0) Integer kilometri,
        String opis,
        @NotNull Integer odgovorniUporabnikId,
        @NotBlank String model,
        @NotBlank String znamka
) {}

package si.majkovac.avtopark.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ServisCreateRequest(
        @NotNull LocalDate datum,
        @NotBlank String vrsta,
        @NotNull @Min(0) Integer km,
        @NotBlank String opis,
        LocalDate naslednjiServis,
        @NotNull Integer voziloId,
        @NotNull Integer uporabnikId,
        @NotNull @Min(0) Float znesek
) {}

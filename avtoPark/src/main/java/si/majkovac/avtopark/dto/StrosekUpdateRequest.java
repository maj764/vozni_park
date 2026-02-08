package si.majkovac.avtopark.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record StrosekUpdateRequest(
        @NotNull LocalDate datum,
        @NotBlank String kategorija,
        String opis,
        @NotNull @Min(0) Float znesek,
        @NotNull Integer voziloId,
        Integer servisId,
        @NotNull Integer uporabnikId
) {}

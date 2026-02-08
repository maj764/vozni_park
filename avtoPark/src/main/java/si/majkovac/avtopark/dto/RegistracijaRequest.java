package si.majkovac.avtopark.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegistracijaRequest(
        @NotBlank String ime,
        @NotBlank String priimek,
        @Email @NotBlank String email,
        @NotBlank String password,
        @NotBlank String kraj,
        @NotNull LocalDate datum
) {}

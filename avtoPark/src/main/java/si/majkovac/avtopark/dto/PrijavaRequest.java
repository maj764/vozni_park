package si.majkovac.avtopark.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PrijavaRequest(
        @Email @NotBlank String email,
        @NotBlank String password
) {}

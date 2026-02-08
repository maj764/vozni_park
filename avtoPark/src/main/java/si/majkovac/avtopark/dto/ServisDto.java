package si.majkovac.avtopark.dto;

import java.time.LocalDate;

public record ServisDto(
        int id,
        LocalDate datum,
        String vrsta,
        int km,
        String opis,
        LocalDate naslednjiServis,
        int voziloId,
        int uporabnikId
) {}

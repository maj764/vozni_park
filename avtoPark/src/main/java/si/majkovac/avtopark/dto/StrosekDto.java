package si.majkovac.avtopark.dto;

import java.time.LocalDate;

public record StrosekDto(
        int id,
        LocalDate datum,
        String kategorija,
        String opis,
        float znesek,
        int voziloId,
        Integer servisId,
        int uporabnikId
) {}

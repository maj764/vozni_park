package si.majkovac.client.dto;

public record VoziloDto(
        int id,
        String registrska,
        int letnik,
        Integer kw,
        int kilometri,
        String opis,
        Integer odgovorniUporabnikId,
        String model,
        String znamka
) {}

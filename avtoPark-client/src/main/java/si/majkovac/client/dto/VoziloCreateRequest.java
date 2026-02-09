package si.majkovac.client.dto;

public record VoziloCreateRequest(
        String registrska,
        int letnik,
        Integer kw,
        int kilometri,
        String opis,
        Integer odgovorniUporabnikId,
        String model,
        String znamka
) {}


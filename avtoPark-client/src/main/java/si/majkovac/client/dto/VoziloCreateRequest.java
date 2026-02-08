package si.majkovac.client.dto;

public record VoziloCreateRequest(
        String registrska,
        int letnik,
        Integer kw,
        int km,
        String opis,
        Integer uId,
        String model,
        String znamka
) {}

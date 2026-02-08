package si.majkovac.client.dto;

public record VoziloUpdateRequest(
        String registrska,
        int letnik,
        Integer kw,
        int km,
        String opis,
        Integer vOup,
        String model,
        String znamka
) {}

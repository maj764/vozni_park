package si.majkovac.client.dto;

public record UporabnikDto(
        int id,
        String ime,
        String priimek,
        String email,
        Integer krajId
) {}

package si.majkovac.avtopark.dto;

public record UporabnikDto(
        int id,
        String ime,
        String priimek,
        String email,
        Integer krajId
) {}

package si.majkovac.avtopark.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import si.majkovac.avtopark.dto.PrijavaRequest;
import si.majkovac.avtopark.dto.RegistracijaRequest;
import si.majkovac.avtopark.dto.UporabnikDto;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class UporabnikiRepository {

    private final JdbcTemplate jdbcTemplate;

    public UporabnikiRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<UporabnikDto> UPORABNIK_MAPPER = (rs, rowNum) ->
            new UporabnikDto(
                    rs.getInt("id"),
                    rs.getString("ime"),
                    rs.getString("priimek"),
                    rs.getString("email"),
                    (Integer) rs.getObject("kraj_id")
            );

    public Optional<UporabnikDto> prijava(PrijavaRequest r) {
        List<UporabnikDto> rows = jdbcTemplate.query(
                "SELECT * FROM public.prijava(?, ?)",
                UPORABNIK_MAPPER,
                r.email(),
                r.password()
        );
        return rows.stream().findFirst();
    }

    public void registracija(RegistracijaRequest r) {
        jdbcTemplate.update(
                "SELECT public.registracija(?, ?, ?, ?, ?, ?)",
                r.ime(),
                r.priimek(),
                r.email(),
                r.password(),
                r.kraj(),
                Date.valueOf(r.datum())
        );
    }


    public Optional<UporabnikDto> getById(int id) {
        String sql = """
                SELECT ?::int AS id,
                       t.ime, t.priimek, t.email, t.kraj_id
                FROM public.dobi_uporabnika(?) t
                """;
        List<UporabnikDto> rows = jdbcTemplate.query(sql, UPORABNIK_MAPPER, id, id);
        return rows.stream().findFirst();
    }


    public void deleteByEmail(String email) {
        jdbcTemplate.update("SELECT public.delete_uporabnik(?)", email);
    }
}

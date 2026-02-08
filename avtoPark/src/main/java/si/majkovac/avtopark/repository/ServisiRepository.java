package si.majkovac.avtopark.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import si.majkovac.avtopark.dto.ServisCreateRequest;
import si.majkovac.avtopark.dto.ServisDto;
import si.majkovac.avtopark.dto.ServisUpdateRequest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ServisiRepository {

    private final JdbcTemplate jdbcTemplate;

    public ServisiRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<ServisDto> SERVIS_MAPPER = (rs, rowNum) ->
            new ServisDto(
                    rs.getInt("id"),
                    rs.getDate("datum").toLocalDate(),
                    rs.getString("vrsta"),
                    rs.getInt("km"),
                    rs.getString("opis"),
                    rs.getDate("naslednji_servis") == null ? null : rs.getDate("naslednji_servis").toLocalDate(),
                    rs.getInt("v_id"),
                    rs.getInt("u_id")
            );

    public List<ServisDto> listByVozilo(int voziloId) {
        String sql = """
                SELECT
                  s.id,
                  s.datum,
                  s.vrsta,
                  s.km,
                  s.opis,
                  s.naslednji_servis,
                  ?::int AS v_id,
                  s.uporabnik_id AS u_id
                FROM public.servisi_vozila(?) s
                """;
        return jdbcTemplate.query(sql, SERVIS_MAPPER, voziloId, voziloId);
    }

    public Optional<ServisDto> getById(int servisId) {
        String sql = """
                SELECT
                  ?::int AS id,
                  t.datum,
                  t.vrsta,
                  t.km,
                  t.opis,
                  t.naslednji_servis,
                  t.v_id,
                  t.u_id
                FROM public.dobi_servis(?) t
                """;
        List<ServisDto> rows = jdbcTemplate.query(sql, SERVIS_MAPPER, servisId, servisId);
        return rows.stream().findFirst();
    }

    public void create(ServisCreateRequest r) {
        jdbcTemplate.update(
                "SELECT public.ustvari_servis(?, ?, ?, ?, ?, ?, ?, ?)",
                Date.valueOf(r.datum()),
                r.vrsta(),
                r.km(),
                r.opis(),
                r.naslednjiServis() == null ? null : Date.valueOf(r.naslednjiServis()),
                r.voziloId(),
                r.uporabnikId(),
                r.znesek()
        );
    }

    public void update(int id, ServisUpdateRequest r) {
        jdbcTemplate.update(
                "SELECT public.update_servis(?, ?, ?, ?, ?, ?, ?, ?)",
                id,
                Date.valueOf(r.datum()),
                r.vrsta(),
                r.km(),
                r.opis(),
                r.naslednjiServis() == null ? null : Date.valueOf(r.naslednjiServis()),
                r.voziloId(),
                r.uporabnikId()
        );
    }

    public void delete(int id) {
        jdbcTemplate.update("SELECT public.servis_delete(?)", id);
    }
}

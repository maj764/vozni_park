package si.majkovac.avtopark.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import si.majkovac.avtopark.dto.StrosekCreateRequest;
import si.majkovac.avtopark.dto.StrosekDto;
import si.majkovac.avtopark.dto.StrosekUpdateRequest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class StroskiRepository {

    private final JdbcTemplate jdbcTemplate;

    public StroskiRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<StrosekDto> STROSEK_MAPPER = (rs, rowNum) ->
            new StrosekDto(
                    rs.getInt("id"),
                    rs.getDate("datum").toLocalDate(),
                    rs.getString("kategorija"),
                    rs.getString("opis"),
                    rs.getFloat("znesek"),
                    rs.getInt("v_id"),
                    (Integer) rs.getObject("ser_id"),
                    rs.getInt("u_id")
            );

    public List<StrosekDto> listByVozilo(int voziloId) {
        String sql = """
                SELECT
                  t.id,
                  t.datum,
                  t.kategorija,
                  t.opis,
                  t.znesek,
                  ?::int AS v_id,
                  t.s_id AS ser_id,
                  t.u_id
                FROM public.stroski_vozila(?) t
                """;
        return jdbcTemplate.query(sql, STROSEK_MAPPER, voziloId, voziloId);
    }

    public Optional<StrosekDto> getById(int strosekId) {
        String sql = """
                SELECT
                  ?::int AS id,
                  t.datum,
                  t.kategorija,
                  t.opis,
                  t.znesek,
                  t.v_id,
                  t.ser_id,
                  t.u_id
                FROM public.dobi_strosek(?) t
                """;
        List<StrosekDto> rows = jdbcTemplate.query(sql, STROSEK_MAPPER, strosekId, strosekId);
        return rows.stream().findFirst();
    }

    public void create(StrosekCreateRequest r) {
        jdbcTemplate.update(
                "SELECT public.ustvari_strosek(?, ?, ?, ?, ?, ?, ?)",
                Date.valueOf(r.datum()),
                r.kategorija(),
                r.opis(),
                r.znesek(),
                r.voziloId(),
                r.servisId(),
                r.uporabnikId()
        );
    }

    public void update(int id, StrosekUpdateRequest r) {
        jdbcTemplate.update(
                "SELECT public.update_strosek(?, ?, ?, ?, ?, ?, ?, ?)",
                id,
                Date.valueOf(r.datum()),
                r.kategorija(),
                r.opis(),
                r.znesek(),
                r.voziloId(),
                r.servisId(),
                r.uporabnikId()
        );
    }

    public void delete(int id) {
        jdbcTemplate.update("SELECT public.brisi_strosek(?)", id);
    }
}

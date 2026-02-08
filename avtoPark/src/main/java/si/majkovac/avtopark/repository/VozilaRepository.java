package si.majkovac.avtopark.repository;

import si.majkovac.avtopark.dto.VoziloCreateRequest;
import si.majkovac.avtopark.dto.VoziloDto;
import si.majkovac.avtopark.dto.VoziloUpdateRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VozilaRepository {

    private final JdbcTemplate jdbcTemplate;

    public VozilaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Mapper za vozila_list() in dobi_vozilo()
    private static final RowMapper<VoziloDto> VOZILO_MAPPER = (rs, rowNum) ->
            new VoziloDto(
                    rs.getInt("id"),
                    rs.getString("registrska"),
                    rs.getInt("letnik"),
                    (Integer) rs.getObject("kw"),
                    rs.getInt("kilometri"),
                    rs.getString("opis"),
                    (Integer) rs.getObject("odgovorni_uporabnik_id"),
                    rs.getString("model"),
                    rs.getString("znamka")
            );

    public List<VoziloDto> list() {
        String sql = "SELECT * FROM public.vozila_list()";
        return jdbcTemplate.query(sql, VOZILO_MAPPER);
    }

    public Optional<VoziloDto> getById(int id) {
        // dobi_vozilo vrača brez "id" v tvojem seznamu (vračal je samo polja)
        // zato najlažje: naredimo query z "id" dodanim v selectu
        String sql = """
                SELECT ?::int AS id, t.*
                FROM public.dobi_vozilo(?) t
                """;
        List<VoziloDto> rows = jdbcTemplate.query(sql, VOZILO_MAPPER, id, id);
        return rows.stream().findFirst();
    }

    public void insert(VoziloCreateRequest r) {
        String sql = "SELECT public.insert_vozilo(?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                r.registrska(),
                r.letnik(),
                r.kw(),
                r.kilometri(),
                r.opis(),
                r.uporabnikId(),
                r.model(),
                r.znamka()
        );
    }

    public void update(int id, VoziloUpdateRequest r) {
        String sql = "SELECT public.update_vozilo(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                id,
                r.registrska(),
                r.letnik(),
                r.kw(),
                r.kilometri(),
                r.opis(),
                r.odgovorniUporabnikId(),
                r.model(),
                r.znamka()
        );
    }

    public void delete(int id) {
        String sql = "SELECT public.brisi_vozilo(?)";
        jdbcTemplate.update(sql, id);
    }
}

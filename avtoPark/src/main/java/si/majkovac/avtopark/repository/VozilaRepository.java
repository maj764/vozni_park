package si.majkovac.avtopark.repository;

import si.majkovac.avtopark.dto.VoziloCreateRequest;
import si.majkovac.avtopark.dto.VoziloDto;
import si.majkovac.avtopark.dto.VoziloUpdateRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.ConnectionCallback;
import java.sql.Types;


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
        jdbcTemplate.execute((ConnectionCallback<Void>) con -> {
            var cs = con.prepareCall("{ call public.insert_vozilo(?, ?, ?, ?, ?, ?, ?, ?) }");
            cs.setString(1, r.registrska());
            cs.setInt(2, r.letnik());

            if (r.kw() == null) cs.setNull(3, Types.INTEGER);
            else cs.setInt(3, r.kw());

            cs.setInt(4, r.kilometri());

            if (r.opis() == null) cs.setNull(5, Types.VARCHAR);
            else cs.setString(5, r.opis());

            if (r.uporabnikId() == null) cs.setNull(6, Types.INTEGER);
            else cs.setInt(6, r.uporabnikId());

            cs.setString(7, r.model());
            cs.setString(8, r.znamka());

            cs.execute();
            return null;
        });
    }


    public void update(int id, VoziloUpdateRequest r) {
        jdbcTemplate.execute((ConnectionCallback<Void>) con -> {
            var cs = con.prepareCall("{ call public.update_vozilo(?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            cs.setInt(1, id);
            cs.setString(2, r.registrska());
            cs.setInt(3, r.letnik());

            if (r.kw() == null) cs.setNull(4, Types.INTEGER);
            else cs.setInt(4, r.kw());

            cs.setInt(5, r.kilometri());

            if (r.opis() == null) cs.setNull(6, Types.VARCHAR);
            else cs.setString(6, r.opis());

            if (r.odgovorniUporabnikId() == null) cs.setNull(7, Types.INTEGER);
            else cs.setInt(7, r.odgovorniUporabnikId());

            cs.setString(8, r.model());
            cs.setString(9, r.znamka());

            cs.execute();
            return null;
        });
    }




    public void delete(int id) {
        jdbcTemplate.execute((ConnectionCallback<Void>) con -> {
            var cs = con.prepareCall("{ call public.brisi_vozilo(?) }");
            cs.setInt(1, id);
            cs.execute();
            return null;
        });
    }

}

package si.majkovac.avtopark.repository;

import si.majkovac.avtopark.dto.KrajDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class KrajiRepository {

    private final JdbcTemplate jdbcTemplate;

    public KrajiRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<KrajDto> KRAJ_MAPPER = (rs, rowNum) ->
            new KrajDto(
                    rs.getInt("id"),
                    rs.getString("ime"),
                    rs.getString("posta")
            );

    public List<KrajDto> list() {
        String sql = "SELECT * FROM kraji_list()";
        return jdbcTemplate.query(sql, KRAJ_MAPPER);
    }

    public Optional<KrajDto> getById(int id) {
        String sql = "SELECT * FROM kraj_get(?)";
        List<KrajDto> rows = jdbcTemplate.query(sql, KRAJ_MAPPER, id);
        return rows.stream().findFirst();
    }
}

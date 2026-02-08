package si.majkovac.avtopark.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DbPingRepository {
    private final JdbcTemplate jdbcTemplate;

    public DbPingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int ping() {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        return result != null ? result : 0;
    }
}

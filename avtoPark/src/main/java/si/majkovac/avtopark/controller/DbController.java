package si.majkovac.avtopark.controller;
import si.majkovac.avtopark.db.DbPingRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DbController {
    private final DbPingRepository repo;

    public DbController(DbPingRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/api/db/ping")
    public Map<String, Object> pingDb() {
        int v = repo.ping();
        return Map.of("db", "ok", "value", v);
    }
}

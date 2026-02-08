package si.majkovac.avtopark.controller;

import si.majkovac.avtopark.dto.KrajDto;
import si.majkovac.avtopark.repository.KrajiRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kraji")
public class KrajiController {

    private final KrajiRepository repo;

    public KrajiController(KrajiRepository repo) {
        this.repo = repo;
    }

    /** GET /api/kraji  -> fn_kraji_list() */
    @GetMapping
    public List<KrajDto> list() {
        return repo.list();
    }

    /** GET /api/kraji/{id} -> fn_kraj_get(id) */
    @GetMapping("/{id}")
    public ResponseEntity<KrajDto> get(@PathVariable int id) {
        return repo.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

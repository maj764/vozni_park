package si.majkovac.avtopark.controller;

import si.majkovac.avtopark.dto.VoziloCreateRequest;
import si.majkovac.avtopark.dto.VoziloDto;
import si.majkovac.avtopark.dto.VoziloUpdateRequest;
import si.majkovac.avtopark.repository.VozilaRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/vozila")
public class VozilaController {

    private final VozilaRepository repo;

    public VozilaController(VozilaRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<VoziloDto> list() {
        return repo.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoziloDto> get(@PathVariable int id) {
        return repo.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody VoziloCreateRequest request) {
        repo.insert(request);
        return ResponseEntity.created(URI.create("/api/vozila")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @Valid @RequestBody VoziloUpdateRequest request) {
        repo.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        repo.delete(id);
        return ResponseEntity.noContent().build();
    }
}

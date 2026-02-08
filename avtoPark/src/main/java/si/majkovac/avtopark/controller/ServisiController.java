package si.majkovac.avtopark.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.majkovac.avtopark.dto.ServisCreateRequest;
import si.majkovac.avtopark.dto.ServisDto;
import si.majkovac.avtopark.dto.ServisUpdateRequest;
import si.majkovac.avtopark.repository.ServisiRepository;

import java.util.List;

@RestController
@RequestMapping("/api/servisi")
public class ServisiController {

    private final ServisiRepository repo;

    public ServisiController(ServisiRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/vozilo/{voziloId}")
    public List<ServisDto> listByVozilo(@PathVariable int voziloId) {
        return repo.listByVozilo(voziloId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServisDto> get(@PathVariable int id) {
        return repo.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ServisCreateRequest request) {
        repo.create(request);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @Valid @RequestBody ServisUpdateRequest request) {
        repo.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        repo.delete(id);
        return ResponseEntity.noContent().build();
    }
}

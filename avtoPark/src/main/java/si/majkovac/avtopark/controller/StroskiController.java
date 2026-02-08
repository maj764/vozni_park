package si.majkovac.avtopark.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.majkovac.avtopark.dto.StrosekCreateRequest;
import si.majkovac.avtopark.dto.StrosekDto;
import si.majkovac.avtopark.dto.StrosekUpdateRequest;
import si.majkovac.avtopark.repository.StrosekRepository;

import java.util.List;

@RestController
@RequestMapping("/api/stroski")
public class StroskiController {

    private final StrosekRepository repo;

    public StroskiController(StrosekRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/vozilo/{voziloId}")
    public List<StrosekDto> listByVozilo(@PathVariable int voziloId) {
        return repo.listByVozilo(voziloId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StrosekDto> get(@PathVariable int id) {
        return repo.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody StrosekCreateRequest request) {
        repo.create(request);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @Valid @RequestBody StrosekUpdateRequest request) {
        repo.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        repo.delete(id);
        return ResponseEntity.noContent().build();
    }
}

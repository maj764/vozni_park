package si.majkovac.avtopark.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.majkovac.avtopark.dto.PrijavaRequest;
import si.majkovac.avtopark.dto.RegistracijaRequest;
import si.majkovac.avtopark.dto.UporabnikDto;
import si.majkovac.avtopark.repository.UporabnikRepository;

@RestController
@RequestMapping("/api/uporabniki")
public class UporabnikiController {

    private final UporabnikRepository repo;

    public UporabnikiController(UporabnikRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/prijava")
    public ResponseEntity<?> prijava(@Valid @RequestBody PrijavaRequest req) {
        try {
            return repo.prijava(req)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(401).body("INVALID_CREDENTIALS"));
        } catch (Exception e) {
            // tvoja funkcija vrže exception INVALID_CREDENTIALS -> vrnemo 401
            return ResponseEntity.status(401).body("INVALID_CREDENTIALS");
        }
    }

    @PostMapping("/registracija")
    public ResponseEntity<Void> registracija(@Valid @RequestBody RegistracijaRequest req) {
        repo.registracija(req);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UporabnikDto> get(@PathVariable int id) {
        return repo.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByEmail(@RequestParam("email") String email) {
        repo.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }
}

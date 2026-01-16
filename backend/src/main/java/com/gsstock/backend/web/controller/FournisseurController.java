package com.gsstock.backend.web.controller;

import com.gsstock.backend.service.FournisseurService;
import com.gsstock.backend.web.dto.purchase.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
@RequiredArgsConstructor
public class FournisseurController {

    private final FournisseurService fournisseurService;

    // READ (ADMIN + COMPTABLE)
    @GetMapping
    public List<FournisseurDto> list() {
        return fournisseurService.findAllActive();
    }

    @GetMapping("/{id}")
    public FournisseurDto get(@PathVariable Long id) {
        return fournisseurService.getById(id);
    }

    @GetMapping("/search")
    public List<FournisseurDto> search(@RequestParam(required = false) String query) {
        return fournisseurService.search(query);
    }

    // WRITE (ADMIN)
    @PostMapping
    public FournisseurDto create(@Valid @RequestBody CreateFournisseurRequest req) {
        return fournisseurService.create(req);
    }

    @PutMapping("/{id}")
    public FournisseurDto update(@PathVariable Long id, @Valid @RequestBody UpdateFournisseurRequest req) {
        return fournisseurService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        fournisseurService.delete(id);
    }
}

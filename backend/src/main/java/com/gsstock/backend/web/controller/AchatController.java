package com.gsstock.backend.web.controller;

import com.gsstock.backend.service.AchatService;
import com.gsstock.backend.web.dto.purchase.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achats")
@RequiredArgsConstructor
public class AchatController {

    private final AchatService achatService;

    // ======================
    // CREATE ACHAT (DRAFT)
    // ======================
    @PostMapping
    public AchatDto create(@RequestBody @Valid CreateAchatRequest request) {
        return achatService.create(request);
    }

    // ======================
    // ADD LINE TO ACHAT
    // ======================
    @PostMapping("/{id}/lines")
    public AchatDto addLine(
            @PathVariable Long id,
            @RequestBody @Valid CreateAchatLineRequest request
    ) {
        return achatService.addLine(id, request);
    }

    // ======================
    // VALIDATE ACHAT
    // ======================
    @PostMapping("/{id}/validate")
    public AchatDto validate(@PathVariable Long id) {
        return achatService.validate(id);
    }

    // ======================
    // GET ALL ACHATS (ADMIN)
    // ======================
    @GetMapping
    public List<AchatDto> getAll() {
        return achatService.findAll();
    }

}

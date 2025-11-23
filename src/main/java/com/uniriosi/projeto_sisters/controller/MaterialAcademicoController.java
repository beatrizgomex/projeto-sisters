package com.uniriosi.projeto_sisters.controller;

import com.uniriosi.projeto_sisters.infrastructure.entitys.MaterialAcademico;
import com.uniriosi.projeto_sisters.service.MaterialAcademicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/materiais")
public class MaterialAcademicoController {

    private final MaterialAcademicoService materialService;

    public MaterialAcademicoController(MaterialAcademicoService materialService) {
        this.materialService = materialService;
    }

    @PostMapping
    public ResponseEntity<MaterialAcademico> compartilharMaterial(
            @RequestBody MaterialAcademico material,
            @RequestParam Long autoraId) {

        MaterialAcademico novoMaterial = materialService.compartilharMaterial(material, autoraId);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMaterial);
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<MaterialAcademico> aprovarMaterial(@PathVariable Long id) {
        MaterialAcademico materialAprovado = materialService.aprovarMaterial(id);
        return ResponseEntity.ok(materialAprovado);
    }

    @GetMapping
    public ResponseEntity<List<MaterialAcademico>> listarMateriais() {
        return ResponseEntity.ok(materialService.listarTodosMateriaisAprovados());
    }

    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<MaterialAcademico> rejeitarMaterial(@PathVariable Long id) {
        MaterialAcademico materialRejeitado = materialService.rejeitarMaterial(id);
        return ResponseEntity.ok(materialRejeitado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMaterial(@PathVariable Long id) {
        materialService.excluirMaterial(id);
        return ResponseEntity.noContent().build();
    }

}

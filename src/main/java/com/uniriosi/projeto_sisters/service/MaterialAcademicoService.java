package com.uniriosi.projeto_sisters.service;

import com.uniriosi.projeto_sisters.infrastructure.entitys.MaterialAcademico;
import com.uniriosi.projeto_sisters.infrastructure.entitys.Usuaria;
import com.uniriosi.projeto_sisters.infrastructure.repository.MaterialAcademicoRepository;
import com.uniriosi.projeto_sisters.infrastructure.repository.UsuariaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialAcademicoService {

    private final MaterialAcademicoRepository materialRepository;
    private final UsuariaRepository usuariaRepository;

    public MaterialAcademicoService(MaterialAcademicoRepository materialRepository, UsuariaRepository usuariaRepository) {
        this.materialRepository = materialRepository;
        this.usuariaRepository = usuariaRepository;
    }

    public MaterialAcademico compartilharMaterial(MaterialAcademico material, Long idAutora) {
        Usuaria autora = usuariaRepository.findById(idAutora)
                .orElseThrow(() -> new RuntimeException("Autora (Usuária) não encontrada"));

        material.setAutora(autora);
        material.setStatus("Pendente");
        return materialRepository.save(material);
    }


    public MaterialAcademico aprovarMaterial(Long idMaterial) {
        MaterialAcademico material = materialRepository.findById(idMaterial)
                .orElseThrow(() -> new RuntimeException("Material não encontrado"));

        material.setStatus("Aceito");
        return materialRepository.save(material);
    }


    public MaterialAcademico rejeitarMaterial(Long idMaterial) {
        MaterialAcademico material = materialRepository.findById(idMaterial)
                .orElseThrow(() -> new RuntimeException("Material não encontrado"));

        material.setStatus("Recusado");
        return materialRepository.save(material);
    }


    public void excluirMaterial(Long idMaterial) {
        if (!materialRepository.existsById(idMaterial)) {
            throw new RuntimeException("Material não encontrado para exclusão");
        }
        materialRepository.deleteById(idMaterial);
    }

    public List<MaterialAcademico> buscarMateriaisPorStatus(String status) {
        return materialRepository.findByStatus(status);
    }

    public List<MaterialAcademico> listarTodosMateriaisAprovados() {
        return materialRepository.findByStatus("Aceito");
    }
}
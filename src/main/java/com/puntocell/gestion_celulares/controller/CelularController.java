package com.puntocell.gestion_celulares.controller;

import com.puntocell.gestion_celulares.dto.CelularRequestDTO;
import com.puntocell.gestion_celulares.dto.CelularResponseDTO;
import com.puntocell.gestion_celulares.entity.Celular;
import com.puntocell.gestion_celulares.services.CelularService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/celulares")
public class CelularController {

    @Autowired
    private CelularService service;

    // GET /api/v1/celulares
    @GetMapping
    public ResponseEntity<List<CelularResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // GET /api/v1/celulares/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CelularResponseDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/celulares/{id}/imagen
    @GetMapping("/{id}/imagen")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Long id) {
        Optional<Celular> celular = service.buscarEntidadPorId(id);
        if (celular.isEmpty() || celular.get().getImagen() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(celular.get().getImagenTipo()))
                .body(celular.get().getImagen());
    }

    // POST /api/v1/celulares
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CelularResponseDTO> crear(
            @Valid @RequestPart("datos") CelularRequestDTO request,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) throws IOException {

        CelularResponseDTO nuevo = service.guardar(request, imagen);
        return ResponseEntity.status(201).body(nuevo);
    }

    // PUT /api/v1/celulares/{id}
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CelularResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestPart("datos") CelularRequestDTO request,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) throws IOException {

        if (service.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        CelularResponseDTO actualizado = service.actualizar(id, request, imagen);
        return ResponseEntity.ok(actualizado);
    }

    // DELETE /api/v1/celulares/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

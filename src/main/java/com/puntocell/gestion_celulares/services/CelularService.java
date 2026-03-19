package com.puntocell.gestion_celulares.services;

import com.puntocell.gestion_celulares.dto.CelularRequestDTO;
import com.puntocell.gestion_celulares.dto.CelularResponseDTO;
import com.puntocell.gestion_celulares.entity.Celular;
import com.puntocell.gestion_celulares.repository.CelularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CelularService {

    @Autowired
    private CelularRepository repository;

    public List<CelularResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Optional<CelularResponseDTO> buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::toResponseDTO);
    }

    public Optional<Celular> buscarEntidadPorId(Long id) {
        return repository.findById(id);
    }

    public CelularResponseDTO guardar(CelularRequestDTO request,
                                      MultipartFile imagen) throws IOException {
        Celular celular = new Celular();
        celular.setMarca(request.getMarca());
        celular.setModelo(request.getModelo());
        celular.setPrecio(request.getPrecio());
        celular.setStock(request.getStock());
        celular.setDisponible(request.getDisponible());

        if (imagen != null && !imagen.isEmpty()) {
            celular.setImagen(imagen.getBytes());
            celular.setImagenTipo(imagen.getContentType());
        }

        return toResponseDTO(repository.save(celular));
    }

    public CelularResponseDTO actualizar(Long id, CelularRequestDTO request,
                                          MultipartFile imagen) throws IOException {
        Celular celular = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Celular no encontrado"));

        celular.setMarca(request.getMarca());
        celular.setModelo(request.getModelo());
        celular.setPrecio(request.getPrecio());
        celular.setStock(request.getStock());
        celular.setDisponible(request.getDisponible());

        if (imagen != null && !imagen.isEmpty()) {
            celular.setImagen(imagen.getBytes());
            celular.setImagenTipo(imagen.getContentType());
        }

        return toResponseDTO(repository.save(celular));
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    // Mapeo de entidad a DTO de respuesta
    private CelularResponseDTO toResponseDTO(Celular celular) {
        CelularResponseDTO dto = new CelularResponseDTO();
        dto.setId(celular.getId());
        dto.setMarca(celular.getMarca());
        dto.setModelo(celular.getModelo());
        dto.setPrecio(celular.getPrecio());
        dto.setStock(celular.getStock());
        dto.setDisponible(celular.getDisponible());
        if (celular.getImagen() != null) {
            dto.setImagenUrl("/api/v1/celulares/" + celular.getId() + "/imagen");
        }
        return dto;
    }
}

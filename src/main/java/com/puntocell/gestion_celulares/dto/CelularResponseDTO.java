package com.puntocell.gestion_celulares.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CelularResponseDTO {

    private Long id;
    private String marca;
    private String modelo;
    private BigDecimal precio;
    private Integer stock;
    private Boolean disponible;
    private String imagenUrl;  // URL para consultar la imagen: /api/v1/celulares/{id}/imagen
}

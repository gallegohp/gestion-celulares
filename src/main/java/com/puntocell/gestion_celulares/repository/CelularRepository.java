package com.puntocell.gestion_celulares.repository;

import com.puntocell.gestion_celulares.entity.Celular;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelularRepository extends JpaRepository<Celular, Long> {
}

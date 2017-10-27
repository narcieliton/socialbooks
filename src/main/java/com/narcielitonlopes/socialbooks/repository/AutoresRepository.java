package com.narcielitonlopes.socialbooks.repository;

import com.narcielitonlopes.socialbooks.domain.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoresRepository extends JpaRepository<Autor, Long> {
}

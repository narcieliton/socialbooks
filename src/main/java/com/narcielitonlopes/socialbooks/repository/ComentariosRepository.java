package com.narcielitonlopes.socialbooks.repository;

import com.narcielitonlopes.socialbooks.com.narcielitonlopes.socialbooks.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentariosRepository extends JpaRepository<Comentario, Long> {
}

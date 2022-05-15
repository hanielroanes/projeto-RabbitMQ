package br.com.haniel.biblioteca.repositorys;

import br.com.haniel.biblioteca.domains.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {
    List<Livro> findByReservado(Boolean naoReservados);
}

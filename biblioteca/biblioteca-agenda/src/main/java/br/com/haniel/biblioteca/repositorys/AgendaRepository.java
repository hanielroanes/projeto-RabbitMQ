package br.com.haniel.biblioteca.repositorys;

import br.com.haniel.biblioteca.domains.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, UUID> {
    List<Agenda> findByLivro(UUID id);

    Optional<Agenda> findTop1ByLivroOrderByPosicaoDesc(UUID livroId);

    Optional<Agenda> findTop1ByLivroOrderByPosicaoAsc(UUID livroId);

    Optional<Agenda> findTop1ByLivroAndClienteOrderByPosicaoAsc(UUID livroId, UUID clienteId);
}

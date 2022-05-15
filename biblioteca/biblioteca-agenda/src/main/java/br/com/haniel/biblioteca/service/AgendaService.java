package br.com.haniel.biblioteca.service;

import br.com.haniel.biblioteca.domains.Agenda;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface AgendaService {
    List<Agenda> buscarAgendamentosPorLivroId(UUID id);

    void criarAgendamento(UUID livroId, UUID clienteId);

    @Transactional
    void deletarAgendamento(UUID livroId, UUID clienteID);
}

package br.com.haniel.biblioteca.service;

import br.com.haniel.biblioteca.domains.Agenda;
import br.com.haniel.biblioteca.dtos.AgendaResponse;

import java.util.List;
import java.util.UUID;

public interface AgendaService {
    List<Agenda> buscarAgendamentosPorLivroId(UUID id);

    void criarAgendamento(UUID livroId, UUID clienteId);

    void deletarAgendamento(UUID livroId, UUID clienteID);

    AgendaResponse buscarAgendamentoPorId(UUID id);
}

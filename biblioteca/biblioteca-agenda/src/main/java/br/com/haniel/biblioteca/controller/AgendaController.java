package br.com.haniel.biblioteca.controller;

import br.com.haniel.biblioteca.domains.Agenda;
import br.com.haniel.biblioteca.dtos.AgendaResponse;
import br.com.haniel.biblioteca.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendas")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Agenda> buscarAgendamentosPorLivroID(@PathVariable UUID id){
        return agendaService.buscarAgendamentosPorLivroId(id);
    }

    @GetMapping("/detalhes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AgendaResponse buscarAgendamentoPorID(@PathVariable UUID id){
        return agendaService.buscarAgendamentoPorId(id);
    }
}

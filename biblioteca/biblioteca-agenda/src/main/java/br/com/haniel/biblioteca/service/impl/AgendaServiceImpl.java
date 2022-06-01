package br.com.haniel.biblioteca.service.impl;

import br.com.haniel.biblioteca.domains.Agenda;
import br.com.haniel.biblioteca.dtos.AgendaResponse;
import br.com.haniel.biblioteca.dtos.ClienteAgendaDto;
import br.com.haniel.biblioteca.dtos.LivroAgendaDto;
import br.com.haniel.biblioteca.dtos.consumers.LivroDto;
import br.com.haniel.biblioteca.rabbit.configs.RabbitSenderService;
import br.com.haniel.biblioteca.repositorys.AgendaRepository;
import br.com.haniel.biblioteca.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class AgendaServiceImpl implements AgendaService {
    private static final String CLIENTE_URL = "clientes/{id}";
    private static final String LIVRO_URL = "livros/{id}";

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private RabbitSenderService rabbitSenderService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Transactional(readOnly = true)
    public List<Agenda> buscarAgendamentosPorLivroId(UUID id) {
        return agendaRepository.findByLivro(id);
    }

    @Override
    @Transactional
    public void criarAgendamento(UUID livroId, UUID clienteId) {
       Integer ultimaPosicao = agendaRepository
               .findTop1ByLivroOrderByPosicaoDesc(livroId)
               .map(Agenda::getPosicao)
               .orElse(0);

        var agenda = Agenda.builder()
                .livro(livroId)
                .cliente(clienteId)
                .posicao(ultimaPosicao + 1)
                .build();

        agendaRepository.save(agenda);
    }

    @Override
    @Transactional
    public void deletarAgendamento(UUID livroId, UUID clienteId){
        Agenda agenda = agendaRepository
                .findTop1ByLivroAndClienteOrderByPosicaoAsc(livroId, clienteId)
                .orElse(null);

        if (!ObjectUtils.isEmpty(agenda)){
            agendaRepository.delete(agenda);
        }

        enviarProximoItemLista(livroId);
    }

    @Override
    @Transactional(readOnly = true)
    public AgendaResponse buscarAgendamentoPorId(UUID id) {
        Agenda agenda = agendaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agenda não encontrada"));


        ClienteAgendaDto cliente = this.buscarCliente(agenda);
        LivroAgendaDto livro = this.buscarLivro(agenda);

        return AgendaResponse.builder()
                .id(agenda.getId())
                .livro(livro)
                .cliente(cliente)
                .posicao(agenda.getPosicao())
                .build();
    }

    private ClienteAgendaDto buscarCliente(Agenda agenda) {
        var paramCliente = new HashMap<String, UUID>();
        paramCliente.put("id", agenda.getCliente());

       return restTemplate.getForObject(CLIENTE_URL, ClienteAgendaDto.class, paramCliente);
    }

    private LivroAgendaDto buscarLivro(Agenda agenda) {
        var paramLivro = new HashMap<String, UUID>();
        paramLivro.put("id", agenda.getLivro());

        return restTemplate.getForObject(LIVRO_URL, LivroAgendaDto.class, paramLivro);
    }

    private void enviarProximoItemLista(UUID livroId){
        Agenda agenda = agendaRepository
                .findTop1ByLivroOrderByPosicaoAsc(livroId)
                .orElse(null);

        if (!ObjectUtils.isEmpty(agenda)){
            rabbitSenderService.enviarMensagemFilaBiblioteca(LivroDto.builder()
                    .idLivro(agenda.getLivro())
                    .idCliente(agenda.getCliente())
                    .build());
            agendaRepository.delete(agenda);
        }else {
            rabbitSenderService.enviarMensagemFilaBiblioteca(
                    LivroDto.builder()
                            .idLivro(livroId)
                            .build());
        }
    }
}

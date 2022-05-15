package br.com.haniel.biblioteca.service.impl;

import br.com.haniel.biblioteca.domains.Agenda;
import br.com.haniel.biblioteca.dtos.consumers.LivroDto;
import br.com.haniel.biblioteca.rabbit.configs.RabbitSenderService;
import br.com.haniel.biblioteca.repositorys.AgendaRepository;
import br.com.haniel.biblioteca.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
public class AgendaServiceImpl implements AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private RabbitSenderService rabbitSenderService;

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

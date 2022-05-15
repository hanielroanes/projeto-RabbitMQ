package br.com.haniel.biblioteca.servivces.impl;

import br.com.haniel.biblioteca.dtos.LivroPost;
import br.com.haniel.biblioteca.domains.Livro;
import br.com.haniel.biblioteca.dtos.senders.LivroSender;
import br.com.haniel.biblioteca.rabbit.configs.RabbitSenderService;
import br.com.haniel.biblioteca.repositorys.LivroRepository;
import br.com.haniel.biblioteca.servivces.ClienteService;
import br.com.haniel.biblioteca.servivces.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class LivroServiceImpl implements LivroService {

    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private RabbitSenderService rabbitService;

    @Override
    @Transactional
    public Livro cadastrarLivro(LivroPost livroPost) {
        var livro = Livro.builder()
                .titulo(livroPost.getTitulo())
                .autor(livroPost.getAutor())
                .descricao(livroPost.getDescricao())
                .reservado(false)
                .build();
        return livroRepository.save(livro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Livro> buscarLivros(boolean disponiveis) {
        if (disponiveis)
            return livroRepository.findByReservado(Boolean.FALSE);
        return livroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Livro buscarLivroPorId(UUID id) {
        return livroRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
    }

    @Override
    @Transactional
    public String reservarLivro(UUID livroId, UUID clienteId) {
        var livro = this.buscarLivroPorId(livroId);
        var cliente = this.clienteService.buscarClientePorId(clienteId);
        if (livro.getReservado()){
            var livroAgendamento = LivroSender.builder()
                    .idLivro(livro.getId())
                    .idCliente(cliente.getId())
                    .build();
            this.rabbitService.enviarMensagemFilaCriaAgenda(livroAgendamento);
            throw new ResponseStatusException(HttpStatus.LOCKED,
                    "O livro se encontra reservado, você foi colocado na lista de espera...");
        }
        livro.setReservado(true);
        livro.setClienteReservado(cliente);
        return String.format("livro %s reservado com sucesso", livro.getTitulo());
    }

    @Override
    @Transactional
    public String devolverLivro(UUID livroId, UUID clienteId) {
        var livro = this.buscarLivroPorId(livroId);
        if (!livro.getReservado()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("O livro %s não se encontra reservado...", livro.getTitulo()));
        }

        if (!livro.getClienteReservado().getId().equals(clienteId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("O livro %s não foi reservado pelo cliente informado", livro.getTitulo()));
        }

        var livroAgendamento = LivroSender.builder()
                .idLivro(livro.getId())
                .idCliente(livro.getClienteReservado().getId())
                .build();
        this.rabbitService.enviarMensagemFilaDeletaAgenda(livroAgendamento);
        return String.format("livro %s devolvido com sucesso", livro.getTitulo());
    }

    @Override
    public void reservarApartirDaAgenda(LivroSender livro) {
        if (livro.getIdLivro() != null && livro.getIdCliente() != null){
            var livroDomain = this.livroRepository.findById(livro.getIdLivro())
                    .orElse(null);
            var clienteDomain = clienteService.buscarClientePorId(livro.getIdCliente());
            livroDomain.setClienteReservado(clienteDomain);
            livroRepository.save(livroDomain);
        }else {
            efetuarDevolução(livro.getIdLivro());
        }
    }

    private void efetuarDevolução(UUID livroId){
        var livro = this.livroRepository.findById(livroId);

       if (livro.isPresent()){
           livro.get().setReservado(false);
           livro.get().setClienteReservado(null);
           livroRepository.save(livro.get());
       }
    }


}

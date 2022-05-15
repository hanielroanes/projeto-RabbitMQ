package br.com.haniel.biblioteca.rabbit.configs;

import br.com.haniel.biblioteca.dtos.consumers.LivroDto;
import br.com.haniel.biblioteca.service.AgendaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitComsumerService {

    @Autowired
    private AgendaService agendaService;

    @RabbitListener(queues = "${spring.rabbitmq.queues.criar-agenda.name}")
    private void criarAgenda(@Payload LivroDto livro){
        agendaService.criarAgendamento(
                livro.getIdLivro(), livro.getIdCliente());
    }

    @RabbitListener(queues = "${spring.rabbitmq.queues.deletar-agenda.name}")
    private void deletarAgenda(@Payload LivroDto livro){
        agendaService.deletarAgendamento(livro.getIdLivro(), livro.getIdCliente());
    }
}

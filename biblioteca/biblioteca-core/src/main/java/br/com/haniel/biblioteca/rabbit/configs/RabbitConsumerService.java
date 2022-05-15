package br.com.haniel.biblioteca.rabbit.configs;

import br.com.haniel.biblioteca.dtos.senders.LivroSender;
import br.com.haniel.biblioteca.servivces.LivroService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

@Component
public class RabbitConsumerService {

    @Autowired
    private LivroService livroService;

    @RabbitListener(queues = "${spring.rabbitmq.queues.biblioteca.name}")
    private void reservarLivro(@Payload LivroSender livro){
        livroService.reservarApartirDaAgenda(livro);
    }
}

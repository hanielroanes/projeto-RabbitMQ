package br.com.haniel.biblioteca.rabbit.configs;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitSenderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queues.biblioteca.name}")
    private String bibliotecaQueue;

    public void enviarMensagemFilaBiblioteca(Object mensagem){
        rabbitTemplate.convertAndSend(bibliotecaQueue, mensagem);
    }


}

package br.com.haniel.biblioteca.rabbit.configs;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitSenderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queues.criar-agenda.name}")
    private String criarAgendaQueue;

    @Value("${spring.rabbitmq.queues.deletar-agenda.name}")
    private String deletarAgendaQueue;

    public void enviarMensagemFilaCriaAgenda(Object mensagem){
        rabbitTemplate.convertAndSend(criarAgendaQueue, mensagem);
    }

    public void enviarMensagemFilaDeletaAgenda(Object mensagem){
        rabbitTemplate.convertAndSend(deletarAgendaQueue, mensagem);
    }


}

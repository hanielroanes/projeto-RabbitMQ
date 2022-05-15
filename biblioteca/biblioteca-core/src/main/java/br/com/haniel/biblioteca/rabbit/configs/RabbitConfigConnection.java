package br.com.haniel.biblioteca.rabbit.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitConfigConnection {

    @Value("${spring.rabbitmq.exchanges.direct.name}")
    private String directExchange;

    @Value("${spring.rabbitmq.queues.biblioteca.name}")
    private String bibliotecaQueue;

    @Value("${spring.rabbitmq.queues.criar-agenda.name}")
    private String criarAgendaQueue;

    @Value("${spring.rabbitmq.queues.deletar-agenda.name}")
    private String deletarAgendaQueue;

    @Autowired
    private AmqpAdmin amqpAdmin;

    private Queue fila(String nomeFila){
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange trocaDireta() {
        return new DirectExchange(directExchange);
    }

    private Binding relacionamento(Queue fila, DirectExchange troca){
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }

    @PostConstruct
    private void adiciona(){
        Queue bibliotecaFila = this.fila(bibliotecaQueue);
        Queue criarAgendaFila   = this.fila(criarAgendaQueue);
        Queue deletarAgendaFila   = this.fila(deletarAgendaQueue);

        DirectExchange troca = this.trocaDireta();

        Binding ligacaoBiblioteca = this.relacionamento(bibliotecaFila, troca);
        Binding ligacaoCriarAgenda   = this.relacionamento(criarAgendaFila, troca);
        Binding ligacaoDeletarAgenda   = this.relacionamento(deletarAgendaFila, troca);

        //Criando as filas no RabbitMQ
        this.amqpAdmin.declareQueue(bibliotecaFila);
        this.amqpAdmin.declareQueue(criarAgendaFila);
        this.amqpAdmin.declareQueue(deletarAgendaFila);

        this.amqpAdmin.declareExchange(troca);

        this.amqpAdmin.declareBinding(ligacaoBiblioteca);
        this.amqpAdmin.declareBinding(ligacaoCriarAgenda);
        this.amqpAdmin.declareBinding(ligacaoDeletarAgenda);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

        return new Jackson2JsonMessageConverter(mapper);
    }
}

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

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

        return new Jackson2JsonMessageConverter(mapper);
    }
}

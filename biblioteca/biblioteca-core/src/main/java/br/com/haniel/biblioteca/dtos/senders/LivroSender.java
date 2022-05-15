package br.com.haniel.biblioteca.dtos.senders;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@Builder
public class LivroSender {

    private UUID idLivro;
    private UUID idCliente;

}

package br.com.haniel.biblioteca.dtos.consumers;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@Builder
public class LivroDto {

    private UUID idLivro;
    private UUID idCliente;

}

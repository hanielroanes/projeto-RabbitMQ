package br.com.haniel.biblioteca.dtos;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendaResponse {

    private UUID id;

    private LivroAgendaDto livro;

    private ClienteAgendaDto cliente;

    private int posicao;
}

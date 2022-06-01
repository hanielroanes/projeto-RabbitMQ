package br.com.haniel.biblioteca.dtos;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteAgendaDto {

    private UUID id;

    private String nome;
}

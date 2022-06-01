package br.com.haniel.biblioteca.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LivroAgendaDto {

    private String titulo;

    private String autor;
}

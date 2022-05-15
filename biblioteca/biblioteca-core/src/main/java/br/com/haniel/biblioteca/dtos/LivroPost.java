package br.com.haniel.biblioteca.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class LivroPost {

    @NotEmpty
    private String titulo;

    @NotEmpty
    private String autor;

    @NotEmpty
    private String descricao;
}

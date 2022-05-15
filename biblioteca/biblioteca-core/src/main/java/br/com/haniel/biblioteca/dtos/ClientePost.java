package br.com.haniel.biblioteca.dtos;

import lombok.*;

import javax.validation.constraints.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientePost {

    @NotEmpty
    private String nome;

    @Min(value = 0, message = "idade não pode ser negativa")
    @NotNull
    private int Idade;
}

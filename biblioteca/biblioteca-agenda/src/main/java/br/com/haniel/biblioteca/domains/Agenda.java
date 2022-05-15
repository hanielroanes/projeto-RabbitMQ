package br.com.haniel.biblioteca.domains;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "agenda")
public class Agenda {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @Column(name = "livro_id", nullable = false, updatable = false)
    private UUID livro;

    @Column(name = "cliente_id", nullable = false, updatable = false)
    private UUID cliente;

    @Column(nullable = false, updatable = false)
    private int posicao;
}

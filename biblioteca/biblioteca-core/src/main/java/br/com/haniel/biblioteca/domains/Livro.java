package br.com.haniel.biblioteca.domains;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private String descricao;

    @Column(columnDefinition = "boolean default false")
    private Boolean reservado;

    @JoinColumn(name = "cliente_Id")
    @ManyToOne
    private Cliente clienteReservado;
}

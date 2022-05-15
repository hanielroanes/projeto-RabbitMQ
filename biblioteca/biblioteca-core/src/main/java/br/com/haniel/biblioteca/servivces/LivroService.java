package br.com.haniel.biblioteca.servivces;

import br.com.haniel.biblioteca.dtos.LivroPost;
import br.com.haniel.biblioteca.domains.Livro;
import br.com.haniel.biblioteca.dtos.senders.LivroSender;

import java.util.List;
import java.util.UUID;

public interface LivroService {
    Livro cadastrarLivro(LivroPost livroPost);

    List<Livro> buscarLivros(boolean disponiveis);

    Livro buscarLivroPorId(UUID id);

    String reservarLivro(UUID livroId, UUID clienteId);

    String devolverLivro(UUID livroId, UUID clienteId);

    void reservarApartirDaAgenda(LivroSender livro);
}

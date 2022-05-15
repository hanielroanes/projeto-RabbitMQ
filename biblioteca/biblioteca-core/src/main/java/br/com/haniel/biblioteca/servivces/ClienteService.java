package br.com.haniel.biblioteca.servivces;

import br.com.haniel.biblioteca.dtos.ClientePost;
import br.com.haniel.biblioteca.domains.Cliente;

import java.util.UUID;

public interface ClienteService {
    Cliente cadastrarCliente(ClientePost clientePost);

    Cliente buscarClientePorId(UUID id);
}

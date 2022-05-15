package br.com.haniel.biblioteca.servivces.impl;

import br.com.haniel.biblioteca.dtos.ClientePost;
import br.com.haniel.biblioteca.domains.Cliente;
import br.com.haniel.biblioteca.repositorys.ClienteRepository;
import br.com.haniel.biblioteca.servivces.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Override
    @Transactional
    public Cliente cadastrarCliente(ClientePost clientePost) {
        var cliente = Cliente.builder()
                .nome(clientePost.getNome())
                .idade(clientePost.getIdade())
                .build();
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarClientePorId(UUID id) {
        return clienteRepository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }
}

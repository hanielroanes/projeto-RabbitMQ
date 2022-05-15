package br.com.haniel.biblioteca.controllers;

import br.com.haniel.biblioteca.dtos.ClientePost;
import br.com.haniel.biblioteca.domains.Cliente;
import br.com.haniel.biblioteca.servivces.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente cadastrarCliente(@RequestBody @Valid ClientePost clientePost){
        return clienteService.cadastrarCliente(clientePost);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente buscarClientePorId(@PathVariable UUID id){
        return clienteService.buscarClientePorId(id);
    }
}

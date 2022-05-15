package br.com.haniel.biblioteca.controllers;

import br.com.haniel.biblioteca.dtos.LivroPost;
import br.com.haniel.biblioteca.domains.Livro;
import br.com.haniel.biblioteca.servivces.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro cadastrarLivro(@RequestBody @Valid LivroPost livroPost) {
        return livroService.cadastrarLivro(livroPost);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Livro> buscarLivros(@RequestParam(required = false) boolean disponiveis){
        return livroService.buscarLivros(disponiveis);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Livro buscarLivroPorId(@PathVariable UUID id){
        return livroService.buscarLivroPorId(id);
    }

    @GetMapping("/reservar/{livroId}")
    @ResponseStatus(HttpStatus.OK)
    public String reservarLivro(@PathVariable UUID livroId, @RequestParam @NotBlank UUID clienteId){
        return livroService.reservarLivro(livroId, clienteId);
    }

    @GetMapping("/devolver/{livroId}")
    @ResponseStatus(HttpStatus.OK)
    public String devolverLivro(@PathVariable UUID livroId, @RequestParam @NotBlank UUID clienteId){
        return livroService.devolverLivro(livroId, clienteId);
    }

}

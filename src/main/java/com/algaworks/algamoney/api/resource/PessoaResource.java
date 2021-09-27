package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Pessoa> listar() {
        return this.repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa pessoaSalva = repository.save(pessoa);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));

        return pessoaSalva;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long id) {
        Optional<Pessoa> pessoa = repository.findById(id);
        return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
    }

}

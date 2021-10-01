package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.service.PessoaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private PessoaService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_read')")
    public List<Pessoa> listar() {
        return this.repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('SCOPE_write')")
    public Pessoa criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa pessoaSalva = repository.save(pessoa);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));

        return pessoaSalva;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_read')")
    public ResponseEntity<Pessoa> buscarPeloid(@PathVariable Long id) {
        Optional<Pessoa> pessoa = repository.findById(id);
        return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and hasAuthority('SCOPE_write')")
    public void remover(@PathVariable Long id){
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and hasAuthority('SCOPE_write')")
    public Pessoa atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa){
        return service.atualizar(id, pessoa);
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and hasAuthority('SCOPE_write')")
    public void atualizarPropriedadeAtivo(@PathVariable Long id, @RequestBody Boolean ativo){
        service.atualizarPropriedadeAtivo(id, ativo);
    }
}

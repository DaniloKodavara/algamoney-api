package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    private final CategoriaRepository repository;

    @Autowired
    private ApplicationEventPublisher publisher;

    public CategoriaResource(CategoriaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and hasAuthority('SCOPE_read')")
    public List<Categoria> listar() {
        return this.repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and hasAuthority('SCOPE_write')")
    public Categoria criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
        Categoria categoriaSalva = this.repository.save(categoria);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));
        return categoriaSalva;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and hasAuthority('SCOPE_read')")
    public ResponseEntity<Categoria> buscarPeloid(@PathVariable Long id) {
        return this.repository.findById(id).map(categoria -> ResponseEntity.ok(categoria)).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        repository.deleteById(id);
    }

}

package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {

    @Autowired
    LancamentoService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Lancamento> listarLancamentos() {
        return service.listarLancamentos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Lancamento buscarLancamentoPorId(@PathVariable Long id) {
        return service.buscarLancamentoPorId(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Lancamento salvar(@Valid @RequestBody Lancamento lancamento) {
        return service.salvarLancamento(lancamento);
    }

}

package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.exceptionhandler.AlgamoneyExceptionHandler;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.api.service.LancamentoService;
import com.algaworks.algamoney.api.service.exception.PessoaInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    LancamentoService service;

    @Autowired
    MessageSource messageSource;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
    public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return service.filtrar(lancamentoFilter, pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
    public Lancamento buscarLancamentoPorId(@PathVariable Long id) {
        return service.buscarLancamentoPorId(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and hasAuthority('SCOPE_write')")
    public Lancamento salvar(@Valid @RequestBody Lancamento lancamento) {
        return service.salvarLancamento(lancamento);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and hasAuthority('SCOPE_write')")
    public void remover(@PathVariable Long id){
        service.removerLancamento(id);
    }

    @ExceptionHandler(PessoaInativaException.class)
    public ResponseEntity<Object> handlePessoaInativaException(PessoaInativaException ex) {
        String mensagemUsuario = messageSource.getMessage("pessoa.inativa", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<AlgamoneyExceptionHandler.Erro> erros = Arrays.asList(new AlgamoneyExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }


}

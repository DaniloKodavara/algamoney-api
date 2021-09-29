package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.exceptionhandler.AlgamoneyExceptionHandler;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.api.service.LancamentoService;
import com.algaworks.algamoney.api.service.exception.PessoaInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter) {
        return service.pesquisarLancamentoPorFiltro(lancamentoFilter);
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

    @ExceptionHandler(PessoaInativaException.class)
    public ResponseEntity<Object> handlePessoaInativaException(PessoaInativaException ex) {
        String mensagemUsuario = messageSource.getMessage("pessoa.inativa", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<AlgamoneyExceptionHandler.Erro> erros = Arrays.asList(new AlgamoneyExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }

}

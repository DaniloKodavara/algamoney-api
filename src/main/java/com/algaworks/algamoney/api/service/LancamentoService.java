package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.service.exception.PessoaInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LancamentoService {

    @Autowired
    LancamentoRepository repository;

    @Autowired
    PessoaService pessoaService;

    public Lancamento buscarLancamentoPorId(Long id){
        return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }


    public List<Lancamento> listarLancamentos() {
        return repository.findAll();
    }

    public Lancamento salvarLancamento(Lancamento lancamento) {
        Pessoa pessoa = pessoaService.buscarPessoaPeloId(lancamento.getPessoa().getId());
        if(pessoa.isInativo())
            throw new PessoaInativaException();
        return repository.save(lancamento);
    }


}

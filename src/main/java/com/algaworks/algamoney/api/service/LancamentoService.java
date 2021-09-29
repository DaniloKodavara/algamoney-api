package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LancamentoService {

    @Autowired
    LancamentoRepository repository;

    public Lancamento buscarLancamentoPorId(Long id){
        return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }


    public List<Lancamento> listarLancamentos() {
        return repository.findAll();
    }

    public Lancamento salvarLancamento(Lancamento lancamento) {
        return repository.save(lancamento);
    }
}

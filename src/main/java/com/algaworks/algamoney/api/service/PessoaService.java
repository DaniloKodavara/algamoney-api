package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    PessoaRepository repository;

    public Pessoa atualizar(Long id, Pessoa pessoa) {
        Pessoa pessoaSalva = buscarPessoaPeloId(id);

        BeanUtils.copyProperties(pessoa, pessoaSalva, "id");

        return repository.save(pessoaSalva);
    }

    public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
        Pessoa pessoaSalva = buscarPessoaPeloId(id);
        pessoaSalva.setAtivo(ativo);
        repository.save(pessoaSalva);
    }

    public Pessoa buscarPessoaPeloId(Long id) {
        Pessoa pessoaSalva = repository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
        return pessoaSalva;
    }
}

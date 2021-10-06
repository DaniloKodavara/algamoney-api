package com.algaworks.algamoney.api.repository.pessoa;

import com.algaworks.algamoney.api.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaRepositoryQuery {

    public Page<Pessoa> findByNomeContaining(String nome, Pageable pageable);

}

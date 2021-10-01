package com.algaworks.algamoney.api.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "permissao")
public class Permissao {

    @Id
    private Long id;
    private String descricao;

}

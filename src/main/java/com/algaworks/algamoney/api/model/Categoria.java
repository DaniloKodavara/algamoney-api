package com.algaworks.algamoney.api.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

}

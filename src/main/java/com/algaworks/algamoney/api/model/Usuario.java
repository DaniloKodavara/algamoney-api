package com.algaworks.algamoney.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    private Long id;
    private String nome;
    private String email;
    private String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_permissao",
            joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_permissao"))
    private List<Permissao> permissoes;
}

package com.example.hulkstore.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "usuarioId")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String correo;
    @NotEmpty
    private String contrasena;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = jakarta.persistence.FetchType.LAZY)
    private List<Carrito> carrito;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol idRol;
}
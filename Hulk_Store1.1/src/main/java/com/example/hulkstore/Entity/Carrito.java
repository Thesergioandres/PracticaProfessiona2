package com.example.hulkstore.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name="carritos")
@Data
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carritoId;
    private int cantidadProductos=0;
    private Double valorTotal=0.0;

    @ManyToOne
    @jakarta.persistence.JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name = "carrito_producto",
            joinColumns = @JoinColumn(name = "carrito_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productos;
}

package com.example.hulkstore.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
public class CarritoDTO {
    private Long carritoId;
    private int cantidadProductos;
    private Double valorTotal;
    private UsuarioDTO usuario;
    private List<ProductoDTO> productos;
}

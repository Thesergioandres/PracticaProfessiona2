package com.example.hulkstore.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "usuarioId")
public class UsuarioDTO {
    private Long usuarioId;
    private String nombre;
    private String correo;
    private String contrasena;
    private List<CarritoDTO> carrito;
    private RolDto idRol;
}

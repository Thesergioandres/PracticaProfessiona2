package com.example.hulkstore.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String correo;
    @NotEmpty
    private String contrasena;
}

package com.example.hulkstore.DTO;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AdminDTO {
    private Long adminId;
    private String nombre;
    private String correo;
    private String contrasena;
}

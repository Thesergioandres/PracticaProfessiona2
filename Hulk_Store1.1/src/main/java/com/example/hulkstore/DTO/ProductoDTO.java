package com.example.hulkstore.DTO;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ProductoDTO {
    private Long productoId;
    private String nombre;
    private int cantidad;
    private Long precio;
    private String imagen;
}

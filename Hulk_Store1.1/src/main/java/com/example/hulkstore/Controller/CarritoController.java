package com.example.hulkstore.Controller;

import com.example.hulkstore.DTO.CarritoDTO;
import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    CarritoService carritoService;

    @GetMapping("/verCarritos")
    public ResponseEntity<List<Carrito>> getCarritos() {
        try {
            List<Carrito> carritos = carritoService.getCarritos();
            return ResponseEntity.ok(carritos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/verCarrito/{carritoId}")
    public ResponseEntity<Object> getCarritoById(@PathVariable("carritoId") Long carritoId) {
        try {
            Optional<CarritoDTO> optionalCarrito = carritoService.getCarritoById(carritoId);
            if (optionalCarrito.isPresent()) {
                Optional<CarritoDTO> carritoOptional = carritoService.getCarritoById(carritoId);
                return  ResponseEntity.ok(carritoOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe carrito con la id: " + carritoId);
            }
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Carrito no encontrado");
        }
    }

    @PostMapping("/agregarProducto")
    public ResponseEntity<String> agregarProductoAlCarrito(@RequestBody Carrito carritoDto) {
        try {
            carritoService.agregarProducto(carritoDto);
            return ResponseEntity.ok("Producto agregado al carrito exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar el producto al carrito: " + e.getMessage());
        }
    }

    @PutMapping("/actualizarProducto/{carritoId}")
    public ResponseEntity<String> actualizarProductoEnCarrito(@PathVariable Long carritoId, @RequestBody Carrito carritoDto) {
        try {
            carritoService.actualizarProducto(carritoId, carritoDto);
            return ResponseEntity.ok("Producto actualizado en el carrito exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el producto en el carrito: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminarProducto/{carritoId}")
    public ResponseEntity<String> eliminarProductoDeCarrito(@PathVariable Long carritoId) {
        try {
            carritoService.eliminarProducto(carritoId);
            return ResponseEntity.ok("Producto eliminado del carrito exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el producto de carrito");
        }
    }
}
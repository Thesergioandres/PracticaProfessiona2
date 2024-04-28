package com.example.hulkstore.Controller;

import com.example.hulkstore.DTO.ProductoDTO;
import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/verProductos") // Listar productos
    public ResponseEntity<List<ProductoDTO>> getProductos() {
        try {
            List<ProductoDTO> productoDTO = productoService.getProductos();
            return ResponseEntity.ok(productoDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/verProducto/{productoId}")//Llamar producto por ID
    public ResponseEntity<Object> getProductoById(@PathVariable("productoId") Long productoId) {
        try {
            Optional<ProductoDTO> optionalProducto = productoService.getProductoById(productoId);
            if (optionalProducto.isPresent()) {
                Optional<ProductoDTO> productoOptional = productoService.getProductoById(productoId);
                return ResponseEntity.ok(productoOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe producto con la id: " + productoId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/addProducto") // Agregar producto
    public ResponseEntity<?> addProducto(@RequestBody Producto producto) {
        try {
            productoService.addProducto(producto);
            return ResponseEntity.ok(Collections.singletonList("Producto agregado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar producto");
        }
    }

    @PutMapping("/updateProducto/{productoId}") // Actualizar producto
    public ResponseEntity<?> updateProducto(@PathVariable("productoId") Long productoId, @RequestBody Producto producto) {
        try {
            Optional<ProductoDTO> optionalProducto = productoService.getProductoById(productoId);
            if (optionalProducto.isPresent()) {
                producto.setProductoId(productoId);
                productoService.updateProducto(producto);
                return ResponseEntity.ok(Collections.singletonList("Producto actualizado correctamente"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto con la id " + productoId + " no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el producto");
        }
    }

    @DeleteMapping("/deleteProducto/{productoId}") // Borrar producto
    public ResponseEntity<?> deleteProducto(@PathVariable Long productoId) {
        try {
            Optional<ProductoDTO> optionalProducto = productoService.getProductoById(productoId);
            if (optionalProducto.isPresent()) {
                productoService.deleteProductoById(productoId);
                return ResponseEntity.ok(Collections.singletonList("Producto eliminado correctamente"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto con la id " + productoId + " no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar producto");
        }
    }
}

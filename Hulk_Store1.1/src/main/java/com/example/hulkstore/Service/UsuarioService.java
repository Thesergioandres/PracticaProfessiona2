package com.example.hulkstore.Service;

import com.example.hulkstore.DTO.CarritoDTO;
import com.example.hulkstore.DTO.ProductoDTO;
import com.example.hulkstore.DTO.RolDto;
import com.example.hulkstore.DTO.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void addUsuario(Usuario usuario) {
        try {
            Carrito carrito = new Carrito();
            carrito.setUsuario(usuario);
            System.out.println("Usuario: " + usuario);
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al agregar el usuario: " + e.getMessage());
        }
    }

    public List<UsuarioDTO> getUsuarios() {
        try {
            return jdbcTemplate.query(usuarioRepository.sqlAllUser,
                (rs, rowNum) -> {
                    Long usuarioId = rs.getLong("usuario_id");

                    UsuarioDTO usuarioDto = new UsuarioDTO();
                    usuarioDto.setUsuarioId(usuarioId);
                    usuarioDto.setNombre(rs.getString("nombre"));
                    usuarioDto.setCorreo(rs.getString("correo"));
                    usuarioDto.setContrasena(rs.getString("contrasena"));
                    usuarioDto.setIdRol(jdbcTemplate.queryForObject(
                            "SELECT r.rol_id, r.descripcion " +
                                    "FROM roles r " +
                                    "LEFT JOIN usuarios u ON r.rol_id = u.id_rol " +
                                    "WHERE u.usuario_id = ?",
                            (rs1, rowNum1) -> {
                                RolDto rol = new RolDto();
                                rol.setRolId(rs1.getLong("rol_id"));
                                rol.setDescripcion(rs1.getString("descripcion"));
                                return rol;
                            },
                            usuarioId
                    ));


                    CarritoDTO carrito = new CarritoDTO();
                    carrito.setCarritoId(rs.getLong("carrito_id"));
                    carrito.setCantidadProductos(rs.getInt("total_productos"));
                    carrito.setValorTotal(rs.getDouble("total_valor"));
                    carrito.setUsuario(usuarioDto);

                    // Asegúrate de que la lista de carritos en el usuario esté inicializada
                    usuarioDto.setCarrito(new ArrayList<>());
                    usuarioDto.getCarrito().add(carrito);

                    // Consulta para obtener los productos asociados a este carrito
                    List<ProductoDTO> productos = jdbcTemplate.query(usuarioRepository.sqlProductUser,
                            (rs2, rowNum2) -> {
                                ProductoDTO producto = new ProductoDTO();
                                producto.setProductoId(rs2.getLong("producto_id"));
                                producto.setNombre(rs2.getString("nombre"));
                                producto.setPrecio(rs2.getLong("precio"));
                                producto.setCantidad(rs2.getInt("cantidad"));
                                return producto;
                            }, carrito.getCarritoId());

                    // Asegúrate de que la lista de productos en el carrito esté inicializada
                    carrito.setProductos(new ArrayList<>());
                    carrito.getProductos().addAll(productos);

                    return usuarioDto;
                });
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener la lista de usuarios: " + e.getMessage());
            return null;
        }
    }

    public Optional<Usuario> getUsuariosById(Long usuarioId) {
        try {
            return usuarioRepository.findById(usuarioId);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener el usuario por ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    public ResponseEntity<?> updateUsuario(Long usuarioId, Usuario UsuarioDetalles) {
        try {
            com.example.hulkstore.Entity.Usuario usuarioUpdate = usuarioRepository.findById(usuarioId).orElse(null);
            if (usuarioUpdate != null) {
                usuarioUpdate.setNombre(UsuarioDetalles.getNombre());
                usuarioUpdate.setCorreo(UsuarioDetalles.getCorreo());
                usuarioUpdate.setContrasena(UsuarioDetalles.getContrasena());
                usuarioUpdate.setIdRol(UsuarioDetalles.getIdRol());
                usuarioRepository.save(usuarioUpdate);
                return ResponseEntity.status(201).body(Collections.singletonList("Usuario actualizado correctamente"));
            } else {
                return ResponseEntity.status(404).body(Collections.singletonList("Usuario con la id " + usuarioId + " no encontrado"));
            }
        } catch (Exception e) {
            System.err.println("Ocurrió un error al actualizar el usuario: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    public void deleteUsuarioById(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al eliminar el usuario por ID: " + e.getMessage());
        }
    }

}


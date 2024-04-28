package com.example.hulkstore.Repository;

import com.example.hulkstore.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    String sqlAllUser = "SELECT u.usuario_id, u.nombre, u.correo, u.contrasena, " + "SUM(c.cantidad_productos) as total_productos, SUM(c.valor_total) as total_valor, c.carrito_id " + "FROM usuarios u " + "LEFT JOIN carritos c ON u.usuario_id = c.usuario_id " + "GROUP BY u.usuario_id";
    String sqlProductUser= "SELECT p.producto_id, p.nombre, p.precio, p.cantidad " + "FROM producto p " + "LEFT JOIN carrito_producto cp ON p.producto_id = cp.producto_id " +  "WHERE cp.carrito_id = ?";
}

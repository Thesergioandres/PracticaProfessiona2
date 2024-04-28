package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Rol;
import com.example.hulkstore.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void addRol(Rol rol) {
        try {
            Rol role = new Rol();
            role.setDescripcion(rol.getDescripcion());
            rolRepository.save(rol);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al agregar el usuario: " + e.getMessage());
        }
    }

    public List<Rol> getRoles() {
        try {
            return rolRepository.findAll();
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener la lista de usuarios: " + e.getMessage());
            return null;
        }
    }

    public Rol getRolById(Long usuarioId) {
        try {
            return rolRepository.findById(usuarioId).orElseThrow();
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener el usuario por ID: " + e.getMessage());
            return null;
        }
    }

    public void updateRol(Rol rol) {
        try {
            rolRepository.save(rol);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al actualizar el usuario: " + e.getMessage());
        }
    }

    public void deleteRolById(Long id) {
        try {
            rolRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al eliminar el usuario por ID: " + e.getMessage());
        }
    }
}

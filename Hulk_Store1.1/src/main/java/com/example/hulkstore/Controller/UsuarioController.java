package com.example.hulkstore.Controller;

import com.example.hulkstore.DTO.UsuarioDTO;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/addUsuario")
    public void saveUsuario(@RequestBody Usuario usuario) {
        usuarioService.addUsuario(usuario);
    }

    @GetMapping("/verUsuarios")
    public List<UsuarioDTO> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @GetMapping("/usuario/{usuarioId}")//Listar usuarios
    public Optional<Usuario> getUsuariosById(@PathVariable("usuarioId") Long usuarioId) {
        return usuarioService.getUsuariosById(usuarioId);
    }

    @PutMapping("/updateUsuario/{usuarioId}")//Actualizar usuario
    public org.springframework.http.ResponseEntity<?> updateUsuario(@PathVariable Long usuarioId, @RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(usuarioId, usuario);
    }

    @DeleteMapping("/deleteUsuario/{usuarioId}")//Borrar usuario
    public void deleteUsuarioById(@PathVariable("usuarioId") Long usuarioId) {
        usuarioService.deleteUsuarioById(usuarioId);
    }

}

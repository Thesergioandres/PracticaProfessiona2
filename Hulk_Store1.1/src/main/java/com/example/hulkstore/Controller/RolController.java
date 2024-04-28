package com.example.hulkstore.Controller;

import com.example.hulkstore.Entity.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.hulkstore.Service.RolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RolController {

    @Autowired
    private RolService rolService;

    @PostMapping("/addRol")
    public void saveRol(@RequestBody Rol rol) {
        rolService.addRol(rol);
    }

    @GetMapping("/verRoles")
    public List<Rol> getRoles() {
        return rolService.getRoles();
    }

    @GetMapping("/roles/{rolId}")//Listar usuarios
    public Rol getRolById(@PathVariable("rolId") Long rolId) {
        return rolService.getRolById(rolId);
    }

    @PutMapping("/updateRoles/{rolId}")//Actualizar usuario
    public void updateRol(@PathVariable("rolId") Long rolId, @RequestBody Rol rol) {
        rol.setRolId(rolId);
        rol.setDescripcion(rol.getDescripcion());
        rolService.updateRol(rol);
    }

    @DeleteMapping("/deleteRoles/{rolId}")//Borrar usuario
    public void deleteUsuarioById(@PathVariable("rolId") Long rolId) {
        rolService.deleteRolById(rolId);
    }

}

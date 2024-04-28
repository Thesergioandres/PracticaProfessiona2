package com.example.hulkstore.Controller;

import com.example.hulkstore.DTO.AdminDTO;
import com.example.hulkstore.Entity.Admin;
import com.example.hulkstore.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/verAdmins") // Listar admins
    public ResponseEntity<List<AdminDTO>> getAdmins() {
        try {
            List<AdminDTO> admins = adminService.getAdmins();
            return ResponseEntity.ok(admins);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/verAdmin/{adminId}")//Listar usuarios
    public ResponseEntity<Object> getAdminById(@PathVariable("adminId") Long adminId) {
        try{
            Optional<AdminDTO> optionalAdmin = adminService.getAdminById(adminId);
            if(optionalAdmin.isPresent()){
                Optional<AdminDTO> adminOptional = adminService.getAdminById(adminId);
                return ResponseEntity.ok(adminOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe administrador con la id: " + adminId);
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/addAdmin")//Agregar admin
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin) {
        try {
            adminService.addAdmin(admin);
            return ResponseEntity.ok("Admin añadido correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar Administrador");
        }
    }

    @PutMapping("/updateAdmin/{adminId}")//Actualizar admin
    public ResponseEntity<String> updateAdmin(@PathVariable("adminId") Long adminId, @RequestBody Admin admin) {
        try {
            Optional<AdminDTO> optionalAdmin = adminService.getAdminById(adminId);
            if (optionalAdmin.isPresent()) {
                admin.setAdminId(adminId);
                adminService.updateAdmin(admin);
                return  ResponseEntity.ok("Administrado actualizado exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe administrador con la id: " + adminId);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el Administrador");
        }
    }
    @DeleteMapping("/deleteAdmin/{adminId}")//Borrar admin
    public ResponseEntity<String> deleteAdminById(@PathVariable("adminId") Long adminId) {
        try {
            Optional<AdminDTO> optionalAdmin = adminService.getAdminById(adminId);
            if (optionalAdmin.isPresent()){
                adminService.deleteAdminById(adminId);
                return ResponseEntity.ok("Administrador eliminado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe administrador con la id: " + adminId);
            }
        } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                 .body("Error al eliminar administrador");
        }
    }

}

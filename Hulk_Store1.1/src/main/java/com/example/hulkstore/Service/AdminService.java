package com.example.hulkstore.Service;

import com.example.hulkstore.DTO.AdminDTO;
import com.example.hulkstore.Entity.Admin;
import com.example.hulkstore.Exceptions.AdminException;
import com.example.hulkstore.Exceptions.ProductoException;
import com.example.hulkstore.Repository.AdminRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AdminService {

    Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ModelMapper modelMapper;
    private AdminDTO adminDTO;

    public List<AdminDTO> getAdmins() {
        try {
            List<Admin> admins = adminRepository.findAll();
            List<AdminDTO> adminDTO = new ArrayList<>();
            for (Admin admin : admins) {
                adminDTO.add(modelMapper.map(admin, AdminDTO.class));
            }
            return adminDTO;
        } catch (Exception e) {
            logger.info("Ocurrió un error al obtener la lista de admins: " + e.getMessage());
            throw new AdminException("Ocurrió un error al obtener la lista de productos");
        }
    }

    public Optional<AdminDTO> getAdminById(Long adminId) {
        try {
            Optional<Admin> optionalAdmin = adminRepository.findById(adminId);

            if (optionalAdmin.isPresent()) {
                Admin admin = optionalAdmin.get();
                return Optional.of(modelMapper.map(admin, AdminDTO.class));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.info("Ocurrió un error al obtener el admin por ID: " + e.getMessage());
            throw new AdminException("Ocurrió un error al obtener el admin");
        }
    }

    public void addAdmin(Admin admin) {
        try {
            adminRepository.save(admin);
            adminDTO = modelMapper.map(admin, AdminDTO.class);
            logger.info("Administrador agregado correctamente desde servicio");
        } catch (Exception e) {
            logger.info("Ocurrió un error al agregar el admin: " + e.getMessage());
            throw new AdminException("Ocurrió un error al agregar el admin");
        }
    }

    public void updateAdmin(Admin admin) {
        try {
            adminRepository.save(admin);
            adminDTO = modelMapper.map(admin, AdminDTO.class);
            logger.info("Administrador actualizado correctamente en el servicio");
        } catch (Exception e) {
            logger.info("Ocurrió un error al actualizar el admin: " + e.getMessage());
            throw new AdminException("Ocurrió un error al actualizar el producto");
        }
    }
    public void deleteAdminById(Long AdminId) {
        try {
            Optional<Admin> optionalAdmin = adminRepository.findById(AdminId);

            if (optionalAdmin.isPresent()) {
                adminRepository.deleteById(AdminId);

                adminDTO = modelMapper.map(optionalAdmin, AdminDTO.class);
                logger.info("Administrador eliminado correctamente en el servicio");
            } else {
                logger.info("No existe administrador con esa id" + AdminId);
            }
        } catch (Exception e) {
            logger.info("Ocurrió un error al eliminar el admin por ID: " + e.getMessage());
            throw new ProductoException("Ocurrió un error al borrar el admin");
        }
    }

}
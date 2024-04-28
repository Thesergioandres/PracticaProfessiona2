package com.example.hulkstore.Service;

import com.example.hulkstore.DTO.ProductoDTO;
import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Exceptions.ProductoException;
import com.example.hulkstore.Repository.ProductoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ProductoService {

    Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductoDTO productoDTO;


    public List<ProductoDTO> getProductos() {
        try {
            List<Producto> productos = productoRepository.findAll();
            List<ProductoDTO>productoDTO = new ArrayList<>();
            for (Producto producto : productos) {
                productoDTO.add(modelMapper.map(producto, ProductoDTO.class));
            }
            return productoDTO;
        } catch (Exception e) {
            logger.info("Ocurrió un error al obtener la lista de productos: " + e.getMessage());
            throw new ProductoException("Ocurrió un error al obtener la lista de productos");
        }
    }

    public Optional<ProductoDTO> getProductoById(Long productoId) {
        try {
            Optional<Producto> optionalProducto = productoRepository.findById(productoId);
            if (optionalProducto.isPresent()){
                Producto producto = optionalProducto.get();
                return Optional.of(modelMapper.map(producto, ProductoDTO.class));
            } else {
                logger.info("Producto no encontrado en el servicio");
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.info("Ocurrió un error al obtener el producto por ID: " + e.getMessage());
            throw new ProductoException("Ocurrió un error al obtener el producto por ID");
        }
    }

    public void addProducto(Producto producto) {
        try {
            productoRepository.save(producto);
            productoDTO = modelMapper.map(producto, ProductoDTO.class);
            logger.info("Producto agregado correctamente en el servicio");
        } catch (Exception e) {
            logger.info("Ocurrió un error al agregar el producto: " + e.getMessage());
            throw new ProductoException("Ocurrió un error al agregar el producto" + e.getMessage());
        }
    }

    public void updateProducto(Producto producto) {
        try {
            productoRepository.save(producto);
            productoDTO = modelMapper.map(producto, ProductoDTO.class);
            logger.info("Producto actualizado correctamente en el servicio");
        } catch (Exception e) {
            logger.info("Ocurrió un error al actualizar el producto: " + e.getMessage());
            throw new ProductoException("Ocurrió un error al actualizar el producto" + e.getMessage());
        }
    }

    public void deleteProductoById(Long productoId) {
        try {
            Optional<Producto> optionalProducto = productoRepository.findById(productoId);
            if (optionalProducto.isPresent()) {
                productoRepository.deleteById(productoId);
                productoDTO = modelMapper.map(optionalProducto, ProductoDTO.class);
                logger.info("Producto eliminado correctamente en el servicio");
            } else {
                logger.info("No se encuentra el producto en el servicio");
            }
        } catch (Exception e) {
            logger.info("Ocurrió un error al borrar el producto: " + e.getMessage());
            throw new ProductoException("Ocurrió un error al borrar el producto");
        }
    }

}



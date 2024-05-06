package com.example.clasesabado.Service;

import com.example.clasesabado.dto.ListPropertyResponse;
import com.example.clasesabado.entity.PropertyEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPropertyService {

    PropertyEntity createProperty (PropertyEntity property);
    ListPropertyResponse getProperties(double minPrice, double maxPrice);
    PropertyEntity updateProperty (PropertyEntity property);
    void deleteProperty (Long id);
    void rentProperty(Long id);
    List<PropertyEntity> findAllProperties();

}

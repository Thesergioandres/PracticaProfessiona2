package com.example.clasesabado.Service.impl;

import com.example.clasesabado.Service.IPropertyService;
import com.example.clasesabado.dto.ListPropertyResponse;
import com.example.clasesabado.entity.PropertyEntity;
import com.example.clasesabado.exceptions.CustomIllegalArgumentException;
import com.example.clasesabado.exceptions.ErrorCode;
import com.example.clasesabado.repository.PropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

@Service
public class PropertyService implements IPropertyService {

    private final PropertyRepository propertyRepository;

    private final Logger logger = LoggerFactory.getLogger(PropertyService.class);

    @Autowired
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public PropertyEntity createProperty(PropertyEntity property) {
        logger.debug("Attempting to register a new property: {}", property);

        try {
            validatePropertyFields(property);
            validateUniqueName(property.getName());
            validateLocation(property.getLocation());
            validatePriceByLocation(property.getLocation(), property.getPrice());

            PropertyEntity propertyEntity = propertyRepository.save(property);
            logger.info("Successfully created property with ID: {}", propertyEntity.getId());
            return propertyEntity;
        } catch (CustomIllegalArgumentException e) {
            logger.error("Error creating property: {}", e.getErrorCode().getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error creating property: {}", e.getMessage());
            throw new CustomIllegalArgumentException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    @Override
    public ListPropertyResponse getProperties(double minPrice, double maxPrice) {
        logger.info("Getting properties with price between {} and {}", minPrice, maxPrice);
        try {
            List<PropertyEntity> entities = propertyRepository.findByPriceBetweenAndAvailabilityTrue(minPrice, maxPrice);
            if (entities.isEmpty()) {
                logger.warn("No properties found that meet the search criteria");
                return new ListPropertyResponse(entities, ErrorCode.INVALID_PROPERTY_CRITERIA.getMessage());
            } else {
                logger.info("Found {} properties that meet criteria", entities.size());
                return new ListPropertyResponse(entities, ErrorCode.REQUEST_SUCCESSFUL.getMessage());
            }
        } catch (Exception e) {
            logger.error("Error getting properties", e);
            throw e;
        }
    }


    @Override
    public PropertyEntity updateProperty(PropertyEntity updatedProperty) {
        try {
            PropertyEntity existingEntity = propertyRepository.findById(updatedProperty.getId())
                    .orElseThrow(() -> new CustomIllegalArgumentException(ErrorCode.PROPERTY_NOT_FOUND));

            logger.debug("Property details before update: {}", existingEntity);

            validatePropertyFields(updatedProperty);

            if (!existingEntity.isAvailability()) {
                logger.warn("Attempt to modify property not available for rent, ID: {}", existingEntity.getId());
                if (!existingEntity.getLocation().equals(updatedProperty.getLocation())) {
                    throw new CustomIllegalArgumentException(ErrorCode.CANNOT_MODIFY_LEASED_PROPERTY_LOCATION);
                }
                if (existingEntity.getPrice() != updatedProperty.getPrice()) {
                    throw new CustomIllegalArgumentException(ErrorCode.CANNOT_MODIFY_LEASED_PROPERTY_PRICE);
                }
            }

            validatePriceByLocation(updatedProperty.getLocation(), updatedProperty.getPrice());

            // Directly update fields in the existing entity
            existingEntity.setName(updatedProperty.getName());
            existingEntity.setAvailability(updatedProperty.isAvailability());
            existingEntity.setImageUrl(updatedProperty.getImageUrl());
            existingEntity.setLocation(updatedProperty.getLocation());
            existingEntity.setPrice(updatedProperty.getPrice());

            // Save the updated entity
            PropertyEntity updatedEntity = propertyRepository.save(existingEntity);
            logger.info("Property with ID: {} updated successfully", updatedProperty.getId());
            return updatedEntity;
        } catch (CustomIllegalArgumentException e) {
            logger.error("Error updating property: {}", e.getErrorCode().getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error updating property with ID: {}", updatedProperty.getId(), e);
            throw e;
        }
    }


    @Override
    public void deleteProperty(Long id) {
        try {
            logger.info("Trying to delete property with ID: {}", id);
            PropertyEntity entity = propertyRepository.findById(id)
                    .orElseThrow(() -> new CustomIllegalArgumentException(ErrorCode.PROPERTY_NOT_FOUND));

            long ageInDays = ChronoUnit.DAYS.between(entity.getCreatedAt(), LocalDateTime.now());
            if (ageInDays > 30) {
                logger.warn("Property with ID: {} is too old to be deleted", id);
                throw new CustomIllegalArgumentException(ErrorCode.PROPERTY_TOO_OLD_TO_DELETE);
            }

            entity.setAvailability(false);
            propertyRepository.save(entity);
            logger.info("Property with ID: {} logically deleted successfully", id);
        } catch (CustomIllegalArgumentException e) {
            logger.error("Error deleting property: {}", e.getErrorCode().getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error deleting property with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public void rentProperty(Long propertyId) {
        logger.info("Trying to rent property with ID: {}", propertyId);
        try {
            PropertyEntity property = propertyRepository.findById(propertyId)
                    .orElseThrow(() -> new CustomIllegalArgumentException(ErrorCode.PROPERTY_NOT_FOUND));

            if (!property.isAvailability()) {
                logger.warn("Property with ID: {} is no longer available for rent", propertyId);
                throw new CustomIllegalArgumentException(ErrorCode.PROPERTY_NOT_AVAILABLE_FOR_RENT);
            }

            property.setAvailability(false);
            propertyRepository.save(property);
            logger.info("Property with ID: {} rented successfully", propertyId);
        } catch (CustomIllegalArgumentException e) {
            logger.error("Error renting property: {}", e.getErrorCode().getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error renting property with ID: {}", propertyId, e);
            throw e;
        }
    }

    @Override
    public List<PropertyEntity> findAllProperties() {
        logger.info("Starting search for all available properties.");
        try {
            List<PropertyEntity> entities = (List<PropertyEntity>) propertyRepository.findAll();

            if (entities.isEmpty()) {
                logger.info("No available properties found.");
            } else {
                logger.info("{} available properties found.", entities.size());
            }

            return entities;
        } catch (Exception e) {
            logger.error("Unexpected error while trying to retrieve all properties.", e);
            throw e;
        }
    }


    private void validatePropertyFields(PropertyEntity property) {
        logger.debug("Validating property fields: {}", property);
        if (property.getName() == null || property.getName().trim().isEmpty() ||
                property.getLocation() == null || property.getLocation().trim().isEmpty() ||
                property.getImageUrl() == null || property.getImageUrl().trim().isEmpty() ||
                property.getPrice() <= 0) {
            logger.warn("Property fields are invalid: {}", property);
            throw new CustomIllegalArgumentException(ErrorCode.PROPERTY_FIELDS_EMPTY);
        }
    }

    private void validateUniqueName(String name) {
        logger.debug("Validating unique name for property: {}", name);
        propertyRepository.findByName(name).ifPresent(s -> {
            logger.warn("The property name already exists: {}", name);
            throw new CustomIllegalArgumentException(ErrorCode.PROPERTY_NAME_EXISTS);
        });
    }

    private void validateLocation(String location) {
        logger.debug("Validating property location: {}", location);
        List<String> allowedLocations = Arrays.asList("Medellin", "Bogota", "Cali", "Cartagena");
        if (!allowedLocations.contains(location)) {
            logger.warn("Invalid property location: {}", location);
            throw new CustomIllegalArgumentException(ErrorCode.INVALID_PROPERTY_LOCATION);
        }
    }

    private void validatePriceByLocation(String location, double price) {
        logger.debug("Validating price by location. Location: {}, Price: {}", location, price);
        if (("Bogota".equals(location) || "Cali".equals(location)) && price < 2000000) {
            logger.warn("The property price in {} is less than the minimum required: {}", location, price);
            throw new CustomIllegalArgumentException(ErrorCode.PRICE_LESS_THAN_MINIMUM);
        }
    }

    public void enableProperty(Long id) {
        PropertyEntity entity = propertyRepository.findById(id).orElseThrow(() -> new CustomIllegalArgumentException(ErrorCode.PROPERTY_NOT_FOUND));
        entity.setAvailability(true); // Suponiendo que "enabled" es un atributo booleano en tu entidad PropertyEntity
        propertyRepository.save(entity);
    }

}

package com.example.clasesabado.exceptions;


public enum ErrorCode {
    PROPERTY_NOT_FOUND("The property does not exist", 1001),
    CANNOT_MODIFY_LOCATION_LEASED_PROPERTY("Cannot modify the location of a leased property", 1002),
    CANNOT_MODIFY_PRICE_LEASED_PROPERTY("Cannot modify the price of a leased property", 1003),
    PROPERTY_FIELDS_EMPTY("Property fields must not be empty and price must be greater than 0", 1004),
    PROPERTY_NAME_EXISTS("A property with the same name already exists", 1005),
    INVALID_PROPERTY_LOCATION("Property location is not valid", 1006),
    PRICE_LESS_THAN_MINIMUM("Properties in Bogotá and Cali must have a price greater than 2,000,000", 1007),
    PROPERTY_TOO_OLD_TO_DELETE("You can only delete properties less than a month old", 1008),
    PROPERTY_NOT_AVAILABLE_FOR_RENT("The property is no longer available for rent", 1009),
    CANNOT_MODIFY_LEASED_PROPERTY_LOCATION("Cannot modify the location of a leased property",1010),
    CANNOT_MODIFY_LEASED_PROPERTY_PRICE("Cannot modify the price of a leased property", 1011),
    INVALID_PROPERTY_CRITERIA("No properties found that meet the criteria", 1012),
    REQUEST_SUCCESSFUL("The request was successful", 1013),
    UNKNOWN_ERROR("Unexpected error", 1014);

    private final String message;
    private final int code;

    ErrorCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}


package com.example.clasesabado.dto;

import com.example.clasesabado.entity.PropertyEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListPropertyResponse {
    private List<PropertyEntity> properties;
    private String message;
}

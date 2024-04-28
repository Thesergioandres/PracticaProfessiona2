package com.example.hulkstore.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "rolId")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;

    @Column(name = "descripcion", length = 80, nullable = false)
    private String descripcion;
}
